package com.heshus.game.screens.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import static com.heshus.game.engine.Play.GAME_RUNNING;
import static com.heshus.game.engine.Play.state;

public class PauseMenu{
    private Stage stage;
    private Texture buttonTexture;
    private TextureRegion buttonTextureRegion;
    private TextureRegionDrawable buttonTextureRegionDrawable;
    private TextButton resumeButton;
    private TextButton quitButton;
    private TextButton settingsButton;
    private Table table;
    private int buttonWidth;
    private int buttonHeight;
    private float buttonScale;
    private Table areYouSure;
    private BitmapFont font;
    public PauseMenu(ExtendViewport viewport, Camera camera) {
        //set up font
        font = new BitmapFont(Gdx.files.internal("Fonts/monogram/pixel.fnt"), false);
        font.getData().setScale(1.5F);
        font.setColor(Color.BLACK);

        //BUTTONS
        //Setup textures and variables
        buttonTexture = new Texture("UI/button_up.png");
        buttonTextureRegion = new TextureRegion(buttonTexture, buttonTexture.getWidth(), buttonTexture.getHeight());
        buttonTextureRegionDrawable =new TextureRegionDrawable(buttonTextureRegion);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(buttonTextureRegionDrawable, buttonTextureRegionDrawable, buttonTextureRegionDrawable, font );

        buttonScale = 2;
        buttonHeight = (int) (buttonTexture.getHeight()*buttonScale);
        buttonWidth = (int) (buttonTexture.getWidth()*buttonScale);

        //Resume button:
        resumeButton = new TextButton("RESUME!!", textButtonStyle); //Set the button up
        resumeButton.padBottom(10);
        resumeButton.addListener(new InputListener() {
             public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                 state = GAME_RUNNING;
                 return false;
             }
        });

        //Settings button:
        settingsButton = new TextButton("SETTINGS", textButtonStyle); //Set the button up
        settingsButton.padBottom(10);
        settingsButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return false;
            }
        });

        //Quit button:
        quitButton = new TextButton("QUIT :(", textButtonStyle); //Set the button up
        quitButton.padBottom(10);
        quitButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return false;
            }
        });

        //Table to store buttons
        table = new Table();
        table.add(resumeButton).width(buttonWidth).height(buttonHeight).padBottom((float) buttonHeight /5);
        table.row();
        table.add(settingsButton).width(buttonWidth).height(buttonHeight).padBottom((float) buttonHeight /5);
        table.row();
        table.add(quitButton).width(buttonWidth).height(buttonHeight).padBottom((float) buttonHeight /5);

        stage = new Stage(viewport);
        stage.addActor(table); //Add the table to the stage to perform rendering and take input.
    }
    public void draw(){
        stage.draw();
    }
    public void update(Camera camera){
        Gdx.input.setInputProcessor(stage);
        table.setPosition(camera.position.x, camera.position.y);
    }
    public void dispose(){
        stage.dispose();
        buttonTexture.dispose();
    }
}
