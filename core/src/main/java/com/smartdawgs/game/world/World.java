package com.smartdawgs.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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
