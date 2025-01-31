package com.smartdawgs.game.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.smartdawgs.game.Main;
import com.smartdawgs.game.utils.TextUtils;
import com.smartdawgs.game.world.WorldElement;
import lombok.Getter;

public class GuiHUD {

    private Main game;

    public GuiHUD(WorldElement world) {
        this.game = world.getGame();
    }

    @Getter
    private final Batch batch = new SpriteBatch();

    public void draw() {
        batch.begin();
        // Draw the HUD
        float middleWidth = game.getWorld().getWorldWidth() / 16f;
        float topHeight = game.getWorld().getViewport().getScreenY() - 30;
        game.getFont().draw(batch, "Appuyez sur la touche 'F'", middleWidth, 30*2);
        game.getFont().draw(batch, "Et les objets retrouvèrent leur finalité", middleWidth, 30);
        game.getPlayer().getInventory().draw(batch);
        game.getWorld().getDialogPanel().getTable().draw(batch, game.getWorld().getDialogPanel().getParentAlpha());
        batch.end();
    }
}
