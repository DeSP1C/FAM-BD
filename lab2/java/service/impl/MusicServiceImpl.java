package service.impl;

import dto.MusicDto;
import entity.Music;
import mapper.MusicMapper;
import repository.MusicRepository;
import service.MusicService;
import validation.Validation;
import util.Error;
import java.util.ArrayList;
import java.util.List;

public class MusicServiceImpl implements MusicService {
    private final MusicRepository musicRepository;
    private final MusicMapper mapper;
    private final Error error;

    public MusicServiceImpl(MusicRepository musicRepository, Error error){
        this.musicRepository = musicRepository;
        this.mapper = new MusicMapper();
        this.error = error;
    }

    private boolean ValidationMusic(Music music){
        if (!Validation.isID(music.getMusicId())) {
            System.out.println("Music id entered incorrectly.");
            return false;
        }
        if (!Validation.isCatalogueName(music.getMusicName())) {
            System.out.println("Music name entered incorrectly");
            return false;
        }
        if (!Validation.isID(music.getCatalogue().getCatalogueId())) {
            System.out.println("Catalogue id entered incorrectly.");
            return false;
        }
        if (!Validation.isID(music.getCreator().getCreatorId())) {
            System.out.println("Creator id entered incorrectly.");
            return false;
        }
        if (!Validation.isView(music.getViews())) {
            System.out.println("Number of views entered incorrectly.");
            return false;
        }

        return true;
    }

    private boolean ValidationId(int id){
        if (!Validation.isID(id)) {
            System.out.println("Music id entered incorrectly.");
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<MusicDto> getAllMusics(){
        List<Music> musicList = musicRepository.getAllMusics();
        ArrayList<MusicDto> musicDtoList = new ArrayList<>();

        if(musicList == null || musicList.isEmpty()){
            System.out.println("Music table is empty");
            return null;
        }
        for(Music m : musicList)
            musicDtoList.add(mapper.toDto(m));

        return musicDtoList;
    }

    @Override
    public MusicDto getMusic(int id){
        if(!ValidationId(id))
            return null;
        if(!error.isMusicIdExists(id)){
            System.out.println("This music id is not exist. ");
            return null;
        }

        Music music = musicRepository.getMusic(id);
        return mapper.toDto(music);
    }

    @Override
    public void addMusic(Music music){
        if(!ValidationMusic(music))
            return;
        if(error.isMusicIdExists(music.getMusicId())){
            System.out.println("The music with this id is already taken");
            return;
        }

        musicRepository.addMusic(music);
    }

    @Override
    public void deleteMusic(int id){
        if(!ValidationId(id))
            return;
        if (!error.isMusicIdExists(id)) {
            System.out.println("The music with this id does not exist.");
            return;
        }

        musicRepository.deleteMusic(id);

    }

    @Override
    public void updateMusic(Music music){
        if(!ValidationMusic(music))
            return;
        if (!error.isMusicIdExists(music.getMusicId())) {
            System.out.println("The music with this id does not exist.");
            return;
        }

        musicRepository.updateMusic(music);
    }

    @Override
    public void getPopularMusic(){
        musicRepository.getPopularMusic();
    }
}
