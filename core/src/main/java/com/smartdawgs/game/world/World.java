package com.smartdawgs.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.utils.ScreenUtils;
import com.smartdawgs.game.Main;

public class World extends WorldElement {
    final float unitScale = 1f / 32f;
    private MapObjects layerCollision;
    private float oldX;
    private float oldY;
    private int label = 0;
    private HouseEnigme1 houseEnigme1;


    private Stage stage;
    private Label labelHouse1;
    private BitmapFont bitmapFont;
    private String labAction;

    public World(Main game) {
        super(game, "GameJamTiledMap");
        houseEnigme1 = new HouseEnigme1(game);
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

        this.game.getPlayer().update(Gdx.graphics.getDeltaTime());
        logic();
        draw();
    }

    public void logic() {
        updateCamera();
        collision();
        playerLimit();
        checkPlace();
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

            if (Math.abs(x - this.game.getPlayer().getX()) < 10f || Math.abs(y - this.game.getPlayer().getY()) < 10f) {
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
        stage.act();
        stage.draw();
        // Dessin des sprites
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
