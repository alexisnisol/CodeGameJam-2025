package com.smartdawgs.game.entity.items;

import com.smartdawgs.game.items.Item;
import com.smartdawgs.game.world.World;
import com.smartdawgs.game.world.WorldElement;

public class InteractableEntityItem extends EntityItem {

    public InteractableEntityItem(WorldElement world, Item item) {
        super(world, item);
    }

    @Override
    public boolean interact() {
        System.out.println(this.getItem().getName() + " was interacted with!");
        return true;
    }
}
