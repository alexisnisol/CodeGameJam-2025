package com.smartdawgs.game.sound;

import com.badlogic.gdx.utils.Disposable;
import games.rednblack.miniaudio.MASound;
import games.rednblack.miniaudio.MiniAudio;

public class SoundManager implements Disposable {
    private final MiniAudio miniAudio;
    private final MASound music;
    private float musicVolume;

    public SoundManager() {
        miniAudio = new MiniAudio();
        music = miniAudio.createSound("dark_fantasy.mp3");
        musicVolume = 1f;
    }

    public void playMusic() {
        music.play();
    }

    public void musicVolumeUp(float volumeUnit) {
        this.musicVolume += volumeUnit;
        music.setVolume(musicVolume);
    }

    public void musicVolumeDown(float volumeUnit) {
        if (this.musicVolume - volumeUnit < 0) {
            this.musicVolume = 0;
        } else {
            this.musicVolume -= volumeUnit;
        }
        music.setVolume(musicVolume);
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
