package com.smartdawgs.game.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Disposable;
import com.smartdawgs.game.entity.EntityPlayer;
import com.smartdawgs.game.entity.items.EntityItem;
import com.smartdawgs.game.items.Item;
import lombok.Getter;

public class Inventory implements Disposable {

    @Getter
    private EntityItem currentItem; //TODO : Set the current item to AIR

    private EntityPlayer player;

    public Inventory(EntityPlayer player){
        this.player = player;
    }

    public boolean setCurrentItem(EntityItem item){
        if(currentItem != null || item == null){
            return false;
        }
        System.out.println("Item " + item + " was set to current item");
        this.currentItem = item;
        return true;
    }

    public Item getItem(){
        if(this.currentItem == null){
            return null;
        }
        return this.currentItem.getItem();
    }

    public EntityItem canDropItem(){
        if(this.currentItem == null){
            return null;
        }
        EntityItem item = this.currentItem;
        this.currentItem = null;
        return item;
    }

    public void draw(Batch batch) {
        if (this.currentItem == null) {
            return;
        }
        BitmapFont font = this.player.getWorld().getGame().getFont();
        GlyphLayout layout = this.player.getWorld().getGame().getGlyphLayout();
        String text = "Vous avez ";

        layout.setText(font, text);
        float textWidth = layout.width;
        this.player.getWorld().getGame().getFont().draw(batch, text, 30, 30);

        this.currentItem.draw(batch);
        this.currentItem.setPosition(textWidth + 7, -7);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "currentItem=" + currentItem +
                '}';
    }

    @Override
    public void dispose() {

    }
}
