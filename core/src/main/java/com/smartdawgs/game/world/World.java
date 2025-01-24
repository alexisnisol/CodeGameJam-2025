package com.smartdawgs.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.utils.ScreenUtils;
import com.smartdawgs.game.Main;
import com.smartdawgs.game.entity.items.EntityItemBook;
import com.smartdawgs.game.gui.DialogPanel;

public class World implements Screen {
    private Main game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    float worldWidth;
    float worldHeight;
    final float unitScale = 1f / 32f;
    private MapObjects layerCollision;
    private float oldX;
    private float oldY;

    private Stage stage;
    private DialogPanel dialogPanel;

    private EntityItemBook book;

    private float time = 0f;


    public World(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();

        this.map=new TmxMapLoader().load("TiledMap/GameJamTiledMap.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);
        //this.layerCollision = this.map.getLayers().get("collision").getObjects();

        this.worldHeight=map.getProperties().get("height", Integer.class)*32f;
        this.worldWidth=map.getProperties().get("width", Integer.class)*32f;
        this.camera=new OrthographicCamera();
        this.viewport=new FitViewport(this.worldWidth, this.worldHeight, this.camera);

        this.stage = new Stage(viewport);
        this.dialogPanel = new DialogPanel();
        this.stage.addActor(dialogPanel.getTable());
        book = new EntityItemBook("book", "a book", dialogPanel);
        book.interact();
    }

    @Override
    public void show() {
        this.oldX = this.game.getPlayer().getX();
        this.oldY = this.game.getPlayer().getY();
    }

    @Override
    public void render(float v) {
        this.game.getPlayer().update(Gdx.graphics.getDeltaTime());
        logic();
        draw();
    }

    public void logic() {
        updateCamera();
        collision();
        playerLimit();
        System.out.println(this.dialogPanel.getTable().isVisible());
    }


    private void updateCamera() {
        camera.position.set(this.game.getPlayer().getX(), this.game.getPlayer().getY(),0);
        camera.zoom = 0.2f;
        camera.update();
    }

    private void collision() {
//        for (MapObject object : this.layerCollision) {
//            if (object instanceof PolygonMapObject) {
//                Polygon obstaclePolygon = ((PolygonMapObject) object).getPolygon();
//                if (Intersector.overlapConvexPolygons(game.getPlayer().getPlayerPolygon(), obstaclePolygon)) {
//                    // Collision détectée
//                    game.getPlayer().setPosition(this.oldX, this.oldY);
//                    game.getPlayer().getPlayerPolygon().setPosition(this.oldX, this.oldY);
//                }
//            }
//        }
        // Sauvegarder la position actuelle
        this.oldX = game.getPlayer().getX();
        this.oldY = game.getPlayer().getY();
    }


    public void draw() {
        ScreenUtils.clear(0, 0, 0, 1);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        mapRenderer.setView(camera);
        mapRenderer.render();
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.polygon(game.getPlayer().getPlayerPolygon().getTransformedVertices());
        shapeRenderer.end();
        // delta entre 2 frames
        float delta = Gdx.graphics.getDeltaTime();
        time += delta;
        if (time > 5) {
            this.dialogPanel.getTable().setVisible(false);
        }
        this.dialogPanel.reposition(oldX, oldY);
        // Mise à jour du stage
        stage.act(delta);
        stage.draw();

        batch.begin();

        game.getPlayer().draw(batch);

        batch.end();
    }

    private void playerLimit() {
        float playerX = this.game.getPlayer().getX();
        float playerY = this.game.getPlayer().getY();

        if (playerX < 0) {
            playerX = 0;
        }
        else if (playerX > worldWidth) {
            playerX = worldWidth;
        }
        else if (playerY < 0) {
            playerY = 0;
        }
        else if (playerY > worldHeight) {
            playerY = worldHeight;
        }
        this.game.getPlayer().setPosition(playerX, playerY);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        game.dispose();
        mapRenderer.dispose();
        map.dispose();
    }
}
