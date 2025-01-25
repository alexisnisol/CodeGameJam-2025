package com.smartdawgs.game.items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.smartdawgs.game.entity.EntityPlayer;

public class ItemDisque extends Item{

    public ItemDisque(String name, Texture texture, Sound sound) {
        super(name, texture, sound);
    }

    @Override
    public void onUse(EntityPlayer player) {
        System.out.println("Disque was used!");
    }
}
