package com.heshus.game.screens.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.heshus.game.editor.CustomiseSprite;
import com.heshus.game.engine.HesHusGame;
import com.heshus.game.entities.Player;

import java.awt.*;

public class PlayerNameScreen implements Screen {
    private Stage stage;
    private HesHusGame game;
    private OrthographicCamera camera;
    private Skin skin;

    public PlayerNameScreen(HesHusGame game, OrthographicCamera camera) {
        this.game = game;
        this.camera = camera;
        this.stage = new Stage(new ScreenViewport(camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        skin = new Skin(Gdx.files.internal("UI/uiskin.json"));

        BitmapFont font = new BitmapFont(Gdx.files.internal("Fonts/monogram/pixel.fnt"), false);
        font.getData().setScale(1.5F);
        font.setColor(Color.BLACK);

        Label title = new Label("Enter your player name", skin);
        title.setFontScale(2f);

        TextField enterName = new TextField("", skin);
        enterName.scaleBy(2);

        //set TextButtonStyle
        Texture buttonTexture = new Texture("UI/button_up.png");
        TextureRegion buttonTextureRegion = new TextureRegion(buttonTexture, buttonTexture.getWidth(), buttonTexture.getHeight());
        TextureRegionDrawable buttonTextureRegionDrawable =new TextureRegionDrawable(buttonTextureRegion);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(buttonTextureRegionDrawable, buttonTextureRegionDrawable, buttonTextureRegionDrawable, font);
        TextButton btn = new TextButton("PLAY", textButtonStyle);

        // Arrange table
        table.add(title).fillX().center();
        table.row().pad(10, 0, 10, 0);
        table.add(enterName).fill();
        table.row().pad(0, 0, 10, 0);
        table.add(btn).center().width(buttonTexture.getWidth()*1.5f).height(buttonTexture.getHeight()*1.5f);

        btn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!enterName.getText().isEmpty()) {
                    game.playerName = enterName.getText();
                    dispose();
                    game.setScreen(new CustomiseSprite(game, camera));
                    return true;
                } else { return false; }
            }
        });
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.2f, 0f, 0, 1);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        stage.dispose();
        skin.dispose();
    }
}
