package com.heshus.game.screens.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.heshus.game.manager.Save;
import com.heshus.game.manager.Score;
import static com.heshus.game.engine.Play.state;
import static com.heshus.game.manager.Save.gd;
import java.util.Arrays;
import java.util.Collections;

public class LeaderboardScreen {
    private BitmapFont font;
    private Stage stage;
    private Camera camera;
    private Table table;
    private Sound clickSound;
    private final int returnState;
    private Texture buttonTexture;
    private TextureRegion buttonTextureRegion;
    private TextureRegionDrawable buttonTextureRegionDrawable;
    private TextButton.TextButtonStyle textButtonStyle;
    private Label title;
    private TextButton backBtn;
    private Score[] highScores;


    public LeaderboardScreen(int returnState, Camera camera, ExtendViewport viewport) {
        this.camera = camera;
        this.stage = new Stage(viewport);
        this.returnState = returnState;
        clickSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/switch2.ogg"));

        // Load the save file
        Save.load();
        highScores = gd.getScores();
        Arrays.sort(highScores, Collections.reverseOrder());

        // Set up font
        font = new BitmapFont(Gdx.files.internal("Fonts/monogram/pixel.fnt"), false);
        font.getData().setScale(.5F);
        font.setColor(Color.BLACK);

        title = new Label("Leaderboard:", new Label.LabelStyle(font, Color.WHITE));

        setButtonStyle();
        backBtn = new TextButton("BACK", textButtonStyle);

        table = new Table();
        stage.addActor(table);

        // Layout the leaderboard
        table.add(title).colspan(3).center();

        for (int i = 0; i < highScores.length; i++) {
            Label rankLabel = new Label(String.valueOf(i+1), new Label.LabelStyle(font, Color.WHITE));
            Label playerLabel = new Label(highScores[i].getName(), new Label.LabelStyle(font, Color.WHITE));
            Label scoreLabel = new Label(String.valueOf(highScores[i].getScore()), new Label.LabelStyle(font, Color.WHITE));
            playerLabel.setFontScale(0.3f);
            scoreLabel.setFontScale(0.3f);
            rankLabel.setFontScale(0.3f);

            table.row().pad(3, 0, 0, 0);
            table.add(rankLabel).left();
            table.add(playerLabel).center();
            table.add(scoreLabel).right();
        }

        table.row().padTop(6);
        table.add(backBtn).center().width(buttonTexture.getWidth()).height(buttonTexture.getHeight()).colspan(3);

        backBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                clickSound.play();
                state = returnState;
                return false;
            }
        });
    }

    private void setButtonStyle() {
        buttonTexture = new Texture("UI/button_up.png");
        buttonTextureRegion = new TextureRegion(buttonTexture, buttonTexture.getWidth(), buttonTexture.getHeight());
        buttonTextureRegionDrawable = new TextureRegionDrawable(buttonTextureRegion);
        textButtonStyle = new TextButton.TextButtonStyle(buttonTextureRegionDrawable, buttonTextureRegionDrawable, buttonTextureRegionDrawable, this.font);
    }

    public void update() {
        Gdx.input.setInputProcessor(stage);
        table.setPosition(camera.position.x, camera.position.y);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        font.dispose();
        buttonTexture.dispose();
        clickSound.dispose();
    }
}
