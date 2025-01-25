package com.smartdawgs.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;
import com.smartdawgs.game.Main;
import com.smartdawgs.game.entity.EntityPlayer;
import com.smartdawgs.game.sound.SoundManager;
import lombok.Getter;

public class DialogPanel {
    private Label dialogLabel;
    private Label title;
    @Getter
    private final Table table;
    private final BitmapFont font;
    private final Main game;
    @Getter
    private float parentAlpha;


    public DialogPanel(Main game) {
        this.game = game;
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
        table.add(title).width(Gdx.graphics.getWidth() / 2f - 10).expand().align(Align.top).row();
        table.add(dialogLabel).width(Gdx.graphics.getWidth() / 2f - 10).expand().align(Align.top);
        table.setBackground(skin.newDrawable("default-round", Color.DARK_GRAY));
        this.parentAlpha = 0f;
    }

    public void setText(String text) {
        dialogLabel.setText(text);
    }
    public void setTitle(String text) {
        title.setText(text);
    }

    public void draw() {
        this.game.getPlayer().setSpeed(0f);
        this.game.getSoundManager().setFacteurVolume(0f);
        this.parentAlpha = 1f;
    }

    public void draw(String title, String text) {
        this.setTitle(title);
        this.setText(text);
        this.game.getPlayer().setSpeed(0f);
        this.game.getSoundManager().setFacteurVolume(0f);
        this.parentAlpha = 1f;
    }

    public void hide() {
        this.game.getPlayer().setSpeed(100f);
        this.game.getSoundManager().setFacteurVolume(1f);
        this.parentAlpha = 0f;
    }

    public void dispose() {
        font.dispose();
    }
}
