package com.smartdawgs.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.smartdawgs.game.EntityRegister;
import com.smartdawgs.game.Main;
import com.smartdawgs.game.entity.Entity;
import com.smartdawgs.game.gui.DialogPanel;
import com.smartdawgs.game.utils.CollisionUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class WorldElement implements Screen {
    @Getter
    protected Main game;

    protected float stateTime = 0;
    protected float deltaTest = 0;

    protected SpriteBatch batch;
    private SpriteBatch hudBatch;
    @Getter
    private DialogPanel dialogPanel;

    protected OrthographicCamera camera;
    protected FitViewport viewport;
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer mapRenderer;
    @Getter
    protected float worldWidth;
    @Getter
    protected float worldHeight;

    protected Stage stage;
    protected Label labelInteraction;
    protected BitmapFont bitmapFont;
    protected String labAction = "";

    protected MapObjects layerCollision;
    protected float oldX;
    protected float oldY;

    @Getter
    protected List<Entity> entities;

    public WorldElement(Main game, String nameTiledMap) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.hudBatch = new SpriteBatch();
        this.dialogPanel = new DialogPanel(this.game);

        this.entities = new ArrayList<>();

        this.map = new TmxMapLoader().load("TiledMap/" + nameTiledMap + ".tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);
        this.worldHeight = map.getProperties().get("height", Integer.class) * 32f;
        this.worldWidth = map.getProperties().get("width", Integer.class) * 32f;
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(this.worldWidth, this.worldHeight, this.camera);

        this.layerCollision = map.getLayers().get("collision").getObjects();

        // Initialisation de Stage et de BitmapFont
        this.stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        //TODO : Use font from Main, Replace Stage by draw font
        bitmapFont = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;

        // Création du label
        labelInteraction = new Label("Appuyez sur la touche 'F'", labelStyle);

        stage.addActor(labelInteraction);

        EntityRegister.registerEntities(this);
    }

    protected void init(){
    }

    protected void updateCamera() {
        camera.position.set(this.game.getPlayer().getX(), this.game.getPlayer().getY(), 0);
        camera.zoom = 0.2f;
        camera.update();
    }

    public void logic() {
        updateCamera();
        collision();
        updateEntities(Gdx.graphics.getDeltaTime());
    }

    public void preRender() {
        ScreenUtils.clear(0, 0, 0, 1);
        this.viewport.apply();
        batch.setProjectionMatrix(this.viewport.getCamera().combined);
    }

    public void postRender() {
        batch.begin();
        for(Entity entity : this.entities){
            entity.draw(batch);
        }

        this.game.getPlayer().draw(batch);
        batch.end();


        hudBatch.begin();
        dialogPanel.getTable().draw(hudBatch, dialogPanel.getParentAlpha());
        this.game.getPlayer().getInventory().draw(hudBatch);
        hudBatch.end();

        stage.act();
        stage.draw();

    }

    public void updateEntities(float delta){
        this.game.getPlayer().update(delta);
        for(Entity entity : entities){
            entity.update(delta);
        }
    }

    protected void collision() {
        for (MapObject object : this.layerCollision) {
            if (object instanceof RectangleMapObject) {
                CollisionUtils.checkRectangleCollision((RectangleMapObject) object, this.game.getPlayer(), this.oldX, this.oldY);
            } else if (object instanceof PolygonMapObject) {
                CollisionUtils.checkPolygonRectangle((PolygonMapObject) object, this.game.getPlayer(), this.oldX, this.oldY);
            }
        }
        // Sauvegarder la position actuelle pour la prochaine itération
        this.oldX = game.getPlayer().getX();
        this.oldY = game.getPlayer().getY();
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
    public void render(float delta) {
        this.stateTime += delta;
    }

    @Override
    public void show() {
        this.oldX = this.game.getPlayer().getX();
        this.oldY = this.game.getPlayer().getY();
        this.labelInteraction.setPosition(game.getPlayer().getX(), game.getPlayer().getY() + 10);
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

}
