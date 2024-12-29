package service;

import dto.CreatorDto;
import entity.Creator;

import java.util.ArrayList;

public interface CreatorService {
    ArrayList<CreatorDto> getAllCreators();

    CreatorDto getCreator(int id);

    void addCreator(Creator creator);

    void deleteCreator(int id);

    void updateCreator(Creator creator);

    void randomGenerateCreators(int recordCount);

    void getCreatorViews(int id);
}
