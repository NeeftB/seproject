package nl.pse.site.seproject.model;

import nl.pse.site.seproject.model.embedded.ResidentId;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name ="Resident")
public class Resident {

    @EmbeddedId
    private ResidentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    @MapsId("username")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country")
    @MapsId("countryName")
    private Country country;

    @Column(name = "end_time")
    private Date endTime;

    public Resident() {
    }

    public Resident(User user, Country country) {
        this.user = user;
        this.country = country;
        this.id = new ResidentId(user.getUsername(), country.getName(), new Date());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resident that = (Resident) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, country);
    }
}
