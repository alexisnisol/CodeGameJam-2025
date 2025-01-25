package com.smartdawgs.game.entity.items;

import com.smartdawgs.game.entity.Entity;
import com.smartdawgs.game.entity.EntityPlayer;
import com.smartdawgs.game.entity.EntityRegister;
import com.smartdawgs.game.items.Item;
import com.smartdawgs.game.world.WorldElement;
import lombok.Getter;

@Getter
public class EntityItem extends Entity implements Interactable {

    private Item item;
    private WorldElement world;

    public EntityItem(WorldElement world, Item item) {
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
    public boolean interact(EntityPlayer player) {
        EntityRegister.SOUND_EQUIP.play();
        System.out.println(this.getItem().getName() + " was picked up!");
        if(this.world.getGame().getPlayer().getInventory().setCurrentItem(this)) {
            this.world.getEntities().remove(this);
        }
        return true;
    }

}
