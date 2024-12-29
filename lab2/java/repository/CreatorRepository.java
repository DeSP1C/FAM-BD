package repository;

import entity.Creator;

import java.util.List;

public interface CreatorRepository {
    List<Creator> getAllCreators();

    Creator getCreator(int id);

    void addCreator(Creator creator);

    void deleteCreator(int id);

    void updateCreator(Creator creator);

    void randomGenerateCreators(int recordCount);

    void getCreatorViews(int id);
}
