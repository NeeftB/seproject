package nl.pse.site.seproject.model;

import javax.persistence.*;

@Entity
@Table(name = "Photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private int id;

    @Column(name = "path")
    private String path;

    @Column(name = "media_type")
    private String mediaType;

    @Column(name = "media_name")
    private String mediaName;

    @Column(name = "size")
    private long size;

    @ManyToOne(fetch = FetchType.LAZY)
    private Report report;

    public Photo() {
    }

    public Photo(String path, String mediaType, String mediaName) {
        this.path = path;
        this.mediaType = mediaType;
        this.mediaName = mediaName;
    }

    public void setId(int id) { this.id = id; }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
