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
        super(parent.getGame(), "chambrepersonnelle");
        this.parent = parent;
        this.nom = "house1";
        init();
    }

    public void init() {
        Vector2 position = WorldUtils.getPoint(this.map.getLayers().get("piece1"), "sortie");
        this.game.getPlayer().setPosition(position.x, position.y);
        labAction = nom;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (labAction.isEmpty()) {
            parent.show();
            this.game.setScreen(parent.init(nom));
            this.dispose();
        } else {
            logic();
            draw();
        }
    }

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
                labAction = "";
            }
        } else {
            labelInteraction.setVisible(false);
            labAction = nom;
        }
    }

    public void draw() {
        super.preRender();

        // Utilisation de ShapeRenderer pour afficher les rectangles
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Dessiner le rectangle du joueur
        Rectangle playerRect = game.getPlayer().getPlayerRect();
        shapeRenderer.rect(playerRect.x, playerRect.y, playerRect.width, playerRect.height);
        shapeRenderer.end();


        mapRenderer.setView(camera);
        mapRenderer.render();

        mapRenderer.getBatch().begin();
        // draw elements
        mapRenderer.getBatch().end();


        super.postRender();
    }



}
