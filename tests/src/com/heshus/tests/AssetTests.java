package com.heshus.tests;

import static org.junit.Assert.assertTrue;

import com.heshus.game.editor.CustomiseSprite;
import com.heshus.game.engine.Play;
import com.heshus.game.manager.DayManager;
import com.heshus.game.manager.Save;
import com.heshus.game.screens.states.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.Gdx;


@RunWith(GdxTestRunner.class)
public class AssetTests {

    @Test
    public void customiseSpritesAssets() {
        assertTrue("Passes when CLICK_SOUND is present",
                Gdx.files.internal(CustomiseSprite.CLICK_SOUND).exists());
        assertTrue("Passes when FONT is present",
                Gdx.files.internal(CustomiseSprite.FONT).exists());
        assertTrue("Passes when LEFT_ARROW is present",
                Gdx.files.internal(CustomiseSprite.LEFT_ARROW).exists());
        assertTrue("Passes when RIGHT_ARROW is present",
                Gdx.files.internal(CustomiseSprite.RIGHT_ARROW).exists());
        assertTrue("Passes when LEFT_ARROW is present",
                Gdx.files.internal(CustomiseSprite.LEFT_ARROW).exists());
    }

    @Test
    public void playAssets(){
        assertTrue("Passes when MAP is present",
                Gdx.files.internal(Play.MAP).exists());
        assertTrue("Passes when WHITE_SQUARE is present",
                Gdx.files.internal(Play.WHITE_SQUARE).exists());
        assertTrue("Passes when TEXT_BUBBLE is present",
                Gdx.files.internal(Play.TEXT_BUBBLE).exists());
        assertTrue("Passes when COUNTER_BOX is present",
                Gdx.files.internal(Play.COUNTER_BOX).exists());
        assertTrue("Passes when VERTICAL_BAR is present",
                Gdx.files.internal(Play.VERTICAL_BAR).exists());

        assertTrue("Passes when TILE_1 is present",
                Gdx.files.internal(Play.TILE_1).exists());
        assertTrue("Passes when TILE_2 is present",
                Gdx.files.internal(Play.TILE_2).exists());
        assertTrue("Passes when TILE_3 is present",
                Gdx.files.internal(Play.TILE_3).exists());
        assertTrue("Passes when TILE_4 is present",
                Gdx.files.internal(Play.TILE_4).exists());

        assertTrue("Passes when CLICK is present",
                Gdx.files.internal(Play.CLICK).exists());
        assertTrue("Passes when VOLUME_UP is present",
                Gdx.files.internal(Play.VOLUME_UP).exists());
        assertTrue("Passes when VOLUME_DOWN is present",
                Gdx.files.internal(Play.VOLUME_DOWN).exists());
    }

    @Test
    public void saveAssets(){
        assertTrue("Passes when SCORES is present",
                Gdx.files.internal(Save.SCORES).exists());
    }

    @Test
    public void leaderBoardAssets(){
        assertTrue("Passes when CLICK is present",
                Gdx.files.internal(LeaderboardScreen.CLICK).exists());
        assertTrue("Passes when FONT is present",
                Gdx.files.internal(LeaderboardScreen.FONT).exists());
        assertTrue("Passes when BUTTON is present",
                Gdx.files.internal(LeaderboardScreen.BUTTON).exists());
    }

    @Test
    public void mainMenuAssets(){
        assertTrue("Passes when CLICK is present",
                Gdx.files.internal(MainMenuScreen.CLICK).exists());
        assertTrue("Passes when FONT is present",
                Gdx.files.internal(MainMenuScreen.FONT).exists());
        assertTrue("Passes when BUTTON is present",
                Gdx.files.internal(MainMenuScreen.BUTTON).exists());
        assertTrue("Passes when BUTTON_WIDE is present",
                Gdx.files.internal(MainMenuScreen.BUTTON_WIDE).exists());
    }

    @Test
    public void pauseMenuAssets(){
        assertTrue("Passes when CLICK is present",
                Gdx.files.internal(PauseMenu.CLICK).exists());
        assertTrue("Passes when FONT is present",
                Gdx.files.internal(PauseMenu.FONT).exists());
        assertTrue("Passes when BUTTON is present",
                Gdx.files.internal(PauseMenu.BUTTON).exists());
    }

    @Test
    public void playerNameAssets(){
        assertTrue("Passes when SKIN is present",
                Gdx.files.internal(PlayerNameScreen.SKIN).exists());
        assertTrue("Passes when CLICK is present",
                Gdx.files.internal(PlayerNameScreen.CLICK).exists());
        assertTrue("Passes when FONT is present",
                Gdx.files.internal(PlayerNameScreen.FONT).exists());
        assertTrue("Passes when BUTTON is present",
                Gdx.files.internal(PlayerNameScreen.BUTTON).exists());
    }

    @Test
    public void settingsAssets(){
        assertTrue("Passes when CLICK is present",
                Gdx.files.internal(PlayerNameScreen.CLICK).exists());
        assertTrue("Passes when FONT is present",
                Gdx.files.internal(PlayerNameScreen.FONT).exists());
        assertTrue("Passes when BUTTON is present",
                Gdx.files.internal(PlayerNameScreen.BUTTON).exists());


    }
    
    @Test
    public void gameOverAssets(){
        assertTrue("Passes when BUTTON is present",
                Gdx.files.internal(GameOverScreen.BUTTON).exists());
        assertTrue("Passes when FONT is present",
                Gdx.files.internal(GameOverScreen.FONT).exists());
        assertTrue("Passes then DUCKS is present",
                Gdx.files.internal(GameOverScreen.ICONS+ DayManager.DUCKS).exists());
        assertTrue("Passes then GYMRAT is present",
                Gdx.files.internal(GameOverScreen.ICONS+ DayManager.GYMRAT).exists());
        assertTrue("Passes then BOOKWORM is present",
                Gdx.files.internal(GameOverScreen.ICONS+ DayManager.BOOKWORM).exists());
    }

}
