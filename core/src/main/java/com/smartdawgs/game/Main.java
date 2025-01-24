package com.smartdawgs.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.smartdawgs.game.entity.EntityItemBook;
import com.smartdawgs.game.entity.EntityPlayer;
import com.smartdawgs.game.gui.DialogPanel;
import com.smartdawgs.game.sound.SoundManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    private EntityPlayer player;
    private SoundManager soundManager;
    private DialogPanel dialogPanel;
    private Stage stage;
    private SpriteBatch batch;
    private ScreenViewport viewport;
    private EntityItemBook book;
    private boolean gamePaused = false;

    @Override
    public void create() {
        viewport = new ScreenViewport();
        stage = new Stage(viewport);

        player = new EntityPlayer();
        //setScreen(new GameScreen(this));
        dialogPanel = new DialogPanel(stage);
        book = new EntityItemBook("book", "This is a book", dialogPanel);
        soundManager = new SoundManager();
        soundManager.playMusic();
    }

    @Override
    public void render() {
        // reset du screen
        ScreenUtils.clear(0, 0, 0.2f, 1);
        super.render();
        // jeu posé en cas de dialog au autre
        if (!gamePaused) {

            float delta = 1 / 1000f;
            soundManager.musicVolumeDown(delta);


            gamePaused = true;
        } else {
            // affichage du dialog

            book.interract();
            // TODO : unpause

        }
    }

    @Override
    public void dispose() {
        super.dispose();
        soundManager.dispose();
    }
}
