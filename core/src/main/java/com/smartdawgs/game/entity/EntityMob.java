package com.smartdawgs.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.smartdawgs.game.world.World;

public class EntityMob extends Entity {

    private final double speed;
    private final World world;
    private Rectangle mobRect;
    private float stateTime;

    // Animation for idle
    private Animation<TextureRegion> idleAnimation;
    private  Animation<TextureRegion> idleSideAnimation;
    private  Animation<TextureRegion> idleBackAnimation;

    // Animation for walking
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> walkSideAnimation;
    private Animation<TextureRegion> walkBackAnimation;

    public EntityMob(TextureAtlas atlas, World world) {
        super(atlas.findRegion("player_idle_1"));
        mobRect =new Rectangle(2, 2, 33, 38);
        mobRect.setPosition(getX(),getY());

        this.speed= world.getGame().getPlayer().getSpeed()/1.2;
        this.world = world;

        idleAnimation = createAnimation(atlas, "skeleton_idle", 4, 0.1f);
        idleSideAnimation = createAnimation(atlas, "skeleton_idle_side", 4, 0.1f);
        idleBackAnimation = createAnimation(atlas, "skeleton_idle_back", 4, 0.1f);

        walkAnimation = createAnimation(atlas, "skeleton_walk", 4, 0.1f);
        walkSideAnimation = createAnimation(atlas, "skeleton_walk_side", 4, 0.1f);
        walkBackAnimation = createAnimation(atlas, "skeleton_walk_back", 4, 0.1f);

        stateTime = 1f;

    }

    @Override
    public void update(float delta) {

    }
}
