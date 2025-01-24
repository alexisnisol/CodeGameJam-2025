package com.smartdawgs.game.world;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.smartdawgs.game.Main;

public class HouseEnigme1 implements Screen {
    private Main game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    float worldWidth;
    float worldHeight;

    public HouseEnigme1(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();

        this.map=new TmxMapLoader().load("TiledMap/TestBillard.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);
        //this.layerCollision = this.map.getLayers().get("collision").getObjects();

        this.worldHeight=map.getProperties().get("height", Integer.class)*32f;
        this.worldWidth=map.getProperties().get("width", Integer.class)*32f;
        this.camera=new OrthographicCamera();
        this.viewport=new FitViewport(this.worldWidth, this.worldHeight, this.camera);
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

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

    }
}
