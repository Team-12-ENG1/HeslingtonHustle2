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

/**
 * This creates a view of the leaderboard, which is displayed to the user when clicking the "Leaderboard" button
 */
public class LeaderboardScreen {
    private final BitmapFont font;
    private final Stage stage;
    private final Camera camera;
    private final Table table;
    private final Sound clickSound;
    private Texture buttonTexture;
    private TextButton.TextButtonStyle textButtonStyle;

    public static final String CLICK = "Sounds/switch2.ogg";
    public static final String FONT = "Fonts/monogram/pixel.fnt";
    public static final String BUTTON = "UI/button_up.png";



    public LeaderboardScreen(int returnState, Camera camera, ExtendViewport viewport) {
        this.camera = camera;
        this.stage = new Stage(viewport);
        clickSound = Gdx.audio.newSound(Gdx.files.internal(CLICK));

        // Load the save file
        Save.load(Save.SCORES);
        Score[] highScores = gd.getScores();
        Arrays.sort(highScores, Collections.reverseOrder());

        // Set up font
        font = new BitmapFont(Gdx.files.internal(FONT), false);
        font.getData().setScale(.5F);
        font.setColor(Color.BLACK);

        Label title = new Label("Leaderboard:", new Label.LabelStyle(font, Color.WHITE));

        setButtonStyle();
        TextButton backBtn = new TextButton("BACK", textButtonStyle);

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
        buttonTexture = new Texture(BUTTON);
        TextureRegion buttonTextureRegion = new TextureRegion(buttonTexture, buttonTexture.getWidth(), buttonTexture.getHeight());
        TextureRegionDrawable buttonTextureRegionDrawable = new TextureRegionDrawable(buttonTextureRegion);
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
