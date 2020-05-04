package nl.pse.site.seproject.dao;

import nl.pse.site.seproject.dao.inter.ICountryDAO;
import nl.pse.site.seproject.model.Country;
import nl.pse.site.seproject.rest.config.ApplicationConfig;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
@Transactional
@Named(ApplicationConfig.COUNTRY_DAO_NAME)
public class CountryDAO implements ICountryDAO {

    @PersistenceContext(unitName = ApplicationConfig.PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    @Override
    public List<Country> getAllCountries() {
        TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c", Country.class);
        return query.getResultList();
    }

    @Override
    public Country getCountryByName(String countryName) {
        TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c WHERE c.name = :countryName", Country.class);
        query.setParameter("countryName", countryName);

        return query.getSingleResult();
    }

    @Override
    public Country getCurrentResident(String username) {
        TypedQuery<Country> query = em.createQuery("SELECT NEW Country (c.name, c.continent, c.language, " +
                "c.countryCode) FROM Country c " +
                "LEFT JOIN c.users users " +
                "LEFT JOIN FETCH users.id i " +
                "WHERE i.username = :username AND users.endTime = null", Country.class);
        query.setParameter("username", username);

        return query.getSingleResult();
    }

    @Override
    public List<Country> getAllHolidaysFromUser(String username) {
        TypedQuery<Country> query = em.createQuery("SELECT NEW Country (c.name, c.continent, c.language, " +
                "c.countryCode) FROM Country c " +
                "LEFT JOIN c.users users " +
                "LEFT JOIN FETCH users.id i " +
                "WHERE i.username = :username", Country.class);
        query.setParameter("username", username);

        return query.getResultList();
    }

    @Override
    public List<Country> getCountriesByContinent(String continentName) {
        TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c WHERE c.continent = :continentName", Country.class);
        query.setParameter("continentName", continentName);
        return query.getResultList();
    }

    @Override
    public List<Country> getCountriesByLanguage(String language) {
        TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c WHERE c.language = :language", Country.class);
        query.setParameter("language", language);
        return query.getResultList();
    }

    @Override
    public boolean countryExists(String countryName) {
        Query query = em.createQuery("SELECT c FROM Country c WHERE c.name = :countryName");
        query.setParameter("countryName", countryName);
        int count = query.getResultList().size();
        return count > 0;
    }

    @Override
    public boolean updateCountry(Country updatedCountry) {
        Country country = getCountryByName(updatedCountry.getName());
        country.setLanguage(updatedCountry.getLanguage());
        country.setContinent(updatedCountry.getContinent());
        country.setCountryCode(updatedCountry.getCountryCode());
        em.flush();
        return true;
    }

    @Override
    public boolean deleteCountry(Country country) {
        em.remove(country);
        return true;
    }

}
