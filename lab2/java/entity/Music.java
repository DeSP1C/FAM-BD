package entity;

import jakarta.persistence.*;

@Entity
@Table(name="musics")
public class Music {
    @Id
    @Column(name="music_id", length = 5, unique = true, nullable = false)
    int musicId;

    @Column(name="music_name", length = 20, nullable = false)
    String musicName;

    @Column(name="views", nullable = false)
    int views;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catalogue_id", referencedColumnName = "catalogue_id", nullable = false)
    private Catalogue catalogue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id", referencedColumnName = "creator_id", nullable = false)
    private Creator creator;

    public Music(){

    }

    public Music(int musicId, String musicName, Creator creator, Catalogue catalogue, int views){
        this.musicId = musicId;
        this.musicName = musicName;
        this.creator = creator;
        this.catalogue = catalogue;
        this.views = views;
    }

    public int getMusicId() {
        return musicId;
    }

    public String getMusicName() {
        return musicName;
    }

    public Creator getCreator() {
        return creator;
    }

    public Catalogue getCatalogue() {
        return catalogue;
    }

    public int getViews() {
        return views;
    }

    public void setCatalogue(Catalogue catalogue){
        this.catalogue = catalogue;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }
}
