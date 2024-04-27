package com.heshus.game.manager;

import java.util.Arrays;
import java.util.Collections;

/**
 * Handles the leaderboard, which is an array of {@link Score} instances.
 */
public class GameData {
    private final int MAX_SCORES = 10;
    private Score[] scores;

    public GameData() {
        scores = new Score[MAX_SCORES];
        init();
    }

    // Create a blank scores list
    public void init() {
        for (int i = 0; i < MAX_SCORES; i++) {
            scores[i] = new Score("player"+String.valueOf(i), (i+1)*10);
        }
        Arrays.sort(scores, Collections.reverseOrder());
    }

    // Getter and setter for the leaderboard scores
    public Score[] getScores() {
        return scores;
    }
    public void setScores(Score[] scores) {
        this.scores = scores;
    }


    /**
     * Check whether a given player's score qualifies to be on the leaderboard
     * @param score A player's score
     * @return A boolean indicating if the player's score qualifies to be on the leaderboard
     */
    public boolean isHighScore(Score score) { return score.getScore() > scores[MAX_SCORES-1].getScore(); }

    public void addHighScore(Score score) {
        scores[MAX_SCORES-1] = score;
        // Get a descending scores array
        Arrays.sort(scores);
    }
}
