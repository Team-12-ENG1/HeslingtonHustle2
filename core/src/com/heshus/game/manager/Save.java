package com.heshus.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * Manages all reading/writing the leaderboard from/to its JSON file.
 */
public class Save {
    public static GameData gd;
    public static final String SCORES = "data/scores.json";

    /**
     * Save the {@link GameData} into the JSON file. It will try to open the file
     * however if it encounters an error, the game will close.
     */
    public static void save(String path) {
        try {
            FileHandle out = Gdx.files.local(path);
            Json json = new Json();
            String jsonScores = json.toJson(gd);
            // Don't append, as we will alter the scores themselves and then write back
            out.writeString(jsonScores, false);
        }
        catch (Exception e) {
            System.out.println("Saving error");
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    /**
     * Load the data from the JSON file (if it exists) and give it as the {@link GameData}.
     */
    public static void load(String path) {
        try {
            if (!saveFileExists()) {
                init();
                return;
            }
            FileHandle in = Gdx.files.local(path);
            Json json = new Json();
            gd = json.fromJson(GameData.class, in);
        }
        catch (Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    // Just in case
    public static boolean saveFileExists() {
        return Gdx.files.internal(SCORES).exists();
    }

    public static void init() {
        gd = new GameData();
        save(SCORES);
    }
}
