package com.smartdawgs.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.smartdawgs.game.world.World;

public class EntityMob extends Entity {

    private final double speed;
    private final World world;
    private Rectangle mobRect;

    public EntityMob(TextureAtlas atlas, World world) {
        super(atlas.findRegion("player_idle_1"));
        mobRect =new Rectangle(2, 2, 33, 38);
        mobRect.setPosition(getX(),getY());

        this.speed= world.getGame().getPlayer().getSpeed()/1.2;
        this.world = world;


    }

    @Override
    public void update(float delta) {

    }
}
