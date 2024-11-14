package entity;

public class Music {
    int musicId;
    String musicName;
    int creatorId;
    int catalogueId;
    int views;

    public Music(int musicId, String musicName, int creatorId, int catalogueId, int views){
        this.musicId = musicId;
        this.musicName = musicName;
        this.creatorId = creatorId;
        this.catalogueId = catalogueId;
        this.views = views;
    }

    public int getMusicId() {
        return musicId;
    }

    public String getMusicName() {
        return musicName;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public int getCatalogueId() {
        return catalogueId;
    }

    public int getViews() {
        return views;
    }

    public void show(){
        System.out.printf("music id = %-5d music name = %-20s creator id = %-5d catalogue id = %-5d views = %d\n", musicId, musicName, creatorId, catalogueId, views);
    }
}
