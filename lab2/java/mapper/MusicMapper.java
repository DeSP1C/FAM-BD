package mapper;

import dto.MusicDto;
import entity.Music;

public class MusicMapper extends AbstractMapper<Music, MusicDto> {
    private static final CatalogueMapper catalogueMapper = new CatalogueMapper();
    private static final CreatorMapper creatorMapper = new CreatorMapper();
    public MusicMapper(){
        super(
                music -> new MusicDto(
                        music.getMusicId(),
                        music.getMusicName(),
                        creatorMapper.toDto(music.getCreator()),
                        catalogueMapper.toDto(music.getCatalogue()),
                        music.getViews()
                ),
                dto -> new Music(
                        dto.musicId(),
                        dto.musicName(),
                        creatorMapper.toEntity(dto.creator()),
                        catalogueMapper.toEntity(dto.catalogue()),
                        dto.views()
                )
        );
    }

}
