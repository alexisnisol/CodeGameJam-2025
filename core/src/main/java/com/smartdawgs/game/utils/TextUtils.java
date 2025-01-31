package com.smartdawgs.game.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class TextUtils {

    public static void drawCenteredText(Batch batch, BitmapFont font, GlyphLayout layout, String text, float x, float y) {
        layout.setText(font, text);
        font.draw(batch, text, x - layout.width, y + layout.height / 2);
    }
}
