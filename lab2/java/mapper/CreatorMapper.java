package mapper;

import dto.CreatorDto;
import entity.Creator;

public class CreatorMapper extends AbstractMapper<Creator, CreatorDto>{
    public CreatorMapper(){
        super(
                creator -> new CreatorDto(
                        creator.getCreatorId(),
                        creator.getFirstName(),
                        creator.getLastName()
                ),
                dto -> new Creator(
                        dto.creatorId(),
                        dto.firstName(),
                        dto.lastName()
                )
        );
    }
}
