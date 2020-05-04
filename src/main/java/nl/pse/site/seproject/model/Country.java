package nl.pse.site.seproject.model;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "Country")
public class Country implements Serializable {

    @Id
    @Column(name = "country_name")
    private String name;

    @Column(name = "continent")
    private String continent;

    @Column(name = "language")
    private String language;

    @Column(name = "country_code")
    private String countryCode;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonbTransient
    private Set<Resident> users = new HashSet<>();

    public Country() {
    }


    public Country(String name, String continent, String language, String countryCode) {
        this.name = name;
        this.continent = continent;
        this.language = language;
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Set<Resident> getUsers() {
        return users;
    }

    public void setUsers(Set<Resident> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
