package com.heshus.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class Save {
    public static GameData gd;

    public static void save() {
        try {
            FileHandle out = Gdx.files.local("data/scores.json");
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

    public static void load() {
        try {
            if (!saveFileExists()) {
                init();
                return;
            }
            FileHandle in = Gdx.files.local("data/scores.json");
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
        return Gdx.files.internal("data/scores.json").exists();
    }

    public static void init() {
        gd = new GameData();
        save();
    }
}
