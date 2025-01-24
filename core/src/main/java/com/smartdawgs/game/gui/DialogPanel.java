package com.smartdawgs.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;
import com.smartdawgs.game.entity.EntityPlayer;

public class DialogPanel {
    private Label dialogLabel;
    private Table table;
    private BitmapFont font;
    private EntityPlayer player;

    public DialogPanel(EntityPlayer player) {
        this.player = player;
        font = new BitmapFont();
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        dialogLabel = new Label("", new Label.LabelStyle(font, Color.WHITE));
        dialogLabel.setWrap(true);
        table = new Table();
        table.setWidth((float) Gdx.graphics.getWidth() / 2);
        table.setHeight((float) Gdx.graphics.getHeight() / 4);
        table.setPosition((float) Gdx.graphics.getWidth() / 4, (float) Gdx.graphics.getHeight() / 4);
        table.add(dialogLabel).expand().align(Align.center);
        table.setVisible(false);
        table.setBackground(skin.newDrawable("default-round", Color.DARK_GRAY));
    }

    public void setText(String text) {
        dialogLabel.setText(text);
    }

    public void reposition() {
        table.setPosition(player.getX() - table.getWidth() / 2, player.getY() + player.getHeight());
    }

    public Table getTable() {
        return table;
    }

    public void draw() {
        reposition();
        this.table.setVisible(true);
    }

    public void hide() {
        this.table.setVisible(false);
    }

    public void dispose() {
        font.dispose();
    }
}
