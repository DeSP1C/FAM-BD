package service.impl;

import dto.CreatorDto;
import entity.Creator;
import mapper.CreatorMapper;
import repository.CreatorRepository;
import service.CreatorService;
import util.Error;
import validation.Validation;

import java.util.ArrayList;
import java.util.List;

public class CreatorServiceImpl implements CreatorService{
    private final CreatorRepository creatorRepository;
    private final CreatorMapper mapper;
    private final Error error;

    public CreatorServiceImpl(CreatorRepository creatorRepository, Error error){
        this.creatorRepository = creatorRepository;
        this.mapper = new CreatorMapper();
        this.error = error;
    }

    private boolean ValidationCreator(Creator creator){
        if (!Validation.isID(creator.getCreatorId())) {
            System.out.println("Creator id entered incorrectly.");
            return false;
        }
        if (!Validation.isName(creator.getFirstName())) {
            System.out.println("First name entered incorrectly");
            return false;
        }
        if (!Validation.isName(creator.getLastName())) {
            System.out.println("Last name entered incorrectly");
            return false;
        }
        return true;
    }

    private boolean ValidationId(int id){
        if (!Validation.isID(id)) {
            System.out.println("Creator id entered incorrectly.");
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<CreatorDto> getAllCreators(){
        List<Creator> creatorList = creatorRepository.getAllCreators();
        ArrayList<CreatorDto> creatorDtoList = new ArrayList<>();

        if(creatorList == null || creatorList.isEmpty()){
            System.out.println("User table is empty");
            return null;
        }

        for(Creator c : creatorList)
            creatorDtoList.add(mapper.toDto(c));

        return creatorDtoList;
    }

    @Override
    public CreatorDto getCreator(int id){
        if(!ValidationId(id))
            return null;

        if (!error.isCreatorIdExists(id)) {
            System.out.println("The creator with this id does not exist.");
            return null;
        }

        Creator creator = creatorRepository.getCreator(id);
        return mapper.toDto(creator);
    }

    @Override
    public void addCreator(Creator creator){
        if(!ValidationCreator(creator))
            return;
        if(error.isCreatorIdExists(creator.getCreatorId())){
            System.out.println("The creator with this id is already taken");
        }

        creatorRepository.addCreator(creator);
    }
    @Override
    public void deleteCreator(int id){
        if(!ValidationId(id))
            return;
        if(!error.isCreatorIdExists(id)){
            System.out.println("The creator with this id does not exist.");
            return;
        }
        creatorRepository.deleteCreator(id);
    }

    @Override
    public void updateCreator(Creator creator){
        if(!ValidationCreator(creator))
            return;
        if(!error.isCreatorIdExists(creator.getCreatorId())){
            System.out.println("The creator with this id does not exist.");
            return;
        }

        creatorRepository.updateCreator(creator);
    }

    @Override
    public void randomGenerateCreators(int recordCount){
        if(!ValidationId(recordCount)){
            System.out.println("This number is unacceptable");
            return;
        }

        creatorRepository.randomGenerateCreators(recordCount);
    }

    @Override
    public void getCreatorViews(int id){
        if (!Validation.isID(id)) {
            System.out.println("Creator ID entered incorrectly.");
            return;
        }

        if (!error.isCreatorIdExists(id)) {
            System.out.println("This creator id does not exist.");
            return;
        }

        creatorRepository.getCreatorViews(id);
    }
}
