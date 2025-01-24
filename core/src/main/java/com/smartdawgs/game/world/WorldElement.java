package com.smartdawgs.game.world;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.smartdawgs.game.Main;

public abstract class WorldElement implements Screen {
    protected Main game;
    protected SpriteBatch batch;
    protected OrthographicCamera camera;
    protected FitViewport viewport;
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer mapRenderer;
    protected float worldWidth;
    protected float worldHeight;

    public WorldElement(Main game, String nameTiledMap){
        this.game = game;
        this.batch = new SpriteBatch();

        this.map=new TmxMapLoader().load("TiledMap/"+ nameTiledMap + ".tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);
        //this.layerCollision = this.map.getLayers().get("collision").getObjects();

        this.worldHeight=map.getProperties().get("height", Integer.class)*32f;
        this.worldWidth=map.getProperties().get("width", Integer.class)*32f;
        this.camera=new OrthographicCamera();
        this.viewport=new FitViewport(this.worldWidth, this.worldHeight, this.camera);
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
        this.map.dispose();
        this.mapRenderer.dispose();
        this.batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height);
    }

    @Override
    public void render(float v) {

    }

    @Override
    public void show() {

    }
}
