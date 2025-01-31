package com.smartdawgs.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.smartdawgs.game.entity.EntityPlayer;
import com.smartdawgs.game.gui.GuiHUD;
import com.smartdawgs.game.gui.ScreenDead;
import com.smartdawgs.game.sound.SoundManager;
import com.smartdawgs.game.utils.Utils;
import com.smartdawgs.game.world.World;
import com.smartdawgs.game.world.WorldElement;
import lombok.Getter;

@Getter
public class Main extends Game {

    @Getter
    private EntityPlayer player;
    @Getter
    private SoundManager soundManager;
    private BitmapFont font;
    private GlyphLayout glyphLayout;

    private World world;
    private GuiHUD hud;

    @Override
    public void create() {
        TextureAtlas playerAtlas = new TextureAtlas(Utils.getInternalPath("atlas/player_atlas.atlas"));
        this.world = new World(this);
        this.player = new EntityPlayer(playerAtlas, world);
        soundManager = new SoundManager();
        soundManager.playMusic();
        this.setScreen(world);
        this.font = new BitmapFont();
        this.glyphLayout = new GlyphLayout();

        this.hud = new GuiHUD(world);
    }

    public void reset() {
        dispose();
        create();
    }

    @Override
    public void render() {
        super.render();

        float delta = 1 / 1000f;
        soundManager.musicVolumeDown(delta);

        if(this.soundManager.getMusicVolume() == 0) {
            this.youAreDead();
        }

        this.hud.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        if (soundManager != null) {
            soundManager.dispose();
        }
        if (font != null) {
            font.dispose();
        }

        //TODO : Check if it's necessary to dispose the world and player

        if (player != null) {
            player.dispose();
        }
    }

    @Override
    public void setScreen(Screen screen) {
        if(this.getPlayer() != null && screen instanceof WorldElement) {
            this.getPlayer().setWorld((WorldElement) screen);
        }
        super.setScreen(screen);
    }

    public void youAreDead() {
        dispose();
        ScreenDead screenDead = new ScreenDead(this);
        this.setScreen(screenDead);
        soundManager = new SoundManager();
    }
    public void addTime(){
    }
}
