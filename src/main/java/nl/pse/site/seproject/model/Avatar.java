package nl.pse.site.seproject.model;

import javax.persistence.*;

@Entity
@Table(name = "Avatar")
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatar_id")
    private int id;

    @Column(name = "path")
    private String path;

    public Avatar() {
    }

    public Avatar(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

