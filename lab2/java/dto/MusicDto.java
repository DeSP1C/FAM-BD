package dto;


public record MusicDto(int musicId, String musicName, CreatorDto creator, CatalogueDto catalogue, int views) {
    public void show() {
        System.out.printf("music id = %-5d music name = %-20s creator id = %-5d catalogue id = %-5d views = %d\n", musicId, musicName, creator.creatorId(), catalogue.catalogueId(), views);
    }
}