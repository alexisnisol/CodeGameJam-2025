package com.smartdawgs.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.smartdawgs.game.entity.EntityPlayer;
import com.smartdawgs.game.sound.SoundManager;
import com.smartdawgs.game.world.HouseEnigme1;
import com.smartdawgs.game.world.World;
import lombok.Getter;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    @Getter
    private EntityPlayer player;
    private SoundManager soundManager;

    @Override
    public void create() {
        TextureAtlas playerAtlas = new TextureAtlas(Utils.getInternalPath("atlas/player_atlas.atlas"));
        player = new EntityPlayer(playerAtlas);
        this.setScreen(new World(this));
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
