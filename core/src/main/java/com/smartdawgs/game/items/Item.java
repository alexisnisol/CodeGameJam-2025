package com.smartdawgs.game.items;

import com.badlogic.gdx.graphics.Texture;
import lombok.Getter;

@Getter
public class Item {

    private Texture texture;
    private String name;

    public Item(String name, Texture texture) {
        this.name = name;
        this.texture = texture;
    }

}
