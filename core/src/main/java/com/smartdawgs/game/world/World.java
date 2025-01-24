package com.smartdawgs.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.smartdawgs.game.Main;
import com.smartdawgs.game.entity.Entity;
import com.smartdawgs.game.utils.WorldUtils;

import java.util.List;

public class World extends WorldElement {
    private float stateTime = 0;
    private float deltaTest = 0;

    protected HouseEnigme1 houseEnigme1;

    public World(Main game) {
        super(game, "GameJamTiledMap");
    }

    public World(Main game, List<Entity> entities) {
        super(game, "GameJamTiledMap");
        this.entities = entities;
        this.game.getPlayer().setWorld(this);
    }

    @Override
    public void render(float delta) {
        stateTime += delta;
        switch (labAction) {
            case "house1":
                /*if (testFirstPassage) {
                    houseEnigme1.init();
                    testFirstPassage = false;
                }*/
                this.game.setScreen(new HouseEnigme1(this));
                this.hide();
                //this.dispose();

                //houseEnigme1.render(delta);
                break;

            case "HouseEnigme2":
                break;

            default:
                logic();
                draw();
                break;
        }
    }

    public void logic() {
        super.logic();
        checkPlace();
    }

    @Override
    protected void updateCamera() {
        //si on est sur la map générale
        if (map.getLayers().get("piece1") == null) {
            updateCameraForWorld();
        }
        //si on est en intérieur
        else {
            updateCameraForInterior();
        }
        camera.update();
    }

    private void updateCameraForWorld() {
        camera.position.set(this.game.getPlayer().getX(), this.game.getPlayer().getY(), 0);
        camera.zoom = 0.2f;
    }

    private void updateCameraForInterior() {
        //parcourps des calques d'objets
        for (MapLayer layer : map.getLayers()) {
            //si c'est la pièce numéro 1 ou 2
            if (layer.getName().equals("piece1") || layer.getName().equals("piece2")) {
                updateCameraForLayer(layer);
            }
        }
    }

    private void updateCameraForLayer(MapLayer layer) {
        //recherche de la texture qui nous intéresse
        for (MapObject object : layer.getObjects()) {
            updateCameraForObject(object);
        }
    }

    private void updateCameraForObject(MapObject object) {
        //la texture qu'on veut est le seul à avoir une variable height
        if (object.getProperties().containsKey("height")) {
            if (object instanceof RectangleMapObject) {
                updateCameraForRectangle((RectangleMapObject) object);
            }
        } else if (object instanceof PolygonMapObject) {
            updateCameraForPolygon((PolygonMapObject) object);
        }
    }

    private void updateCameraForRectangle(RectangleMapObject object) {
        Rectangle rectangle = object.getRectangle();
        //vérification de la présence du joueur dans le calque
        if (rectangle.contains(this.game.getPlayer().getX(), this.game.getPlayer().getY())) {
            camera.position.set(rectangle.x, rectangle.y, 0);
            camera.viewportWidth = rectangle.width;
            camera.viewportHeight = rectangle.height;
        }
    }

    private void updateCameraForPolygon(PolygonMapObject object) {
        Polygon polygon = object.getPolygon();
        //vérification de la présence du joueur dans le calque
        if (polygon.contains(this.game.getPlayer().getX(), this.game.getPlayer().getY())) {
            Rectangle bounds = polygon.getBoundingRectangle();
            camera.position.set(bounds.x, bounds.y, 0);
            camera.viewportWidth = bounds.width;
            camera.viewportHeight = bounds.height;
        }
    }

    private void checkPlace() {
        MapObject nearlyPoint = WorldUtils.getNearlyPoint(map.getLayers().get("Label"), this.game.getPlayer().getX(), this.game.getPlayer().getY(), 50f);

        if(nearlyPoint != null){
            labelInteraction.setVisible(true);
            labelInteraction.setText("Appuyez sur la touche 'F'");
            labelInteraction.setPosition(game.getPlayer().getX(), game.getPlayer().getY() + 10);
            if (Gdx.input.isKeyPressed(Input.Keys.F) && stateTime - deltaTest > 0.5) {
                deltaTest = stateTime;
                labAction = nearlyPoint.getName();
                testFirstPassage = true;
            }
        } else {
            labelInteraction.setVisible(false);
            labAction = "";
            testFirstPassage = false;
        }
    }

    public void draw() {
        super.preRender();

        mapRenderer.setView(camera);
        mapRenderer.render();

        mapRenderer.getBatch().begin();
        mapRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("grass"));
        mapRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("road"));
        mapRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("floor"));

        mapRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("trottoire"));
        mapRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("tree1"));
        mapRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("tree2"));
        mapRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("batiment"));
        mapRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("batiment2"));

        mapRenderer.getBatch().end();

        super.postRender();
    }

    public WorldElement init(String pointSortie) {
        Vector2 position = WorldUtils.getPoint(map.getLayers().get("Label"), pointSortie);
        this.game.getPlayer().setPosition(position.x, position.y);
        labAction = "";
        return this;
    }
}
