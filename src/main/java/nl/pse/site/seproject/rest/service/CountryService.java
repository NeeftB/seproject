package nl.pse.site.seproject.rest.service;

import nl.pse.site.seproject.dao.inter.ICountryDAO;
import nl.pse.site.seproject.model.Country;
import nl.pse.site.seproject.rest.config.ApplicationConfig;
import nl.pse.site.seproject.rest.service.inter.ICountryService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Named(ApplicationConfig.COUNTRY_SERVICE_NAME)
public class CountryService implements ICountryService {


    private ICountryDAO countryDAO;

    @Inject
    public CountryService(@Named(ApplicationConfig.COUNTRY_DAO_NAME)ICountryDAO countryDAO){
        this.countryDAO = countryDAO;
    }

    @Override
    public List<Country> getAllCountries() {
        return countryDAO.getAllCountries();
    }

    @Override
    public Country getCountryByName(String countryName) {
        return countryDAO.getCountryByName(countryName);
    }

    @Override
    public Country getCurrentResident(String username) {
        return countryDAO.getCurrentResident(username);
    }

    @Override
    public List<Country> getCountriesByContinent(String continentName) {
        return countryDAO.getCountriesByContinent(continentName);
    }

    @Override
    public List<Country> getCountriesByLanguage(String language) {
        return countryDAO.getCountriesByLanguage(language);
    }

    @Override
    public List<Country> getAllHolidaysFromUser(String username) {
        return countryDAO.getAllHolidaysFromUser(username);
    }

    @Override
    public boolean countryExists(String countryName) {
        return countryDAO.countryExists(countryName);
    }

    @Override
    public boolean updateCountry(Country updatedCountry) {
        if(countryExists(updatedCountry.getName()) && updatedCountry.getLanguage() != null || updatedCountry.getContinent()
                != null || updatedCountry.getCountryCode() != null){
            return countryDAO.updateCountry(updatedCountry);
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteCountry(String countryName) {
        return countryDAO.deleteCountry(getCountryByName(countryName));
    }

}
