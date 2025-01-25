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
import java.util.Timer;
import java.util.TimerTask;

public class World extends WorldElement {

    private int nbDisque=0;

    private Boolean sonAnimaux=false;
    private Boolean sonObject=false;
    private Boolean deplacementObject=false;
    private Boolean journeaux=false;

    public World(Main game) {
        super(game, "GameJamTiledMap");
    }

    public World(Main game, List<Entity> entities) {
        super(game, "GameJamTiledMap");
        this.entities = entities;
        this.game.getPlayer().setWorld(this);
    }

    public Boolean getSonAnimaux() {
        return sonAnimaux;
    }

    public Boolean getSonObject() {
        return sonObject;
    }

    public Boolean getDeplacementObject() {
        return deplacementObject;
    }

    public Boolean getJourneaux() {
        return journeaux;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        verifFin();
        draw();
        logic();
    }

    private void verifFin() {
        if(nbDisque>=4) {
            game.setScreen(new EcranTitre());
        }
    }

    public void logic() {
        super.logic();
        checkPlace();
    }

    public void disqueInfluence() {
        this.deplacementObject=true;
        jukeBox.setVisible(true);
        jukeBox.setText("Et les objets retrouvèrent leur finalité");
        jukeBox.setPosition(game.getPlayer().getX(), game.getPlayer().getY() + 10);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                jukeBox.setVisible(false);
            }
        }, 5000);
    }

    public void disqueHamonie() {
        this.journeaux=true;
        jukeBox.setVisible(true);
        jukeBox.setText("Et le monde retrouva son état d’antan");
        jukeBox.setPosition(game.getPlayer().getX(), game.getPlayer().getY() + 10);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                jukeBox.setVisible(false);
            }
        }, 5000);
    }

    public void disqueSecret() {
        this.sonObject=true;
        jukeBox.setVisible(true);
        jukeBox.setText("Et les secrets du monde furent révélés");
        jukeBox.setPosition(game.getPlayer().getX(), game.getPlayer().getY() + 10);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                jukeBox.setVisible(false);
            }
        }, 5000);
    }

    public void disqueAme() {
        this.sonAnimaux=true;
        jukeBox.setVisible(true);
        jukeBox.setText("Et les êtres retrouvèrent leur raison");
        jukeBox.setPosition(game.getPlayer().getX(), game.getPlayer().getY() + 10);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                jukeBox.setVisible(false);
            }
        }, 5000);
    }

    private void checkPlace() {
        MapObject nearlyPoint = WorldUtils.getNearlyPoint(map.getLayers().get("Label"), this.game.getPlayer().getX(), this.game.getPlayer().getY(), 50f);

        if(nearlyPoint != null){
            labelInteraction.setVisible(true);
            labelInteraction.setText("Appuyez sur la touche 'F'");
            labelInteraction.setPosition(game.getPlayer().getX(), game.getPlayer().getY() + 10);
            if (Gdx.input.isKeyPressed(Input.Keys.F) && stateTime - deltaTest > 0.5) {
                deltaTest = stateTime;
                interactWithElement(nearlyPoint.getName());
            }
        } else {
            labelInteraction.setVisible(false);
        }
    }

    private void interactWithElement(String element) {
        switch (element) {
            case "house1":
                this.game.setScreen(new HouseEnigme1(this));
                this.hide();
                break;
            case "house2":
                this.game.setScreen(new HouseEnigme2(this));
                this.hide();
                break;
            case "house3":
                this.game.setScreen(new HouseEnigme3(this));
                this.hide();
                break;
            case "house4":
                this.game.setScreen(new HouseEnigme4(this));
                this.hide();
                break;

            case "influence":
                disqueInfluence();
                nbDisque+=1;
                break;

            case "harmonie":
                disqueHamonie();
                nbDisque+=1;
                break;

            case "ame":
                disqueAme();
                nbDisque+=1;
                break;

            case "secret":
                disqueSecret();
                nbDisque+=1;
                break;

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
        return this;
    }
}
