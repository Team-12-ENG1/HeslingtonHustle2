package com.heshus.game.manager;

public class Score implements Comparable<Score> {
    private String name;
    private long score;

    // Zero-argument constructor for Json
    public Score() {
        this.name = null;
        this.score = 0;
    }

    public Score(String name, long score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public long getScore() {return score;}
    public void setScore(int score) {this.score = score;}

    // This is used when sorting all the scores created in the game
    @Override
    public int compareTo(Score o) {
        return Long.compare(this.score, o.score);
    }
}
