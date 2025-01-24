package com.smartdawgs.game.entity;

public class EntityItem extends Entity {
    protected String name;

    public EntityItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void interract(){
        // TODO : implement the interraction
    }

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub
    }
}
