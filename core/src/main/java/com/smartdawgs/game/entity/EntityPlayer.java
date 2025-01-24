package com.smartdawgs.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;

public class EntityPlayer extends Entity{

    @Getter
    @Setter
    private float speed;
    private Polygon polygon;
    private Animation<TextureRegion> animation;
    private float stateTime;
    private static final int FRAME_COLS = 4; // Example value
    private static final int FRAME_ROWS = 4; // Example value
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> currentAnimation;

    public EntityPlayer(TextureAtlas atlas) {
        super(atlas.findRegion("player_1"));
        float[] dimensions={1,1,1,1,1,1,1,1};
        polygon=new Polygon(dimensions);
        polygon.setPosition(getX(),getY());
        this.speed=100f;
        this.setScale(2.0f);

        walkAnimation = new Animation<TextureRegion>(0.1f,
            atlas.findRegion("player_move_1"),
            atlas.findRegion("player_move_2")
        );

        stateTime = 1f;
    }

    public Polygon getPlayerPolygon() {
        return this.polygon;
    }

    public void update(float delta) {
        stateTime += delta;

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

    @Override
    public void draw(Batch batch) {
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
