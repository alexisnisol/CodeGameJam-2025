package com.smartdawgs.game.items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.smartdawgs.game.entity.EntityPlayer;
import lombok.Getter;

public class ItemDisque extends Item{

    private DisqueType disqueType;

    public ItemDisque(String name, Texture texture, Sound sound, DisqueType disqueType) {
        super(name, texture, sound);
        this.disqueType = disqueType;
    }

    @Override
    public void onUse(EntityPlayer player) {
        System.out.println("Disque was used!");
        player.getWorld().addDisque(this.disqueType);
    }

    @Getter
    public enum DisqueType {
        INFLUENCE("Et les objets retrouvèrent leur finalité", true, false, false, false),
        HARMONIE("Et le monde retrouva son état d’antan", true, true, true, true),
        SECRET("Et les secrets du monde furent révélés", false, false, true, false),
        AME("Et les êtres retrouvèrent leur raison", false, false, false, true);

        private String text;
        private boolean deplacementObject;
        private boolean journeaux;
        private boolean sonObject;
        private boolean sonAnimaux;

        DisqueType(String text, boolean deplacementObject, boolean journeaux, boolean sonObject, boolean sonAnimaux) {
            this.text = text;
            this.deplacementObject = deplacementObject;
            this.journeaux = journeaux;
            this.sonObject = sonObject;
            this.sonAnimaux = sonAnimaux;
        }
    }
}
