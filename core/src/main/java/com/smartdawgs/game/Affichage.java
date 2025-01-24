package com.smartdawgs.game;

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
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

public class Affichage implements Screen {
    private Main jeu;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private TiledMap map;
    private TiledMapRenderer renderer;
    float worldWidth;
    float worldHeight;
    private MapObjects collision;
    private float oldX;
    private float oldY;


    public Affichage(Main jeu) {
        this.jeu = jeu;
        this.batch = new SpriteBatch();

        this.map=new TmxMapLoader().load("TiledMap/GameJamTiledMap.tmx");
        this.renderer=new OrthoCachedTiledMapRenderer(this.map);
        this.collision =this.map.getLayers().get("collision").getObjects();

        this.worldHeight=map.getProperties().get("height", Float.class);
        this.worldWidth=map.getProperties().get("width", Float.class);
        this.camera=new OrthographicCamera();
        this.viewport=new FitViewport(this.worldWidth, this.worldHeight, this.camera);
    }

    @Override
    public void show() {
        this.oldX = this.jeu.getPlayer().getX();
        this.oldY = this.jeu.getPlayer().getY();
    }

    @Override
    public void render() {
        logic();
        draw();
    }

    public void logic() {
        updateCamera();
        cameraLimit();
        collision();
        playerLimit();
    }

    private void updateCamera() {
        camera.position.set(jeu.getPlayer().getX(),jeu.getPlayer().getY(),0);
        camera.update();
    }

    private void cameraLimit() {
        float cameraX=camera.position.x;
        float cameraY=camera.position.y;

        if(cameraX<0){
            cameraX=0;
        }
        else if(cameraY<0){
            cameraY=0;
        }
        else if(cameraX>this.worldWidth){
            cameraX=this.worldWidth;
        }
        else if(cameraY>this.worldHeight){
            cameraY=this.worldHeight;
        }
        camera.position.set(cameraX,cameraY,0);
        camera.update();
    }

    private void collision() {
        //on va ajouter une collision à tout les objets
        for(MapObject obj:this.collision){
            if(obj instanceof PolygonMapObject){
                PolygonMapObject polygon=(PolygonMapObject)obj;
                //vérification s'il y a collision
                if(Intersector.overlapConvexPolygons(jeu.getPlayer().getPlayerPolygon(),polygon)){
                    jeu.getPlayer().setPosition(this.oldX,this.oldY);
                    jeu.getPlayer().getPlayerPolygon().setPosition(this.oldX, this.oldY);
                }
            }
        }
        this.oldX=jeu.getPlayer().getX();
        this.oldY=jeu.getPlayer().getY();
    }

    public void draw() {
        ScreenUtils.clear(0, 0, 0, 1);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setView(camera);
        renderer.render();
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.polygon(jeu.getPlayer().getPlayerPolygon().getTransformedVertices());
        shapeRenderer.end();

        batch.begin();
        jeu.getPlayer().draw(batch);
        batch.end();
    }

    private void playerLimit() {
        float playerX = jeu.getPlayer().getX();
        float playerY = jeu.getPlayer().getY();

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
        jeu.getPlayer().setPosition(playerX, playerY);
    }



    @Override
    public void dispose() {
        jeu.dispose();
        renderer.dispose();
        map.dispose();
    }
}
