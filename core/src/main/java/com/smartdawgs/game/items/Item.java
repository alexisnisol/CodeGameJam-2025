package com.smartdawgs.game.items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import lombok.Getter;

@Getter
public class Item {

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

}
