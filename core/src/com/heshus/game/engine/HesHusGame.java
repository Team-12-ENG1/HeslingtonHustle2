package com.heshus.game.engine;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
//import com.heshus.game.manager.ActivityManager;
import com.heshus.game.manager.DayManager;

//LUKE IMPORTS
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.heshus.game.screens.states.MainMenuScreen;

/**
 * Main class for HesHus, and it extends the Game class from LibGDX
 * manages some of our initial game settings
 */
public class HesHusGame extends Game {
	/**
	 * the SpriteBatch is used for drawing some of the textures and sprites
	 */
	// we can use the HesHusGame as a second central game class for our screens/states
	public SpriteBatch batch;

	/**
	 *  The font used to write text on the screen with LibGDX's Arial font as
	 *  default
	 */
	public BitmapFont font;

	public DayManager dayManager;

	/**
	 * The Preferences is a hash map that holds certain value
	 */
	public static Preferences settings;
	/**
	 * This method sets up the initial settings for window size
	 */
	public void create() {
		batch = new SpriteBatch();
		// use libGDX's default Arial font
		font = new BitmapFont();
		setDefaultPreferences();
		// adjusts the window size according to requirements
		System.out.println(settings.getInteger("screenWidth"));
		Gdx.graphics.setWindowedMode(settings.getInteger("windowWidth"), settings.getInteger("windowHeight"));
		// setting to the MainMenuScreen
		this.setScreen(new MainMenuScreen(this));
		this.dayManager = new DayManager();
	}
	/**
	 * the render method is called by the game loop from the application
	 */
	// important!
	public void render() {
		super.render();
	}
	/**
	 * the dispose frees up resource, only called when the game is closing
	 */

	public void dispose() {
		// cleans up the Spritebatch resource to prevent memory leaks
		batch.dispose();
		// cleans up the font resource to prevent memory leaks
		font.dispose();
	}

	/**
	 * Sets the default preferences for the screen
	 */
	public void setDefaultPreferences(){
		settings = Gdx.app.getPreferences("userSettings");
		settings.putString("name", "bucket");
		settings.putBoolean("soundOn", true);
		settings.putInteger("windowWidth", 1280);
		settings.putInteger("windowHeight",720);
		settings.putBoolean("isFullScreen", false);
		settings.flush();
	}

}

