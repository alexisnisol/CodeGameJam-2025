package com.smartdawgs.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Entity extends Sprite {

    public Entity(TextureAtlas.AtlasRegion region) {
        super(region);
    }

    public Entity(Texture sprite) {
        super(sprite);
    }

    public abstract void update(float delta);

    public Animation<TextureRegion> createAnimation(TextureAtlas atlas, String name, int frames, float duration) {
        TextureRegion[] regions = new TextureRegion[frames];
        for (int i = 0; i < frames; i++) {
            regions[i] = atlas.findRegion(name + "_" + (i + 1));
        }
        return new Animation<TextureRegion>(duration, regions);
    }
}
