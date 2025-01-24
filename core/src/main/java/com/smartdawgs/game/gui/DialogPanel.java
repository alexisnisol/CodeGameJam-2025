package com.smartdawgs.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;

public class DialogPanel {
    private Stage stage;
    private Label dialogLabel;
    private Table table;
    private BitmapFont font;

    public DialogPanel(Stage stage) {
        this.stage = stage;

        font = new BitmapFont();
        dialogLabel = new Label("test apzjpafpoaezfpoazefoqenregjnqeognvqeornvlnvojergoizovn,zGNOZG,OZNGZngjgzeognamkrgnekrmgn", new Label.LabelStyle(font, Color.WHITE));
        dialogLabel.setWrap(true);
        dialogLabel.setWidth(100);
        dialogLabel.setAlignment(Align.topLeft);
        dialogLabel.setPosition(10, 10);

        table = new Table();
        // TODO : set the table size
        // TODO : set the table position
        table.setWidth(100);
        table.setHeight(100);
        table.bottom().center();
        table.add(dialogLabel).width(100).pad(10);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json")); // Use a skin for styling
        table.setBackground(skin.newDrawable("white", Color.GRAY)); // Simple dark gray background
    }

    public void setText(String text) {
        dialogLabel.setText(text);
    }

    public void render() {
        stage.addActor(table);
        stage.act();
        stage.draw();
    }

    public void dispose() {
        font.dispose();
    }
}
