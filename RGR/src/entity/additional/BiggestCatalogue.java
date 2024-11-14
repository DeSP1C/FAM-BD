package entity.additional;

public class BiggestCatalogue {
    private final String firstName;
    private final String lastName;
    private final String catalogueName;
    private final int totalMusic;

    public BiggestCatalogue(String firstName, String lastName, String catalogueName, int totalMusic){
        this.firstName = firstName;
        this.lastName = lastName;
        this.catalogueName = catalogueName;
        this.totalMusic = totalMusic;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCatalogueName() {
        return catalogueName;
    }

    public int getTotalMusic() {
        return totalMusic;
    }

    public void show(){
        System.out.printf("Owner: %s %s. Catalogue: %s. Total music: %d%n", firstName, lastName, catalogueName, totalMusic);
    }
}
