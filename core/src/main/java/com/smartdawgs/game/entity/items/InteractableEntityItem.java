package com.smartdawgs.game.entity.items;

import com.smartdawgs.game.items.Item;
import com.smartdawgs.game.world.World;

public class InteractableEntityItem extends EntityItem {

    public InteractableEntityItem(World world, Item item) {
        super(world, item);
    }

    @Override
    public boolean interact() {
        System.out.println(this.getItem().getName() + " was interacted with!");
        return true;
    }
}
