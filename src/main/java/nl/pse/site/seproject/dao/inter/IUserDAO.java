package nl.pse.site.seproject.dao.inter;

import nl.pse.site.seproject.model.*;

import java.util.List;

/**
 * All needed methods for the user dao are declared here.
 */
public interface IUserDAO {

    List<User> getAllUsers();
    User getUserByName(String username);
    boolean addUser(User user);
    boolean updateUser(User updatedUser);
    boolean userExists(String username);
    boolean addHolidayToUser(User user, Country country);
    void finishHolidayFromUser(String username);
    boolean addReportToUser(User user, Report report);
    boolean deleteUser(User user);
    boolean removeReportFromUser(User user, Report report);
    boolean removeHolidayFromUser(User user, Country country);
    boolean emailAlreadyInUse(String emailAddress);
}
