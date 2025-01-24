package com.smartdawgs.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.smartdawgs.game.entity.enums.Direction;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

public class EntityPlayer extends Entity{

    @Getter
    @Setter
    private float speed;
    private Rectangle playerRect;
    private Animation<TextureRegion> animation;
    private float stateTime;
    private Animation<TextureRegion> currentAnimation;
    private Direction direction;
    private boolean isMoving = false;


    // Animation for idle
    private Animation<TextureRegion> idleAnimation;
    private  Animation<TextureRegion> idleSideAnimation;
    private  Animation<TextureRegion> idleBackAnimation;

    // Animation for walking
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> walkSideAnimation;
    private Animation<TextureRegion> walkBackAnimation;

    public EntityPlayer(TextureAtlas atlas) {
        super(atlas.findRegion("player_1"));
        playerRect =new Rectangle(2, 2, 33, 38);
        playerRect.setPosition(getX(),getY());
        this.speed=100f;
        this.setScale(2.0f);

        idleAnimation = createAnimation(atlas, "player_idle", 5, 0.1f);
        idleSideAnimation = createAnimation(atlas, "player_idle_side", 5, 0.1f);
        idleBackAnimation = createAnimation(atlas, "player_idle_back", 5, 0.1f);

        walkAnimation = createAnimation(atlas, "player_walk", 5, 0.1f);
        walkSideAnimation = createAnimation(atlas, "player_walk_side", 5, 0.1f);
        walkBackAnimation = createAnimation(atlas, "player_walk_back", 5, 0.1f);

        stateTime = 1f;
    }

    public Rectangle getPlayerRect() {
        return this.playerRect;
    }

    public void update(float delta) {
        stateTime += delta;

        isMoving = false;
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.translateY(speed * delta);
            this.direction = Direction.UP;
            this.isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.translateY(- speed * delta);
            this.direction = Direction.DOWN;
            this.isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.translateX(- speed * delta);
            this.direction = Direction.LEFT;
            this.isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.translateX(speed * delta);
            this.direction = Direction.RIGHT;
            this.isMoving = true;
        }

        playerRect.setPosition(getX() + 7,getY() - 8);

    }

    @Override
    public void draw(Batch batch) {
        currentAnimation = idleAnimation;

        if (isMoving){
            if (direction == Direction.UP) {
                currentAnimation = walkBackAnimation;
            } else if (direction == Direction.DOWN) {
                currentAnimation = walkAnimation;
            } else if (direction == Direction.LEFT) {
                currentAnimation = walkSideAnimation;
            } else if (direction == Direction.RIGHT) {
                currentAnimation = walkSideAnimation;
            }
        } else {
            if (direction == Direction.UP) {
                currentAnimation = idleBackAnimation;
            } else if (direction == Direction.DOWN) {
                currentAnimation = idleAnimation;
            } else if (direction == Direction.LEFT) {
                currentAnimation = idleSideAnimation;
            } else if (direction == Direction.RIGHT) {
                currentAnimation = idleSideAnimation;
            }
        }

        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        if (direction == Direction.LEFT && !currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        } else if (direction == Direction.RIGHT && currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }

        batch.draw(currentFrame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
