package com.smartdawgs.game.sound;

import com.badlogic.gdx.utils.Disposable;
import games.rednblack.miniaudio.MASound;
import games.rednblack.miniaudio.MiniAudio;

public class SoundManager implements Disposable {
    private final MiniAudio miniAudio;
    private final MASound music;

    public SoundManager() {
        miniAudio = new MiniAudio();
        music = miniAudio.createSound("dark_fantasy.mp3");
    }

    public void playMusic() {
        music.play();
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
