package com.smartdawgs.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.smartdawgs.game.entity.EntityPlayer;
import com.smartdawgs.game.sound.SoundManager;
import com.smartdawgs.game.utils.Utils;
import com.smartdawgs.game.world.World;
import lombok.Getter;

@Getter
public class Main extends Game {

    @Getter
    private EntityPlayer player;
    @Getter
    private SoundManager soundManager;
    private BitmapFont font;
    private GlyphLayout glyphLayout;

    @Override
    public void create() {
        TextureAtlas playerAtlas = new TextureAtlas(Utils.getInternalPath("atlas/player_atlas.atlas"));
        World world = new World(this);
        this.player = new EntityPlayer(playerAtlas, world);
        soundManager = new SoundManager();
        soundManager.playMusic();
        this.setScreen(world);
        this.font = new BitmapFont();
        this.glyphLayout = new GlyphLayout();
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
