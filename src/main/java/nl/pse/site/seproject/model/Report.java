package nl.pse.site.seproject.model;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Report")
public class Report {

    @Id
    @Column(name = "report_number")
    private String reportNumber;

    @Column(name = "title")
    private String title;

    @Column(name = "country")
    private String country;

    @Column(name = "region")
    private String region;

    @Column(name = "category")
    private String category;

    @Column(name = "paragraph_one",
            length = 65535,
            columnDefinition = "text")
    private String paragraphOne;

    @Column(name = "paragraph_two",
            length = 65535,
            columnDefinition = "text")
    private String paragraphTwo;

    @Column(name = "paragraph_three",
            length = 65535,
            columnDefinition = "text")
    private String paragraphThree;

    @Column(name = "published")
    private Boolean published;

    @Column(name = "amount_of_votes")
    private int amountOfVotes;

    @Column(name = "ranking")
    private double ranking;

    @Column(name = "views")
    private int views;

    @Column(name = "timestamp")
    private Date timestamp;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonbTransient
    private Set<Photo> photos = new HashSet<>();

    @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Report() {
    }


    public Report(String title, String country, String region, String category, String paragraphOne,
                  String paragraphTwo, String paragraphThree, Boolean published) {
        this.reportNumber = "";
        this.title = title;
        this.country = country;
        this.region = region;
        this.category = category;
        this.paragraphOne = paragraphOne;
        this.paragraphTwo = paragraphTwo;
        this.paragraphThree = paragraphThree;
        this.timestamp = null;
        this.published = published;
        this.amountOfVotes = 0;
        this.ranking = 0.0;
        this.views = 0;
    }

    public Report(String reportNumber, String title, String country, String region, String category, String paragraphOne,
                  String paragraphTwo, String paragraphThree, Date timestamp, Boolean published, int amountOfVotes,
                  double ranking, int views) {
        this.reportNumber = reportNumber;
        this.title = title;
        this.country = country;
        this.region = region;
        this.category = category;
        this.paragraphOne = paragraphOne;
        this.paragraphTwo = paragraphTwo;
        this.paragraphThree = paragraphThree;
        this.timestamp = timestamp;
        this.published = published;
        this.amountOfVotes = amountOfVotes;
        this.ranking = ranking;
        this.views = views;
    }

    public String getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(String reportNumber) {
        this.reportNumber = reportNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getParagraphOne() {
        return paragraphOne;
    }

    public void setParagraphOne(String paragraphOne) {
        this.paragraphOne = paragraphOne;
    }

    public String getParagraphTwo() {
        return paragraphTwo;
    }

    public void setParagraphTwo(String paragraphTwo) {
        this.paragraphTwo = paragraphTwo;
    }

    public String getParagraphThree() {
        return paragraphThree;
    }

    public void setParagraphThree(String paragraphThree) {
        this.paragraphThree = paragraphThree;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public int getAmountOfVotes() {
        return amountOfVotes;
    }

    public void setAmountOfVotes(int amountOfVotes) {
        this.amountOfVotes = amountOfVotes;
    }

    public double getRanking() {
        return ranking;
    }

    public void setRanking(double ranking) {
        this.ranking = ranking;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void addPhoto(Photo photo) {
        getPhotos().add(photo);
        photo.setReport(this);
    }

    public void deletePhoto(Photo photo) {
        getPhotos().remove(photo);
        photo.setReport(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(reportNumber, report.reportNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportNumber);
    }
}
