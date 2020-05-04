package nl.pse.site.seproject.model.embedded;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class ResidentId implements Serializable {

    @Column(name = "username")
    private String username;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "start_time")
    private Date startTime;

    public ResidentId() {
    }

    public ResidentId(String username, String countryName, Date startTime) {
        this.username = username;
        this.countryName = countryName;
        this.startTime = startTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResidentId that = (ResidentId) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(countryName, that.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, countryName);
    }
}
