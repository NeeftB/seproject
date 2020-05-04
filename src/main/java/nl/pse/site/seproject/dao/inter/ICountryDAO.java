package nl.pse.site.seproject.dao.inter;

import nl.pse.site.seproject.model.Country;

import java.util.List;

public interface ICountryDAO {

    List<Country> getAllCountries();
    Country getCountryByName(String countryName);
    Country getCurrentResident(String username);
    List<Country> getAllHolidaysFromUser(String username);
    List<Country> getCountriesByContinent(String continentName);
    List<Country> getCountriesByLanguage(String language);
    boolean countryExists(String countryName);
    boolean updateCountry(Country updatedCountry);
    boolean deleteCountry(Country country);
}
