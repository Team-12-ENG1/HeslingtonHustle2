package com.heshus.game.manager;

import java.util.Arrays;
import java.util.Collections;

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

    public Score[] getScores() {
        return scores;
    }

    public void setScores(Score[] scores) {
        this.scores = scores;
    }

    public boolean isHighScore(Score score) { return score.getScore() > scores[MAX_SCORES-1].getScore(); }

    public void addHighScore(long score, String name) {
        Score newScore = new Score(name, score);
        if (isHighScore(newScore)) {
            scores[MAX_SCORES-1] = newScore;
            // Get a descending scores array
            Arrays.sort(scores);
        }
    }
}
