package com.smartdawgs.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.smartdawgs.game.EntityRegister;
import com.smartdawgs.game.Main;
import com.smartdawgs.game.entity.Entity;
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
    private Stage stage;
    private Label labelHouse1;
    private BitmapFont bitmapFont;
    private String labAction;

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

        this.layerCollision = map.getLayers().get("collision").getObjects();

        // Initialisation de Stage et de BitmapFont
        this.stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        bitmapFont = new BitmapFont();  // Initialisation de bitmapFont
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;

        // Création du label
        labelHouse1 = new Label("Appuyez sur la touche 'F'", labelStyle);

        stage.addActor(labelHouse1);  // Ajout du label à la scène
        EntityRegister.registerEntities(this);
    }

    @Override
    public void show() {
        this.oldX = this.game.getPlayer().getX();
        this.oldY = this.game.getPlayer().getY();
        this.game.getPlayer().setPosition(worldWidth/2, worldHeight/2);
        labelHouse1.setPosition(game.getPlayer().getX(), game.getPlayer().getY() + 10);
        }

    @Override
    public void render(float v) {
        logic();
        draw();
    }

    public void logic() {
        updateCamera();
        collision();
        updateEntities(Gdx.graphics.getDeltaTime());
        playerLimit();
        checkPlace();
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

    private void checkPlace() {
        MapObjects points = map.getLayers().get("Label").getObjects();
        for (MapObject object : points) {
            float x = (float) object.getProperties().get("x", Float.class);
            float y = (float) object.getProperties().get("y", Float.class);

            if ((Math.abs(x - this.game.getPlayer().getX()) < 50f || Math.abs(x + this.game.getPlayer().getX()) < 50f ) && (Math.abs(y - this.game.getPlayer().getY()) < 50f || Math.abs(y + this.game.getPlayer().getY()) < 50f )) {
                labelHouse1.setVisible(true);
                labelHouse1.setText("Appuyez sur la touche 'F'");
                labelHouse1.setPosition(game.getPlayer().getX(), game.getPlayer().getY() + 10);
                if (Gdx.input.isKeyPressed(Input.Keys.F)) {
                    labAction = object.getName();
                }
            }

            else {
                labelHouse1.setVisible(false);
                labAction = "";
            }
        }
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
            else  if (object instanceof PolygonMapObject) {
                // Récupérer le polygone
                Polygon polygon = ((PolygonMapObject) object).getPolygon();

                // Convertir le rectangle du joueur en polygone temporaire
                Polygon playerPolygon = rectangleToPolygon(game.getPlayer().getPlayerRect());

                // Vérifier la collision entre le polygone du joueur et celui de l'objet
                if (Intersector.overlapConvexPolygons(playerPolygon, polygon)) {
                    game.getPlayer().setX(oldX); // Revenir à l'ancienne position
                    game.getPlayer().setY(oldY);
                }
            }
        }
        // Sauvegarder la position actuelle pour la prochaine itération
        this.oldX = game.getPlayer().getX();
        this.oldY = game.getPlayer().getY();
    }

    // Méthode utilitaire pour convertir un Rectangle en Polygon
    private Polygon rectangleToPolygon(Rectangle rectangle) {
        float[] vertices = new float[]{
            rectangle.x, rectangle.y,  // Bottom-left
            rectangle.x + rectangle.width, rectangle.y,  // Bottom-right
            rectangle.x + rectangle.width, rectangle.y + rectangle.height,  // Top-right
            rectangle.x, rectangle.y + rectangle.height  // Top-left
        };
        return new Polygon(vertices);
    }




    public void draw() {
        ScreenUtils.clear(0, 0, 0, 1);

        viewport.apply();
        mapRenderer.setView(camera);


        mapRenderer.getBatch().begin();
        mapRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("grass"));
        mapRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("road"));
        mapRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("floor"));
        mapRenderer.getBatch().end();


        batch.begin();
        for(Entity entity : entities){
            entity.draw(batch);
        }
        this.game.getPlayer().draw(batch);
        batch.end();

        hudBatch.begin();
        this.game.getPlayer().getInventory().draw(hudBatch);
        hudBatch.end();

        mapRenderer.getBatch().begin();
        mapRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("trottoire"));

        mapRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("tree1"));
        mapRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("tree2"));
        mapRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("batiment"));
        mapRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("batiment2"));

        mapRenderer.getBatch().end();

        stage.act();
        stage.draw();
    

    

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
