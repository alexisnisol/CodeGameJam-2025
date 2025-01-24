package com.smartdawgs.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.utils.ScreenUtils;
import com.smartdawgs.game.Main;

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


    public World(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();

        this.map=new TmxMapLoader().load("TiledMap/GameJamTiledMap.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);
        //this.layerCollision = this.map.getLayers().get("collision").getObjects();

        this.worldHeight=map.getProperties().get("height", Integer.class);
        this.worldWidth=map.getProperties().get("width", Integer.class);
        this.camera=new OrthographicCamera();
        this.viewport=new FitViewport(this.worldWidth, this.worldHeight, this.camera);
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
    }


    private void updateCamera() {
        camera.position.set(this.game.getPlayer().getX(), this.game.getPlayer().getY(),0);
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
