package com.smartdawgs.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class EntityPlayer extends Sprite{
    private TextureAtlas atlas;
    private float speed;
    private Polygon polygon;

    public EntityPlayer(TextureAtlas atlas) {
        super(atlas.findRegion("player"));
        float[] dimensions={1,1,1,1,1,1,1,1};//valeurs dimensions polygone joueur à changer quand on aura la texture
        polygon=new Polygon(dimensions);
        polygon.setPosition(getX(),getY());
        this.speed=100f;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Polygon getPlayerPolygon() {
        return this.polygon;
    }


    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.setY(this.getY() + speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.setY(this.getY() - speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.setX(this.getX() - speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.setX(this.getX() + speed * delta);
        }

        polygon.setPosition(getX(), getY());
    }
}
