package mapper;
import dto.CatalogueDto;
import entity.Catalogue;

public class CatalogueMapper extends AbstractMapper<Catalogue, CatalogueDto> {
    private static final  UserMapper userMapper = new UserMapper();
    public CatalogueMapper() {
        super(
                catalogue -> new CatalogueDto(
                        catalogue.getCatalogueId(),
                        catalogue.getCatalogueName(),
                        userMapper.toDto(catalogue.getUser())
                ),
                dto -> new Catalogue(
                        dto.catalogueId(),
                        dto.catalogueName(),
                        userMapper.toEntity(dto.user())
                )
        );
    }
}
