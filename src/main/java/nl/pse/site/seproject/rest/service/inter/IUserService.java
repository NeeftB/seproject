package nl.pse.site.seproject.rest.service.inter;

import nl.pse.site.seproject.model.Photo;
import nl.pse.site.seproject.model.Report;
import nl.pse.site.seproject.model.User;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    User getUserByName(String username);
    boolean addUser(User user);
    boolean updateUser(User updatedUser);
    boolean userExists(String username);
    boolean emailAlreadyInUse(String emailAddress);
    boolean addHolidayCountryToUser(String username, String countryName);
    boolean addReportToUser(String username, Report report);
    boolean deleteUser(String username);
    boolean removeReportFromUser(String username, String reportNumber);
    boolean removeHolidayFromUser(String username, String countryName);
    boolean checkPassword(String username, String password);
    String getRole(String username);
}
