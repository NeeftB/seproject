package nl.pse.site.seproject.rest.service;

import nl.pse.site.seproject.dao.inter.IPhotoDAO;
import nl.pse.site.seproject.dao.inter.IReportDAO;
import nl.pse.site.seproject.model.Photo;
import nl.pse.site.seproject.model.Report;
import nl.pse.site.seproject.rest.config.ApplicationConfig;
import nl.pse.site.seproject.rest.service.inter.ICountryService;
import nl.pse.site.seproject.rest.service.inter.IPhotoService;
import nl.pse.site.seproject.rest.service.inter.IReportService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Transactional
@Named(ApplicationConfig.REPORT_SERVICE_NAME)
public class ReportService implements IReportService {

    private IReportDAO reportDAO;
    private ICountryService countryService;

    @Inject
    public ReportService(@Named(ApplicationConfig.REPORT_DAO_NAME)IReportDAO reportDAO,
                         @Named(ApplicationConfig.COUNTRY_SERVICE_NAME) ICountryService countryService){
        this.reportDAO = reportDAO;
        this.countryService = countryService;
    }

    @Override
    public List<Report> getAllReports() {
        return null;
    }

    @Override
    public List<Report> getAllPublishedReports() {
        List<Report> reports = reportDAO.getAllPublishedReports();

        for(Report r: reports){
            System.out.println(r.getUser().getUsername());
        }

        return reportDAO.getAllPublishedReports();
    }

    @Override
    public List<Report> getTopTenReports() {
        return reportDAO.getTopTenReports();
    }

    @Override
    public List<Report> getReportsOfUser(String username) {
        return reportDAO.getReportsOfUser(username);
    }

    @Override
    public Report getReportByReportNumber(String reportNumber) {
    return reportDAO.getReportByReportNumber(reportNumber);
    }


    @Override
    public boolean addReport(String username, Report report) {
        report.setReportNumber(createReportNumber(countryService.getCountryByName(report.getCountry()).getCountryCode()));
        reportDAO.addReport(report);
        return true;
    }

    @Override
    public boolean publishReport(String reportNumber) {
        return reportDAO.publishReport(getReportByReportNumber(reportNumber));
    }

    @Override
    public boolean updateReport(Report updatedReport, String reportNumber) {
        if(reportExists(reportNumber)){
            return reportDAO.updateReport(getReportByReportNumber(reportNumber), updatedReport);
        }
        return false;
    }

    @Override
    public boolean deleteReport(String reportNumber) {
        return reportDAO.deleteReport(getReportByReportNumber(reportNumber));
    }

    public boolean updateRanking(int newRate) {
        return false;
    }

    @Override
    public boolean reportExists(String reportNumber) {
        return reportDAO.reportExists(reportNumber);
    }

    @Override
    public String createReportNumber(String countryCode){
        String BEGIN_OF_STRING = "REP";
        String UNDERSCORE ="_";
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmmss");
        Date currentDate = new Date();
        StringBuilder sb = new StringBuilder();
        sb.append(BEGIN_OF_STRING + UNDERSCORE + countryCode.toUpperCase() + UNDERSCORE + dateFormat.format(currentDate));
        return sb.toString();
    }
}
