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

import static com.heshus.game.engine.Play.state;

/**
 * View the credits of the game
 */
public class CreditsScreen {
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

    /**
     * Create the credits view from the main menu
     * @param returnState the view to return to
     * @param camera camera viewing the UI
     * @param viewport camera's viewport
     */
    public CreditsScreen(int returnState, Camera camera, ExtendViewport viewport) {
        this.camera = camera;
        this.stage = new Stage(viewport);
        clickSound = Gdx.audio.newSound(Gdx.files.internal(CLICK));

        // Set up font
        font = new BitmapFont(Gdx.files.internal(FONT), false);
        font.getData().setScale(.5F);
        font.setColor(Color.BLACK);

        setButtonStyle();
        TextButton backBtn = new TextButton("BACK", textButtonStyle);

        Label title = new Label("Credits:", new Label.LabelStyle(font, Color.WHITE));
        Label creators = new Label("Created by Team 12", new Label.LabelStyle(font, Color.WHITE));
        title.setFontScale(.6f);
        creators.setFontScale(.5f);

        String[] names = {"Daniel Agar", "James Leney", "Leyi Ye", "Vanessa Ndiangang", "Matt Smith", "Charles Thompson"};

        table = new Table();
        stage.addActor(table);

        // Layout the credits
        table.add(title).colspan(2).center().padBottom(3);
        table.row();
        table.add(creators).colspan(2).center().padBottom(5);

        for (int i = 0; i < names.length; i+=2) {
            Label name1 = new Label(names[i], new Label.LabelStyle(font, Color.WHITE));
            Label name2 = new Label(names[i+1], new Label.LabelStyle(font, Color.WHITE));
            name1.setFontScale(0.4f);
            name2.setFontScale(0.4f);

            table.row().pad(3, 0, 0, 0);
            table.add(name1).left();
            table.add(name2).right();
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

    /**
     * Update the view
     */
    public void update() {
        Gdx.input.setInputProcessor(stage);
        table.setPosition(camera.position.x, camera.position.y);
        stage.draw();
    }

    /**
     * Dispose of textures
     */
    public void dispose() {
        stage.dispose();
        font.dispose();
        buttonTexture.dispose();
        clickSound.dispose();
    }

    /**
     * Set button styles
     */
    private void setButtonStyle() {
        buttonTexture = new Texture(BUTTON);
        TextureRegion buttonTextureRegion = new TextureRegion(buttonTexture, buttonTexture.getWidth(), buttonTexture.getHeight());
        TextureRegionDrawable buttonTextureRegionDrawable = new TextureRegionDrawable(buttonTextureRegion);
        textButtonStyle = new TextButton.TextButtonStyle(buttonTextureRegionDrawable, buttonTextureRegionDrawable, buttonTextureRegionDrawable, this.font);
    }

}
