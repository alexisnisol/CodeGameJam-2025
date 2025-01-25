package com.smartdawgs.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.smartdawgs.game.Main;
import com.smartdawgs.game.utils.WorldUtils;

public class HouseEnigme1 extends WorldElement {

    private World parent;
    private String nom;

    public HouseEnigme1(World parent) {
        this(parent, "chambrepersonnelle", "house1");
    }

    public HouseEnigme1(World parent, String tiledMap, String nom) {
        super(parent.getGame(), tiledMap);
        this.parent = parent;
        this.nom = nom;
        init();
    }

    public void init() {
        Vector2 position = WorldUtils.getPoint(this.map.getLayers().get("piece1"), "sortie");
        this.game.getPlayer().setPosition(position.x, position.y);
    }

    @Override
    public void logic() {
        super.logic();
        checkPlace();
        this.camera.zoom = 0.1f;
    }

    private void checkPlace() {

        MapObject nearlyPoint = WorldUtils.getNearlyPoint(map.getLayers().get("piece1"), this.game.getPlayer().getX(), this.game.getPlayer().getY(), 50f);

        if(nearlyPoint != null) {
            labelInteraction.setVisible(true);
            labelInteraction.setText("Appuyez sur la touche 'F'");
            labelInteraction.setPosition(game.getPlayer().getX(), game.getPlayer().getY() + 10);
            if (Gdx.input.isKeyPressed(Input.Keys.F) && stateTime - deltaTest > 0.5) {
                deltaTest = stateTime;
                interactWithElement("exit");
            }
        } else {
            labelInteraction.setVisible(false);
        }
    }

    private void interactWithElement(String element) {
        switch (element) {
            case "exit":
                parent.show();
                this.game.setScreen(parent.init(nom));
                this.dispose();
        }
    }

    @Override
    public void draw() {
        super.preRender();

        mapRenderer.setView(camera);
        mapRenderer.render();

        mapRenderer.getBatch().begin();
        // draw elements
        mapRenderer.getBatch().end();


        super.postRender();
    }



}
