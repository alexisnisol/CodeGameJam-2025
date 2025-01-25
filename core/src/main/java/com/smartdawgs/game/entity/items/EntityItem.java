package com.smartdawgs.game.entity.items;

import com.smartdawgs.game.entity.Entity;
import com.smartdawgs.game.items.Item;
import com.smartdawgs.game.world.World;
import lombok.Getter;

@Getter
public class EntityItem extends Entity implements Interactable {

    private Item item;
    private World world;

    public EntityItem(World world, Item item) {
        super(item.getTexture());
        this.world = world;
        this.item = item;
    }

    public EntityItem spawn(float x, float y){
        this.setPosition(x, y);
        return this;
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public String toString() {
        return "EntityItem{" +
                "item=" + item +
                '}';
    }

    @Override
    public boolean interact() {
        this.item.playSound(); // Test si fonctionnel
        System.out.println(this.getItem().getName() + " was picked up!");
        if(this.world.getGame().getPlayer().getInventory().setCurrentItem(this)) {
            this.world.getEntities().remove(this);
        }
        return true;
    }

    public void onUse() {

    }



}
