package com.smartdawgs.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.smartdawgs.game.entity.EntityPlayer;
import com.smartdawgs.game.sound.SoundManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    private EntityPlayer player;
    private SoundManager soundManager;

    public EntityPlayer getPlayer() {
        return player;
    }

    @Override
    public void create() {
        player = new EntityPlayer();
        //setScreen(new GameScreen(this));
        soundManager = new SoundManager();
        soundManager.playMusic();
    }

    @Override
    public void render() {
        super.render();

        float delta = 1 / 1000f;
        soundManager.musicVolumeDown(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
        soundManager.dispose();
    }
}
