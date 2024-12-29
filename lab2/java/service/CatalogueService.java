package service;

import dto.CatalogueDto;
import entity.Catalogue;
import java.util.ArrayList;

public interface CatalogueService {
    ArrayList<CatalogueDto> getAllCatalogues();

    CatalogueDto getCatalogue(int id);

    void addCatalogue(Catalogue catalogue);

    void deleteCatalogue(int id);

    void updateCatalogue(Catalogue catalogue);

    void getBiggestCatalogue();
}
