package nl.pse.site.seproject.dao.inter;

import nl.pse.site.seproject.model.Report;

import java.util.List;

public interface IReportDAO {

    List<Report> getTopTenReports();
    List<Report> getReportsOfUser(String username);
    Report getReportByReportNumber(String reportNumber);
    boolean addReport(Report report);
    boolean deleteReport(Report report);
    boolean updateTitle(Report updatedReport);
    boolean publishReport(Report report);
    boolean updateViews(int newView, Report report);
    boolean updateRanking(int newRate, Report report);
    boolean updateAmountOfVotes(int newAmount, Report report);
    boolean reportExists(String reportNumber);
    boolean updateReport(Report report, Report updatedReport);
}
