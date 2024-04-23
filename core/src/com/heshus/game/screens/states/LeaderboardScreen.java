package com.heshus.game.screens.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import static com.heshus.game.engine.Play.state;


import java.util.Scanner;

public class LeaderboardScreen {
    BitmapFont font;
    private Stage stage;
    private Camera camera;
    Table table;
    private Sound clickSound;
    private final int returnState;

    public LeaderboardScreen(int returnState, Camera camera, ExtendViewport viewport) {
        this.camera = camera;
        this.stage = new Stage(viewport);
        this.returnState = returnState;
        clickSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/switch2.ogg"));


        FileHandle handle = Gdx.files.internal("data/scores.csv");
        Scanner scanner = new Scanner(handle.read());

        // Set up font
        font = new BitmapFont(Gdx.files.internal("Fonts/monogram/pixel.fnt"), false);
        font.getData().setScale(.5F);
        font.setColor(Color.BLACK);

        Label title = new Label("Leaderboard:", new Label.LabelStyle(font, Color.WHITE));

        // Set TextButtonStyle
        Texture buttonTexture = new Texture("UI/button_up.png");
        TextureRegion buttonTextureRegion = new TextureRegion(buttonTexture, buttonTexture.getWidth(), buttonTexture.getHeight());
        TextureRegionDrawable buttonTextureRegionDrawable =new TextureRegionDrawable(buttonTextureRegion);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(buttonTextureRegionDrawable, buttonTextureRegionDrawable, buttonTextureRegionDrawable, this.font);
        TextButton backBtn = new TextButton("BACK", textButtonStyle);

        table = new Table();
        stage.addActor(table);

        // Layout the leaderboard
        table.add(title).fillX().colspan(2);

        while (scanner.hasNextLine()) {
            table.row().pad(3, 0, 0, 0);
            String[] values = scanner.nextLine().split(",");
            table.add(new Label(values[0], new Label.LabelStyle(font, Color.WHITE))).center();
            table.add(new Label(String.valueOf(values[1]), new Label.LabelStyle(font, Color.WHITE))).center();
        }

        table.row().padTop(6);
        table.add(backBtn).center().width(buttonTexture.getWidth()).height(buttonTexture.getHeight()).colspan(2);

        backBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                clickSound.play();
                state = returnState;
                return false;
            }
        });
    }

    public void update() {
        Gdx.input.setInputProcessor(stage);
        table.setPosition(camera.position.x, camera.position.y);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        font.dispose();
    }
}
