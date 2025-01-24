package com.smartdawgs.game.entity.items;

import com.smartdawgs.game.entity.Entity;

public class EntityItem extends Entity {
    protected String name;

    public EntityItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void interact(){
        // TODO : implement the interaction
    }

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub
    }
}
