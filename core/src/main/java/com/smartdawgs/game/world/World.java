package com.smartdawgs.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.smartdawgs.game.EntityRegister;
import com.smartdawgs.game.Main;
import com.smartdawgs.game.entity.Entity;
import com.smartdawgs.game.gui.DialogPanel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class World implements Screen {
    @Getter
    private Main game;
    private SpriteBatch batch;
    private SpriteBatch hudBatch;
    @Getter
    private OrthographicCamera camera;
    @Getter
    private FitViewport viewport;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    float worldWidth;
    float worldHeight;
    final float unitScale = 1f / 32f;
    private MapObjects layerCollision;
    private float oldX;
    private float oldY;
    private DialogPanel dialogPanel;


    @Getter
    private List<Entity> entities;

    public World(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.hudBatch = new SpriteBatch();

        this.entities = new ArrayList<>();

        this.map=new TmxMapLoader().load("TiledMap/GameJamTiledMap.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);

        this.worldHeight=map.getProperties().get("height", Integer.class)*32f;
        this.worldWidth=map.getProperties().get("width", Integer.class)*32f;
        this.camera=new OrthographicCamera();
        this.viewport=new FitViewport(this.worldWidth, this.worldHeight, this.camera);
        this.dialogPanel = new DialogPanel(game.getPlayer(), game.getSoundManager());
        this.layerCollision = map.getLayers().get("collision").getObjects();

        EntityRegister.registerEntities(this);
    }

    @Override
    public void show() {
        this.oldX = this.game.getPlayer().getX();
        this.oldY = this.game.getPlayer().getY();
        this.game.getPlayer().setPosition(worldWidth/2, worldHeight/2);
        this.entities.get(0).setPosition(worldWidth/2, worldHeight/2);
        this.entities.get(1).setPosition(worldWidth/2, worldHeight/2 + 100);

        this.entities.get(2).setPosition(worldWidth/2 + 100, worldHeight/2);
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
        updateEntities(Gdx.graphics.getDeltaTime());
        playerLimit();
    }

    public void updateEntities(float delta){
        this.game.getPlayer().update(delta);
        for(Entity entity : entities){
            entity.update(delta);
        }
    }

    private void updateCamera() {
        camera.position.set(this.game.getPlayer().getX(), this.game.getPlayer().getY(),0);
        camera.zoom = 0.2f;
        camera.update();
    }

    private void collision() {
        for (MapObject object : this.layerCollision) {
            if (object instanceof RectangleMapObject) {
                // Récupération du rectangle de collision
                Rectangle obstacleRectangle = ((RectangleMapObject) object).getRectangle();

                // Récupération du rectangle du joueur
                Rectangle playerRectangle = game.getPlayer().getPlayerRect();

                // Vérification de l'intersection entre les deux rectangles
                if (Intersector.overlaps(playerRectangle, obstacleRectangle)) {
                    // Calcul des chevauchements sur chaque axe
                    float overlapX = Math.min(
                        playerRectangle.x + playerRectangle.width,
                        obstacleRectangle.x + obstacleRectangle.width
                    ) - Math.max(playerRectangle.x, obstacleRectangle.x);

                    float overlapY = Math.min(
                        playerRectangle.y + playerRectangle.height,
                        obstacleRectangle.y + obstacleRectangle.height
                    ) - Math.max(playerRectangle.y, obstacleRectangle.y);

                    // Comparaison des chevauchements
                    if (overlapX < overlapY) {
                        // Collision principalement sur l'axe X
                        if (playerRectangle.x < obstacleRectangle.x) {
                            game.getPlayer().setX(oldX - 0.08f);
                        } else {
                            game.getPlayer().setX(oldX + 0.08f);
                        }
                    } else {
                        // Collision principalement sur l'axe Y
                        if (playerRectangle.y < obstacleRectangle.y) {
                            game.getPlayer().setY(oldY - 0.08f);
                        } else {
                            game.getPlayer().setY(oldY + 0.08f);
                        }
                    }
                }
            }
        }
        // Sauvegarder la position actuelle pour la prochaine itération
        this.oldX = game.getPlayer().getX();
        this.oldY = game.getPlayer().getY();
    }

    public void draw() {
        ScreenUtils.clear(0, 0, 0, 1);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        mapRenderer.setView(camera);
        mapRenderer.render();

        // Utilisation de ShapeRenderer pour afficher les rectangles
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Dessiner le rectangle du joueur
        Rectangle playerRect = game.getPlayer().getPlayerRect();
        shapeRenderer.rect(playerRect.x, playerRect.y, playerRect.width, playerRect.height);

        shapeRenderer.end();

        // Dessin des sprites
        batch.begin();

        for(Entity entity : entities){
            entity.draw(batch);
        }

        this.game.getPlayer().draw(batch);
        batch.end();

        hudBatch.begin();
        this.game.getPlayer().getInventory().draw(hudBatch);
        hudBatch.end();
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
