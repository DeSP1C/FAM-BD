package entity;

public class Catalogue{
    private final int catalogueId;
    private final String catalogueName;
    private final int userId;

    public Catalogue(int catalogueId, String catalogueName, int userId){
       this.catalogueId = catalogueId;
       this.catalogueName = catalogueName;
       this.userId = userId;
    }

    public int getCatalogueId() {
        return catalogueId;
    }

    public String getCatalogueName() {
        return catalogueName;
    }

    public int getUserId() {
        return userId;
    }

    public void show(){
        System.out.printf("catalogue id = %-5d catalogue name = %-15s user id = %d\n", catalogueId, catalogueName, userId);
    }
}
