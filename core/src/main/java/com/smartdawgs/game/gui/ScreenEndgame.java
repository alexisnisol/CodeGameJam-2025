package com.smartdawgs.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

public class ScreenEndgame implements Screen {
    private SpriteBatch batch;
    private BitmapFont font;
    private float creditY;

    private final String endText =
        "Tu as ramené la musique dans ce monde, mais ce n’est pas la fin.\n" +
            "C’est un cycle éternel : cette dimension se taira de nouveau,\n" +
            "et quelqu’un devra à nouveau restaurer sa mélodie.\n" +
            "La boucle ne cesse de se répéter.\n" +
            "Néanmoins, à chaque renaissance, il reste une lueur d’espoir…\n" +
            "La musique nous sauvera, toujours.";
    private final String creditsText="Credit";


    public ScreenEndgame() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(1.2f);
        creditY = -Gdx.graphics.getHeight();  // Position de départ en bas de l'écran
    }

    @Override
    public void show() {
        // Méthode appelée lors du changement vers cet écran
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // Efface l'écran en noir

        batch.begin();

        // Affichage du message principal au centre
        font.draw(batch, endText,
            Gdx.graphics.getWidth() / 2f - 300,
            Gdx.graphics.getHeight() - 100,
            600, Align.center, true);

        // Défilement du générique
        creditY += 50 * delta;
        font.draw(batch, creditsText,
            Gdx.graphics.getWidth() / 2f - 300,
            creditY,
            600, Align.center, true);

        batch.end();

        // Fermer l'écran après le générique
        if (creditY > Gdx.graphics.getHeight() + 400) {
            Gdx.app.exit();
            System.exit(0);
        }
    }

    @Override
    public void resize(int width, int height) {
        this.batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
