package com.smartdawgs.game.entity.items;

import com.smartdawgs.game.entity.EntityPlayer;
import com.smartdawgs.game.items.Item;
import com.smartdawgs.game.items.ItemDisque;
import com.smartdawgs.game.world.WorldElement;

public class EntityJukebox extends InteractableEntityItem {

    public EntityJukebox(WorldElement world, Item item) {
        super(world, item);
    }

    @Override
    public boolean interact(EntityPlayer player) {
        Item playerItem = player.getInventory().getItem();

        if(playerItem instanceof ItemDisque) {
            this.getItem().playSound();
            playerItem.onUse();
            player.getInventory().canDropItem();
        }
        return true;
    }
}
