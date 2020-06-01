package nl.pse.site.seproject.dao;

import nl.pse.site.seproject.dao.inter.IReportDAO;
import nl.pse.site.seproject.model.Photo;
import nl.pse.site.seproject.model.Report;
import nl.pse.site.seproject.rest.config.ApplicationConfig;
import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Stateless
@Transactional
@Named(ApplicationConfig.REPORT_DAO_NAME)
public class ReportDAO implements IReportDAO {

    final static String NEW_REPORT = "SELECT NEW Report(r.reportNumber, r.title, r.country, r.region," +
            "r.category, r.paragraphOne, r.paragraphTwo, r.paragraphThree, r.timestamp, r.published, r.amountOfVotes, r.ranking," +
            " r.views) FROM Report r ";

    @PersistenceContext(unitName = ApplicationConfig.PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    @Override
    public List<Report> getAllReports() {
        return null;
    }

    @Override
    public List<Report> getAllPublishedReports() {
        TypedQuery<Report> query = em.createQuery("SELECT r FROM Report r LEFT JOIN FETCH r.user u WHERE r.published = :true", Report.class);
        query.setParameter("true", true);

        return query.getResultList();
    }

    @Override
    public List<Report> getTopTenReports() {
        //Hier moeten we de ORDER BY nog op de juiste manier gebruiken. Dit moment even de views gepakt.
        TypedQuery<Report> query = em.createQuery(NEW_REPORT + "ORDER BY r.views DESC", Report.class);
        query.setMaxResults(10);
        return query.getResultList();
    }

    @Override
    public List<Report> getReportsOfUser(String username) {
        TypedQuery<Report> query = em.createQuery(NEW_REPORT + " LEFT JOIN r.user u WHERE u.username = :username"
                , Report.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    @Override
    public Report getReportByReportNumber(String reportNumber) {
        //Misschien hier een andere select van maken. Dan miss wel kunnen updaten
//        TypedQuery<Report> query = em.createQuery(NEW_REPORT + " LEFT JOIN r.user u WHERE r.reportNumber = :reportNumber",
//                Report.class);
        TypedQuery<Report> query = em.createQuery("SELECT r FROM Report r LEFT JOIN FETCH r.photos p " +
                        "LEFT JOIN r.user u WHERE r.reportNumber = :reportNumber",
                Report.class);
        query.setParameter("reportNumber", reportNumber);
        return query.getSingleResult();
    }

    @Override
    public boolean addReport(Report report) {
        em.persist(report);
        return true;
    }

    @Override
    public boolean deleteReport(Report report) {
        Set<Photo> photos = report.getPhotos();

        for(Photo p: photos){
            report.deletePhoto(p);
        }

        em.remove(report);
        return true;
    }

    @Override
    public boolean updateTitle(Report updatedReport) {
        Report report = getReportByReportNumber(updatedReport.getReportNumber());
        report.setTitle(updatedReport.getTitle());
        em.flush();
        return false;
    }

    @Override
    public boolean publishReport(Report report) {
        report.setPublished(true);
        em.flush();
        return true;
    }

    @Override
    public boolean updateViews(int newView, Report report) {
        report.setViews(newView);
        em.flush();
        return true;
    }

    @Override
    public boolean updateRanking(int newRate, Report report) {
        report.setRanking(newRate);
        em.flush();
        return true;
    }

    @Override
    public boolean updateAmountOfVotes(int newAmount, Report report) {
        report.setAmountOfVotes(newAmount);
        em.flush();
        return true;
    }

    @Override
    public boolean reportExists(String reportNumber) {
        Query query = em.createQuery("SELECT r FROM Report r WHERE r.reportNumber = :reportnumber");
        query.setParameter("reportnumber", reportNumber);
        int count = query.getResultList().size();

        return count > 0;
    }

    @Override
    public boolean updateReport(Report report, Report updatedReport) {
        report.setTitle(updatedReport.getTitle());
        report.setCountry(updatedReport.getCountry());
        report.setRegion(updatedReport.getRegion());
        report.setParagraphOne(updatedReport.getParagraphOne());
        report.setCategory(updatedReport.getCategory());
        report.setParagraphTwo(updatedReport.getParagraphTwo());
        report.setParagraphThree(updatedReport.getParagraphThree());
        report.setPublished(updatedReport.getPublished());
        em.merge(report);
        em.flush();

        return true;
    }
}
