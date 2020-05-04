package nl.pse.site.seproject.rest.service.inter;

import nl.pse.site.seproject.model.Country;

import java.util.List;

public interface ICountryService {

    List<Country> getAllCountries();
    Country getCountryByName(String countryName);
    Country getCurrentResident(String username);
    List<Country> getCountriesByContinent(String continentName);
    List<Country> getCountriesByLanguage(String language);
    List<Country> getAllHolidaysFromUser(String username);
    boolean countryExists(String countryName);
    boolean updateCountry(Country updatedCountry);
    boolean deleteCountry(String countryName);
}
