package com.smartdawgs.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.smartdawgs.game.EntityRegister;
import com.smartdawgs.game.entity.items.EntityItem;
import com.smartdawgs.game.gui.Inventory;
import com.smartdawgs.game.utils.enums.Direction;
import com.smartdawgs.game.world.World;
import com.smartdawgs.game.world.WorldElement;
import lombok.Getter;
import lombok.Setter;

public class EntityPlayer extends Entity{

    @Getter
    @Setter
    private float speed;
    @Getter
    private Rectangle playerRect;
    private Animation<TextureRegion> animation;
    private float stateTime;
    private Animation<TextureRegion> currentAnimation;
    private Direction direction;
    private boolean isMoving = false;

    private float deltaTest = 0;


    @Getter
    @Setter
    private WorldElement world;

    @Getter
    private Inventory inventory;

    // Animation for idle
    private Animation<TextureRegion> idleAnimation;
    private  Animation<TextureRegion> idleSideAnimation;
    private  Animation<TextureRegion> idleBackAnimation;

    // Animation for walking
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> walkSideAnimation;
    private Animation<TextureRegion> walkBackAnimation;

    public EntityPlayer(TextureAtlas atlas, World world) {
        super(atlas.findRegion("player_idle_1"));
        playerRect =new Rectangle(2, 2, 33, 38);
        playerRect.setPosition(getX(),getY());
        this.speed=200f;
        this.world = world;
        this.inventory = new Inventory(this);

        this.setScale(2.0f);

        idleAnimation = createAnimation(atlas, "player_idle", 5, 0.1f);
        idleSideAnimation = createAnimation(atlas, "player_idle_side", 5, 0.1f);
        idleBackAnimation = createAnimation(atlas, "player_idle_back", 5, 0.1f);

        walkAnimation = createAnimation(atlas, "player_walk", 5, 0.1f);
        walkSideAnimation = createAnimation(atlas, "player_walk_side", 5, 0.1f);
        walkBackAnimation = createAnimation(atlas, "player_walk_back", 5, 0.1f);

        stateTime = 1f;

        this.setPosition(this.world.getWorldWidth() / 2, this.world.getWorldHeight() / 2);
    }

    public void update(float delta) {
        stateTime += delta;
        handleInput(delta);
        playerRect.setPosition(getX() + 7,getY() - 8);
    }

    public void handleInput(float delta) {
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

        if (Gdx.input.isKeyPressed(Input.Keys.E) && stateTime - deltaTest > 0.5) {
            this.useItem();
            deltaTest = stateTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F) && stateTime - deltaTest > 0.5) {
            this.interaction();
            deltaTest = stateTime;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.Y) && stateTime - deltaTest > 0.5){
            this.world.getEntities().add(new EntityItem(this.world, EntityRegister.MUSHROOM).spawn(this.getX(), this.getY()));
            deltaTest = stateTime;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.G) || Gdx.input.isKeyPressed(Input.Keys.P)) {
            EntityItem item = this.inventory.canDropItem();
            if (item != null) {
                System.out.println("Item " + item + " was dropped");
                this.world.getEntities().add(item.spawn(this.getX(), this.getY()));
            }
        }
    }

    private void useItem() {
        if(this.inventory.getCurrentItem() != null){
            this.inventory.getCurrentItem().onUse();
        }
    }

    private void interaction() {
        int i = 0;
        while (i < world.getEntities().size()) {
            Entity entity = world.getEntities().get(i);
            if (playerRect.overlaps(entity.getBoundingRectangle())) {
                if (entity instanceof EntityItem) {
                    EntityItem item = (EntityItem) entity;
                    item.interact();
                }
            }
            i++;
        }
    }

    @Override
    public void draw(Batch batch) {
        currentAnimation = idleAnimation;

        if (direction == Direction.UP) {
            currentAnimation = isMoving ? walkBackAnimation : idleBackAnimation;
        } else if (direction == Direction.DOWN) {
            currentAnimation = isMoving ? walkAnimation : idleAnimation;
        } else if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            currentAnimation = isMoving ? walkSideAnimation : idleSideAnimation;
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
