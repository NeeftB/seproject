package nl.pse.site.seproject.dao;

import nl.pse.site.seproject.dao.inter.IUserDAO;
import nl.pse.site.seproject.model.Country;
import nl.pse.site.seproject.model.Report;
import nl.pse.site.seproject.model.Resident;
import nl.pse.site.seproject.model.User;
import nl.pse.site.seproject.rest.config.ApplicationConfig;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Stateless
@Transactional
@Named(ApplicationConfig.USER_DAO_NAME)
public class UserDAO implements IUserDAO {

    @PersistenceContext(unitName = ApplicationConfig.PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    @Override
    public List<User> getAllUsers() {
        em.clear();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    public User getUserByName(String username) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u " +
                "WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    @Override
    public boolean addUser(User user) {
        em.persist(user);
        return true;
    }

    @Override
    public boolean updateUser(User updatedUser) {
            User user = getUserByName(updatedUser.getUsername());
            user.setGender(updatedUser.getGender());
            user.setAge(updatedUser.getAge());
            user.setFirstname(updatedUser.getFirstname());
            user.setMiddlename(updatedUser.getMiddlename());
            user.setLastname(updatedUser.getLastname());
            user.setEmail(updatedUser.getEmail());
            em.flush();
            return true;
    }

    @Override
    public boolean userExists(String username) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username");
        query.setParameter("username", username);

        return query.getResultList().size() > 0;
    }

    @Override
    public boolean emailAlreadyInUse(String emailAddress) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
        query.setParameter("email", emailAddress);

        return query.getResultList().size() > 0;
    }

    @Override
    public boolean addHolidayToUser(User user, Country country) {
        user.addVisitedCountry(country);
        em.flush();
        return true;
    }

    @Override
    public void finishHolidayFromUser(String username) {
        User u = getUserByName(username);
        Set<Resident> residents = u.getVisitedCountries();

        if (residents.size() > 0) {
            for (Resident r : residents) {
                if (r.getEndTime() == null) {
                    r.setEndTime(new Date());
                }
            }
        }
    }

     @Override
    public boolean addReportToUser(User user, Report report) {
        user.addReport(report);
        em.flush();
        return true;
    }

    @Override
    public boolean deleteUser(User user) {
        em.remove(user);
        return true;
    }

    @Override
    public boolean removeReportFromUser(User user, Report report) {
        user.deleteReport(report);
        em.flush();
        return true;
    }


    @Override
    public boolean removeHolidayFromUser(User user, Country country) {
        user.deleteVisitedCountry(country);
        em.flush();
        return true;
    }
}
