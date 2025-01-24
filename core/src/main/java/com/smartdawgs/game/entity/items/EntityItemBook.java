package com.smartdawgs.game.entity.items;

import com.smartdawgs.game.gui.DialogPanel;

public class EntityItemBook extends EntityItem {
    private String name;
    private String text;
    private DialogPanel dialogPanel;

    public EntityItemBook(String name, String text, DialogPanel dialogPanel) {
        super(name);
        this.text = text;
        this.dialogPanel = dialogPanel;
    }

    @Override
    public void interact(){
        dialogPanel.setText(text);
        dialogPanel.draw();
    }

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub
    }

}
