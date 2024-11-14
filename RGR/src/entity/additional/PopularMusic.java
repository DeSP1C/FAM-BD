package entity.additional;

public class PopularMusic {
    private final String firstName;
    private final String lastName;
    private final String musicName;
    private final int views;

    public PopularMusic(String firstName, String lastName, String musicName, int views){
        this.firstName = firstName;
        this.lastName = lastName;
        this.musicName = musicName;
        this.views = views;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMusicName() {
        return musicName;
    }

    public int getViews() {
        return views;
    }

    public void show(){
        System.out.printf("Owner: %s %s. Music: %s. Views: %d%n", firstName, lastName, musicName, views);
    }
}
