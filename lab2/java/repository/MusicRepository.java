package repository;

import entity.Music;

import java.util.List;

public interface MusicRepository {
    List<Music> getAllMusics();

    Music getMusic(int id);

    void addMusic(Music music);

    void deleteMusic(int id);

    void updateMusic(Music music);

    void getPopularMusic();
}
