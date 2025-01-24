package com.smartdawgs.game.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public abstract class Entity  extends Sprite {

    public Entity() {
        super();
    }

    public Entity(TextureAtlas.AtlasRegion sprite) {
        super(sprite);
    }

    public abstract void update(float delta);
}
