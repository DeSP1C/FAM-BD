package service;

import dto.MusicDto;
import entity.Music;

import java.util.ArrayList;

public interface MusicService {
    ArrayList<MusicDto> getAllMusics();

    MusicDto getMusic(int id);

    void addMusic(Music music);

    void deleteMusic(int id);

    void updateMusic(Music music);

    void getPopularMusic();
}
