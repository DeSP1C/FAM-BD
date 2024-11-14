package entity.additional;

public class CreatorStats {
    private final String firstName;
    private final String lastName;
    private final int views;

    public CreatorStats(String firstName, String lastName, int views){
        this.firstName = firstName;
        this.lastName = lastName;
        this.views = views;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getViews() {
        return views;
    }

    public void show(){
        System.out.printf("Creator: %s %s. Total views: %d%n", firstName, lastName, views);
    }
}
