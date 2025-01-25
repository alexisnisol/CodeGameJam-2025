package com.smartdawgs.game.items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import lombok.Getter;

@Getter
public class Item implements Disposable {

    private Texture texture;
    private String name;
    private Sound sound;

    public Item(String name, Texture texture, Sound sound) {
        this.name = name;
        this.texture = texture;
    }

    public void playSound() {
        if (sound != null) {
            sound.play(1.0f);
        }
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
        if (sound != null) {
            sound.dispose();
        }
    }

}
