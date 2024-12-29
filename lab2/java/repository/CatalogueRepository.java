package repository;

import entity.Catalogue;

import java.util.List;

public interface CatalogueRepository {
    List<Catalogue> getAllCatalogues();

    Catalogue getCatalogue(int id);

    void addCatalogue(Catalogue catalogue);

    void deleteCatalogue(int id);

    void updateCatalogue(Catalogue catalogue);

    void getBiggestCatalogue();
}
