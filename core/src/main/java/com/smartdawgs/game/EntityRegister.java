package com.smartdawgs.game;

import com.badlogic.gdx.graphics.Texture;
import com.smartdawgs.game.entity.items.EntityItem;
import com.smartdawgs.game.entity.items.EntityItemBook;
import com.smartdawgs.game.items.Item;
import com.smartdawgs.game.world.World;
import com.smartdawgs.game.world.WorldElement;

public class EntityRegister {

    public static final Item MUSHROOM = new Item("Test", new Texture("items/mushroom.png"));

    public static void registerEntities(WorldElement world) {
        world.getEntities().add(new EntityItem(world, MUSHROOM).spawn(300, 100));
        world.getEntities().add(new EntityItem(world, MUSHROOM).spawn(300, 100));
        world.getEntities().add(new EntityItemBook(world, MUSHROOM).spawn(300, 100));
    }
}
