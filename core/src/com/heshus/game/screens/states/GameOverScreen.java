package com.heshus.game.screens.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.heshus.game.engine.HesHusGame;
import com.heshus.game.manager.DayManager;
import com.heshus.game.manager.Save;
import com.heshus.game.manager.Score;

import java.util.ArrayList;
import java.util.List;

import static com.heshus.game.manager.Save.gd;

public class GameOverScreen implements Screen {

    private final HesHusGame game;
    // New: added the player's score as a Score instance
    private Score playerScore;
    private BitmapFont font;
    private Table table;
    private TextButton menuBtn;
    private Label title;
    private Label scoreLabel;

    private Label streaksLabel;
    private Texture buttonTexture;
    private TextureRegion buttonTextureRegion;
    private TextureRegionDrawable buttonTextureRegionDrawable;
    private TextButton.TextButtonStyle textButtonStyle;
    private Stage stage;

    private List<Texture> streakTextures;
    private List<Image> streakImages;
    private List<String[]> scoreAndStreaks;

    public final static String FONT = "Fonts/monogram/pixel.fnt";
    public final static String BUTTON = "UI/button_up.png";
    public final static String ICONS = "Icons/";

    /**
     * Create an instance of the game over screen
     *
     * @param game The base game class
     */
    public GameOverScreen(final HesHusGame game) {
        this.game = game;
        stage = new Stage(new ExtendViewport(400, 225));
        // New: Create an instance of Score for the player
        this.scoreAndStreaks = game.dayManager.endGame();
        game.score = Integer.parseInt(this.scoreAndStreaks.get(0)[0]);
        this.scoreAndStreaks.remove(0);
        playerScore = new Score(game.playerName, game.score);
        this.streakTextures = new ArrayList<>();
        for(String[] scoreAndStreak : this.scoreAndStreaks) {
            streakTextures.add(new Texture(ICONS + scoreAndStreak[1]));
        }
        this.streakImages = new ArrayList<>();
        for(Texture streakTexture: streakTextures){
            streakImages.add(new Image(streakTexture));
        }
        // Set up font
        font = new BitmapFont(Gdx.files.internal(FONT), false);
        font.getData().setScale(.5F);
        font.setColor(Color.BLACK);

        title = new Label("Game Over!", new Label.LabelStyle(font, Color.WHITE));
        scoreLabel = new Label("Score: " + playerScore.getScore(), new Label.LabelStyle(font, Color.WHITE));
        if(!streakImages.isEmpty()){
            streaksLabel = new Label("You achieved some streaks!", new Label.LabelStyle(font, Color.WHITE));
        }else{
            streaksLabel = new Label("You didn't achieve any streaks!", new Label.LabelStyle(font, Color.WHITE));
        }
        setButtonStyle();
        menuBtn = new TextButton("Main Menu", textButtonStyle);

        table = new Table();
        stage.addActor(table);
        table.setFillParent(true);
    }

    // New: modified the show method to save the player's score if it qualifies to be on the leaderboard
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.add(title).center().colspan(2);
        table.row().pad(5, 0, 0, 0);
        if (gd.isHighScore(playerScore)) {
            gd.addHighScore(playerScore);
            Save.save(Save.SCORES);
            Label highScore = new Label("High Score!", new Label.LabelStyle(font, Color.WHITE));
            table.add(highScore).center().colspan(2);
            table.row().pad(5, 0, 0, 0);
        }
        table.add(scoreLabel).center().colspan(2);
        table.row().pad(5, 0, 0, 0);

        // Add Streaks
        table.add(streaksLabel).center().colspan(2);
        if (!streakImages.isEmpty()) {
            for (int i = 0; i < streakImages.size(); i++) {
                table.row().pad(5,0,0,0);
                table.add(streakImages.get(i)).size(30,30).right();
                table.add(new Label(this.scoreAndStreaks.get(i)[0], new Label.LabelStyle(font, Color.WHITE))).center();
            }
        }
        table.row().pad(5,0,0,0);
        table.add(menuBtn).center().colspan(2);

        menuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.dayManager = new DayManager();
                game.score = 0;
                game.playerName = "";
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.draw();
    }

    private void setButtonStyle() {
        buttonTexture = new Texture(BUTTON);
        buttonTextureRegion = new TextureRegion(buttonTexture, buttonTexture.getWidth(), buttonTexture.getHeight());
        buttonTextureRegionDrawable = new TextureRegionDrawable(buttonTextureRegion);
        textButtonStyle = new TextButton.TextButtonStyle(buttonTextureRegionDrawable, buttonTextureRegionDrawable, buttonTextureRegionDrawable, this.font);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        font.dispose();
        buttonTexture.dispose();
        stage.dispose();
        for(Texture streak: streakTextures){
            streak.dispose();
        }
    }
}
