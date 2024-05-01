package com.heshus.game.manager;

/**
 * A class that holds a player's name and score, where both attributes can be accessed
 * and as it implements {@link Comparable}, allows for the comparison of instances.
 */
public class Score implements Comparable<Score> {
    private String name;
    private int score;

    // Zero-argument constructor for Json
    public Score() {
        this.name = null;
        this.score = 0;
    }

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // Getters and setters for the attributes
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getScore() {return score;}

    // This is used when sorting all the scores created in the game
    @Override
    public int compareTo(Score o) {
        return Double.compare(this.score, o.score);
    }
}
