package entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="catalogues")
public class Catalogue {
    @Id
    @Column(name="catalogue_id", unique=true, nullable = false)
    int catalogueId;

    @Column(name="catalogue_name", length=20, nullable = false)
    String catalogueName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "catalogue", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Music> musics;

    public Catalogue(){

    }

    public Catalogue(int catalogueId, String catalogueName, User user){
        this.catalogueId = catalogueId;
        this.catalogueName = catalogueName;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCatalogueId() {
        return catalogueId;
    }

    public String getCatalogueName() {
        return catalogueName;
    }


    public User getUser() {
        return user;
    }
}
