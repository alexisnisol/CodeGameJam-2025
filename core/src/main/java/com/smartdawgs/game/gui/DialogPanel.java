package com.smartdawgs.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;
import com.smartdawgs.game.entity.EntityPlayer;
import com.smartdawgs.game.sound.SoundManager;

public class DialogPanel {
    private Label dialogLabel;
    private Label title;
    private Table table;
    private BitmapFont font;
    private EntityPlayer player;
    private SoundManager soundManager;


    public DialogPanel(EntityPlayer player, SoundManager soundManager) {
        this.player = player;
        this.soundManager = soundManager;
        font = new BitmapFont();
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        initTitle();
        initContent();
        table = new Table();
        initTable(skin);
    }

    public void initTitle() {
        title = new Label("Dialog", new Label.LabelStyle(font, Color.WHITE));
        title.setAlignment(Align.center);
        title.setWidth(Gdx.graphics.getWidth() / 2f - 10);
    }

    public void initContent(){
        dialogLabel = new Label("", new Label.LabelStyle(font, Color.WHITE));
        dialogLabel.setWrap(true);
        dialogLabel.setAlignment(Align.center);
        dialogLabel.setWidth(Gdx.graphics.getWidth() / 2f - 10);
    }

    public void initTable(Skin skin) {
        table.setWidth(Gdx.graphics.getWidth());
        table.setHeight(Gdx.graphics.getHeight() / 3f);
        table.setPosition(Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 4f);
        table.add(title).width(Gdx.graphics.getWidth() / 2f - 10).expand().align(Align.center).row();
        table.add(dialogLabel).width(Gdx.graphics.getWidth() / 2f - 10).expand().align(Align.center);
        table.setVisible(false);
        table.setBackground(skin.newDrawable("default-round", Color.DARK_GRAY));
    }

    public void setText(String text) {
        dialogLabel.setText(text);
    }

    public void reposition() {
        float x = player.getX() - (float) Gdx.graphics.getWidth() / 2;
        float y = player.getY() - (float) Gdx.graphics.getHeight() / 2;
        table.setPosition(x, y);
    }

    public Table getTable() {
        return table;
    }

    public void draw() {
        reposition();
        this.player.setSpeed(0f);
        this.soundManager.setFacteurVolume(1f);
        this.table.setVisible(true);
    }

    public void hide() {
        this.player.setSpeed(100f);
        this.soundManager.setFacteurVolume(1f);
        this.table.setVisible(false);
    }

    public void dispose() {
        font.dispose();
    }
}
