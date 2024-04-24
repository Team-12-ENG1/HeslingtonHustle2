package com.heshus.game.screens.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.heshus.game.engine.HesHusGame;
import com.heshus.game.manager.DayManager;
import com.heshus.game.manager.Save;
import com.heshus.game.manager.Score;
import static com.heshus.game.manager.Save.gd;

public class GameOverScreen implements Screen {

    final HesHusGame game;
    OrthographicCamera camera;
    private Score playerScore;


    public GameOverScreen(final HesHusGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Create an instance of Score for the player
        playerScore = new Score(game.playerName, game.dayManager.calculateScore());
    }

    @Override
    public void show() {
        // TODO: Make layout a table using Scene2D
        if (gd.isHighScore(playerScore)) {
            gd.addHighScore(playerScore);
            Save.save();
            // Make a new label saying "you're on the leaderboard!"
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "GAME OVER! Your score was " + game.dayManager.calculateScore(), 100, 150);
        game.font.draw(game.batch, "Tap anywhere to go to the main menu!", 100, 100);
        game.batch.end();

        //Resets variables to default when a new game can be played
        if (Gdx.input.isTouched()) {
            game.dayManager = new DayManager();
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
