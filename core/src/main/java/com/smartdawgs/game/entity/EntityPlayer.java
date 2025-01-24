package com.smartdawgs.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import lombok.Getter;
import lombok.Setter;

public class EntityPlayer extends Entity{

    @Getter
    @Setter
    private float speed;
    private Polygon polygon;

    public EntityPlayer(TextureAtlas atlas) {
        super(atlas.findRegion("player_1"));
        float[] dimensions={1,1,1,1,1,1,1,1};
        polygon=new Polygon(dimensions);
        polygon.setPosition(getX(),getY());
        this.speed=100f;
    }

    public Polygon getPlayerPolygon() {
        return this.polygon;
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.translateY(speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.translateY(- speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.translateX(- speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.translateX(speed * delta);
        }
    }
}
