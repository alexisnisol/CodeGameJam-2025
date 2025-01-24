package com.smartdawgs.game.sound;

import com.badlogic.gdx.utils.Disposable;
import games.rednblack.miniaudio.MASound;
import games.rednblack.miniaudio.MiniAudio;

public class SoundManager implements Disposable {
    private final MiniAudio miniAudio;
    private MASound music;
    private float musicVolume;
    private float facteurVolume = 1f;

    public SoundManager() {
        miniAudio = new MiniAudio();
        musicVolume = 1f;
        randomMusic();
    }

    // enumération de toutes le musiques
    private enum Music {
        MUSIC_1("musiques/dark_fantasy.mp3"),
        MUSIC_2("musiques/beethoven.mp3"),
        MUSIC_3("musiques/epic_intro.mp3"),
        MUSIC_4("musiques/halloween.mp3");

        private final String path;

        Music(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    public void setFacteurVolume(float facteurVolume) {
        this.facteurVolume = facteurVolume;
    }

    private void randomMusic() {
        Music[] musics = Music.values();
        int random = (int) (Math.random() * musics.length);
        music = miniAudio.createSound(musics[random].getPath());
        music.setVolume(musicVolume);
        music.setLooping(true);
    }

    public void playMusic() {
        music.play();
    }

    public void musicVolumeUp(float volumeUnit) {
        this.musicVolume += volumeUnit;
        if (this.musicVolume < 1){
            music.setVolume(musicVolume);
        }
    }

    public void musicVolumeDown(float volumeUnit) {
        if (this.musicVolume - (volumeUnit) < 0) {
            this.musicVolume = 0;
        } else if (this.musicVolume <= 1f) {
            this.musicVolume -= volumeUnit * facteurVolume;
            music.setVolume(musicVolume);
        }
    }

    public void stopMusic() {
        music.stop();
    }

    public void pauseMusic() {
        music.pause();
    }

    public void stopEngine() {
        miniAudio.stopEngine();
    }

    public void resumeEngine() {
        miniAudio.startEngine();
    }

    @Override
    public void dispose() {
        music.dispose();
        miniAudio.dispose();
    }
}
