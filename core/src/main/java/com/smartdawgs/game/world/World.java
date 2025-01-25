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
        super.render(delta);
        switch (labAction) {
            case "house1":
                this.game.setScreen(new HouseEnigme1(this));
                this.hide();
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

        if(labAction != "" && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.getDialogPanel().hide();
            labAction = "";
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
            }
        } else {
            labelInteraction.setVisible(false);
            labAction = "";
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
