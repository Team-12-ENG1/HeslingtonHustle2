package com.heshus.game.screens.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.heshus.game.editor.CustomiseSprite;
import com.heshus.game.engine.HesHusGame;

public class PlayerNameScreen {
    private BitmapFont font;
    private final Stage stage;
    private final Camera camera;
    private final Table table;
    private final Sound clickSound;
    private Texture buttonTexture;
    private TextButton.TextButtonStyle textButtonStyle;
    private final TextField enterName;
    private HesHusGame game;
    private final Skin skin;

    public PlayerNameScreen(HesHusGame game, Camera camera, ExtendViewport viewport) {
        this.camera = camera;
        this.stage = new Stage(viewport);
        this.game = game;

        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));
        clickSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/switch2.ogg"));
        setupFont(.5f);
        setButtonStyle();

        Label title = new Label("Enter your name:", new Label.LabelStyle(font, Color.WHITE));
        TextButton button = new TextButton("PLAY", textButtonStyle);
        enterName = new TextField("", skin);
        enterName.setMessageText("Max 10 characters");
        enterName.setScale(0.8f);

        table = new Table();
        stage.addActor(table);

        // Arrange table
        table.add(title).fillX().center();
        table.row().pad(10, 0, 10, 0);
        table.add(enterName).center();
        table.row().pad(0, 0, 10, 0);
        table.add(button).center().width(buttonTexture.getWidth()).height(buttonTexture.getHeight());

        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                clickSound.play();
                if ((!enterName.getText().isEmpty()) && (enterName.getText().length() <= 10)) {
                    game.playerName = enterName.getText();
                    game.setScreen(new CustomiseSprite(game, (OrthographicCamera) camera));
                    dispose();
                    return true;
                } else { return false; }
            }
        });
    }

    private void setButtonStyle() {
        buttonTexture = new Texture("UI/button_up.png");
        TextureRegion buttonTextureRegion = new TextureRegion(buttonTexture, buttonTexture.getWidth(), buttonTexture.getHeight());
        TextureRegionDrawable buttonTextureRegionDrawable = new TextureRegionDrawable(buttonTextureRegion);
        textButtonStyle = new TextButton.TextButtonStyle(buttonTextureRegionDrawable, buttonTextureRegionDrawable, buttonTextureRegionDrawable, this.font);
    }

    private void setupFont(float scale) {
        font = new BitmapFont(Gdx.files.internal("Fonts/monogram/pixel.fnt"), false);
        font.getData().setScale(scale);
        font.setColor(Color.BLACK);
    }

    public void update() {
        Gdx.input.setInputProcessor(stage);
        table.setPosition(camera.position.x, camera.position.y);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
        font.dispose();
        buttonTexture.dispose();
        clickSound.dispose();
    }
}
