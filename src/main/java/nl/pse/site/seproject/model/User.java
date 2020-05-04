package nl.pse.site.seproject.model;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "User")
public class User implements Serializable {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "middlename")
    private String middlename;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "role")
    private String role;

    @Column(name = "cookie_id")
    private int cookieId;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonbTransient
    private Set<Resident> visitedCountries = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonbTransient
    private Set<Report> reports = new HashSet<>();

    public User(){
    }

    public User(String username, String password, String email, String firstname, String middlename, String lastname, int age, String gender, String role, int cookieId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.age = age;
        this.gender = gender;
        this.role = role;
        this.cookieId = cookieId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getCookieId() {
        return cookieId;
    }

    public void setCookieId(int cookieId) {
        this.cookieId = cookieId;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Set<Resident> getVisitedCountries() {
        return visitedCountries;
    }

    public void addVisitedCountry(Country country) {
        Resident resident = new Resident(this, country);
        getVisitedCountries().add(resident);
        country.getUsers().add(resident);
    }

    public void deleteVisitedCountry(Country country){

        for(Iterator<Resident> iterator = visitedCountries.iterator(); iterator.hasNext(); ){
            Resident resident = iterator.next();

            if(resident.getCountry().equals(country) && resident.getUser().equals(this)){
                iterator.remove();
                resident.getCountry().getUsers().remove(resident);
                resident.setCountry(null);
                resident.setUser(null);
            }
        }
    }

    public Set<Report> getReports() {
        return reports;
    }

    public void addReport(Report report) {
        getReports().add(report);
        report.setUser(this);
    }

    public void deleteReport(Report report){
        for(Report r: reports){
            if(r.getUser().equals(this) && r.equals(report)){
                this.reports.remove(r);
                report.setUser(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
