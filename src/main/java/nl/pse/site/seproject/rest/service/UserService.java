package nl.pse.site.seproject.rest.service;


import nl.pse.site.seproject.dao.inter.IUserDAO;
import nl.pse.site.seproject.model.Report;
import nl.pse.site.seproject.model.User;
import nl.pse.site.seproject.rest.config.ApplicationConfig;
import nl.pse.site.seproject.rest.service.inter.ICountryService;
import nl.pse.site.seproject.rest.service.inter.IReportService;
import nl.pse.site.seproject.rest.service.inter.IUserService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Named(ApplicationConfig.USER_SERVICE_NAME)
public class UserService implements IUserService {

    private IUserDAO userDAO;
    private ICountryService countryService;
    private IReportService reportService;

    @Inject
    public UserService(
            @Named(ApplicationConfig.USER_DAO_NAME)IUserDAO userDAO,
            @Named(ApplicationConfig.COUNTRY_SERVICE_NAME) ICountryService countryService,
            @Named(ApplicationConfig.REPORT_SERVICE_NAME) IReportService reportService){
        this.userDAO = userDAO;
        this.countryService = countryService;
        this.reportService = reportService;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserByName(String username) {
        if(userExists(username)){
            return userDAO.getUserByName(username);
        } else {
            return null;
        }
    }

    @Override
    public boolean addUser(User user) {
        if(userExists(user.getUsername())){
            return false;
        } else if(emailAlreadyInUse(user.getEmail())) {
            return false;
        } else {
            userDAO.addUser(user);
            return true;
        }
    }

    @Override
    public boolean updateUser(User updatedUser) {
        if(userExists(updatedUser.getUsername()) && updatedUser.getGender() != null || updatedUser.getFirstname() != null
                || updatedUser.getLastname() != null || updatedUser.getEmail() != null){
            return userDAO.updateUser(updatedUser);
        } else {
            return false;
        }
    }

    @Override
    public boolean userExists(String username) {
        return userDAO.userExists(username);
    }

    @Override
    public boolean emailAlreadyInUse(String emailAddress) {
        return userDAO.emailAlreadyInUse(emailAddress);
    }

    @Override
    public boolean addHolidayCountryToUser(String username, String countryName) {
        if(!userExists(username) || !countryService.countryExists(countryName)){
            return false;
        } else {
            userDAO.finishHolidayFromUser(username);
            return userDAO.addHolidayToUser(userDAO.getUserByName(username),
                    countryService.getCountryByName(countryName));
        }
    }

    @Override
    public boolean addReportToUser(String username, Report report) {
        if(!userExists(username)){
            return false;
        } else {
            reportService.addReport(username, report);
            return userDAO.addReportToUser(userDAO.getUserByName(username), report);
        }
    }

    @Override
    public boolean deleteUser(String username) {
        return userDAO.deleteUser(getUserByName(username));
    }

    @Override
    public boolean removeReportFromUser(String username, String reportNumber) {
        return userDAO.removeReportFromUser(getUserByName(username), reportService.getReportByReportNumber(reportNumber));
    }

    @Override
    public boolean removeHolidayFromUser(String username, String countryName) {
        return userDAO.removeHolidayFromUser(getUserByName(username), countryService.getCountryByName(countryName));
    }

    @Override
    public boolean checkPassword(String username, String password) {
        try {
            getUserByName(username).getPassword().equalsIgnoreCase(password);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public String getRole(String username) {
        return getUserByName(username).getRole();
    }
}
