package service.impl;

import dto.CatalogueDto;
import entity.Catalogue;
import mapper.CatalogueMapper;
import repository.CatalogueRepository;
import service.CatalogueService;
import util.Error;
import validation.Validation;

import java.util.ArrayList;
import java.util.List;

public class CatalogueServiceImpl implements CatalogueService{
    private final CatalogueRepository catalogueRepository;
    private final CatalogueMapper mapper;
    private final Error error;

    public CatalogueServiceImpl(CatalogueRepository catalogueRepository, Error error){
        this.catalogueRepository = catalogueRepository;
        this.mapper = new CatalogueMapper();
        this.error = error;
    }

    private boolean ValidationCatalogue(Catalogue catalogue){
        if (!Validation.isID(catalogue.getCatalogueId())) {
            System.out.println("Catalogue id entered incorrectly.");
            return false;
        }
        if (!Validation.isID(catalogue.getUser().getUserId())) {
            System.out.println("User id entered incorrectly.");
            return false;
        }
        if (!Validation.isCatalogueName(catalogue.getCatalogueName())) {
            System.out.println("Catalogue name entered incorrectly");
            return false;
        }
        return true;
    }

    private boolean ValidationId(int id){
        if (!Validation.isID(id)) {
            System.out.println("Catalogue id entered incorrectly.");
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<CatalogueDto> getAllCatalogues(){
        List<Catalogue> cataloguesList = catalogueRepository.getAllCatalogues();
        ArrayList<CatalogueDto> cataloguesDtoList = new ArrayList<>();

        if(cataloguesList == null || cataloguesList.isEmpty()){
            System.out.println("Catalogue table is empty");
            return null;
        }

        for(Catalogue c : cataloguesList)
            cataloguesDtoList.add(mapper.toDto(c));


        return cataloguesDtoList;
    }

    @Override
    public CatalogueDto getCatalogue(int id){
        if(!ValidationId(id))
            return null;

        if (!error.isCatalogueIdExists(id)) {
            System.out.println("This catalogue id is not exist. ");
            return null;
        }

        Catalogue catalogue = catalogueRepository.getCatalogue(id);
        return mapper.toDto(catalogue);
    }

    @Override
    public void addCatalogue(Catalogue catalogue){
        if(!ValidationCatalogue(catalogue))
            return;
        if(error.isCatalogueIdExists(catalogue.getCatalogueId())){
            System.out.println("The catalogue with this id is already taken");
            return;
        }

        catalogueRepository.addCatalogue(catalogue);
    }

    @Override
    public void deleteCatalogue(int id){
        if(!ValidationId(id))
            return;
        if (!error.isCatalogueIdExists(id)) {
            System.out.println("The catalogue with this id does not exist.");
            return;
        }

        catalogueRepository.deleteCatalogue(id);
    }

    @Override
    public void updateCatalogue(Catalogue catalogue){
        if(!ValidationCatalogue(catalogue))
            return;
        if(!error.isCatalogueIdExists(catalogue.getCatalogueId())){
            System.out.println("The catalogue with this id does not exist.");
            return;
        }

        catalogueRepository.updateCatalogue(catalogue);
    }

    @Override
    public void getBiggestCatalogue(){
        catalogueRepository.getBiggestCatalogue();
    }
}
