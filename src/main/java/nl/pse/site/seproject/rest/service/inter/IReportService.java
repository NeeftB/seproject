package nl.pse.site.seproject.rest.service.inter;

import nl.pse.site.seproject.model.Photo;
import nl.pse.site.seproject.model.Report;

import java.util.List;

public interface IReportService {

    List<Report> getAllReports();
    List<Report> getTopTenReports();
    List<Report> getReportsOfUser(String username);
    Report getReportByReportNumber(String reportNumber);
    String createReportNumber(String username);
    boolean addReport(String username, Report report);
    boolean publishReport(String reportNumber);
    boolean updateReport(Report updatedReport, String reportNumber);
    boolean updateRanking(int newRate);
    boolean reportExists(String reportNumber);

}
