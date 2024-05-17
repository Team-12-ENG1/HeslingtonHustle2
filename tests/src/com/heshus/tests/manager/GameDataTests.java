package com.heshus.tests.manager;

import com.heshus.game.manager.GameData;
import com.heshus.game.manager.Score;
import org.junit.Test;


import static org.junit.Assert.*;


public class GameDataTests {

    @Test
    public void testGameData() {
        GameData gameData = new GameData();
        gameData.init();

        Score highScore = new Score("name", 96);
        assertTrue(gameData.isHighScore(highScore));

        Score notHighScore = new Score("name", 1);
        assertFalse(gameData.isHighScore(notHighScore));
    }

    @Test
    public void emptyListTest(){
        GameData gameData = new GameData();
        Score score = new Score("name", 11);
        assertTrue(gameData.isHighScore(score));
    }

    @Test
    public void newScoreEqualsLastHighScore() {
        GameData gameData = new GameData();
        gameData.init();
        int lowestScore = gameData.getScores()[GameData.MAX_SCORES-1].getScore();
        Score highScore = new Score("name", lowestScore);
        assertFalse(gameData.isHighScore(highScore));
    }
}
