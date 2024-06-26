package com.heshus.game.editor;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.heshus.game.engine.HesHusGame;
import com.heshus.game.engine.Play;

import java.util.ArrayList;

public class CustomiseSprite {
    private final HesHusGame game;
    private final Camera camera;
    private final Stage stage;
    private final Sound clickSound;
    private BitmapFont font;
    private final Texture leftarrowTexture;
    private final Texture rightarrowTexture;
    private final Image character;
    private final Table table;

    public static final String CLICK_SOUND = "Sounds/switch2.ogg";
    public static final String FONT = "Fonts/monogram/pixel.fnt";
    public static final String LEFT_ARROW = "UI/keyboard_arrow_left_outline.png" ;
    public static final String RIGHT_ARROW = "UI/keyboard_arrow_right_outline.png" ;



    boolean validPlayer = false;

    // this is the default sprite selected
    // importantly, this number points to an ARRAY INDEX, not the number in the name of the .png file
    // so for player-1 to player-6 it ranges from 0 TO 5 not 1 to 6
    private int playerSelection = 3;
    private final int totalPlayerSpriteChoices = 6;

    private final ArrayList<Texture> textureList = new ArrayList<Texture>();

    /**
     * Create the CustomiseSprite menu (screen) instance
     * @param game The game instance
     * @param camera The current camera object
     * @param viewport The current view's viewport
     */
    public CustomiseSprite(final HesHusGame game, Camera camera, ExtendViewport viewport) {
        this.game = game;
        this.camera = camera;
        this.stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);
        clickSound = Gdx.audio.newSound(Gdx.files.internal(CLICK_SOUND));
        setupFont();

        //set up arrow key textures
        leftarrowTexture = new Texture(LEFT_ARROW);
        rightarrowTexture = new Texture(RIGHT_ARROW);

        //add all player textures to the textureList
        for (int i = 0; i < totalPlayerSpriteChoices; i++) {
            textureList.add(new Texture("Icons/player-" + (i + 1) +".png"));
        }

        // Begin making UI layout
        Label title = new Label("CHOOSE A PLAYER:", new Label.LabelStyle(font, Color.WHITE));
        Label prompt = new Label("Press ENTER to start", new Label.LabelStyle(font, Color.WHITE));

        Image leftarrow = new Image(leftarrowTexture);
        Image rightarrow = new Image(rightarrowTexture);

        character = new Image(textureList.get(playerSelection));

        table = new Table();
        stage.addActor(table);

        // Arrange table layout
        table.add(title).center().colspan(3);
        table.row().pad(10, 0, 10, 0);
        table.add(leftarrow).size(30, 30);
        table.add(character).size(60, 60);
        table.add(rightarrow).size(30, 30);
        table.row().pad(10, 0, 10, 0);
        table.add(prompt).center().colspan(3);
    }

    /**
     * Set up the font for the UI, with a given scale
     */
    private void setupFont() {
        font = new BitmapFont(Gdx.files.internal(FONT), false);
        font.getData().setScale((float) 0.5);
    }

    /**
     * Update the table with the new character to select
     */
    public void update() {
        Gdx.input.setInputProcessor(stage);
        table.setPosition(camera.position.x, camera.position.y);

        //left arrow: decrement playerSelection
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            playerSelection = (playerSelection-- <= 0) ? totalPlayerSpriteChoices-1 : playerSelection--;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            //right arrow: increment playerSelection
            playerSelection = (playerSelection++ >= totalPlayerSpriteChoices-1) ? 0 : playerSelection++;
        }

        //validate player choice (should always pass but good to be safe)
        validPlayer = (playerSelection <= totalPlayerSpriteChoices && playerSelection >= 0);

        if (validPlayer) {
            character.setDrawable(new TextureRegionDrawable(new TextureRegion(textureList.get(playerSelection))));
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                game.setScreen(new Play(game, textureList.get(playerSelection)));
                dispose();
            }
        }
        camera.update();
        stage.draw();
    }

    /**
     * Dispose of all textures
     */
    public void dispose() {
        font.dispose();
        stage.dispose();
        leftarrowTexture.dispose();
        rightarrowTexture.dispose();
        clickSound.dispose();
    }
}
