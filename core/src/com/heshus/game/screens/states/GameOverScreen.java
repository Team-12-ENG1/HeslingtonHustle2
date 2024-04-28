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
    // New: added the player's score as a Score instance
    private Score playerScore;


    public GameOverScreen(final HesHusGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // New: Create an instance of Score for the player
        game.score = game.dayManager.endGame();
        playerScore = new Score(game.playerName, game.score);
    }

    // New: modified the show method to save the player's score if it qualifies to be on the leaderboard
    @Override
    public void show() {
        // todo: Make layout a table using Scene2D
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
        // New: modified text to display the player's score
        game.font.draw(game.batch, "GAME OVER! Your score was " + game.dayManager.calculateScore(), 100, 150);
        game.font.draw(game.batch, "Tap anywhere to go to the main menu!", 100, 100);
        game.batch.end();

        // New: modified to reset the base game's attributes
        if (Gdx.input.isTouched()) {
            game.dayManager = new DayManager();
            game.score = 0;
            game.playerName = "";
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
