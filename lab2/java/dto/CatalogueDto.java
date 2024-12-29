package dto;

public record CatalogueDto(int catalogueId, String catalogueName, UserDto user) {
    public void show() {
        System.out.printf("catalogue id = %-5d catalogue name = %-15s user id = %d\n", catalogueId, catalogueName, user.userId());
    }
}