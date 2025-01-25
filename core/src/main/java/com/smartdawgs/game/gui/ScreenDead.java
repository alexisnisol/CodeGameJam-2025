package com.smartdawgs.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smartdawgs.game.Main;

public class ScreenDead implements Screen {
    private final SpriteBatch batch;
    private final Main game;
    private Texture deadTexture;
    private float stateTime = 0;
    private int width;
    private int height;

    public ScreenDead(Main game) {
        super();
        this.game = game;
        this.batch = new SpriteBatch();
        this.width = Gdx.graphics.getWidth();
        this.height = Gdx.graphics.getHeight();
    }

    @Override
    public void show() {
        deadTexture = new Texture("mort.png");
    }

    @Override
    public void render(float delta) {
        stateTime += delta;

        if ((stateTime > 2 && Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) || stateTime > 10) {
            game.reset();
        }
        batch.begin();
        batch.draw(deadTexture, 0, 0, width, height);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (deadTexture != null) {
            deadTexture.dispose();
        }
    }
}
