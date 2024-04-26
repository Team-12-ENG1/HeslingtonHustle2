package com.heshus.game.manager;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Represents the day of the week that the current play through is on
 * Constructor can include the 3 unused variables to count the scores for the current day and overall scores separately
 */
public class Day {
    private int dayNumber, studyScore, eatScore, energy, recreationalScore;
    private float time;


    /**
     * Constructor for the current day
     *
     * @param _dayNumber Current day
     * @param _time      Current day's time
     * @param _energy    Current day's energy
     */

    public Day(int _dayNumber, float _time, int _energy) {
        this.dayNumber = _dayNumber;
        this.time = _time;
        this.energy = _energy;
        this.studyScore = 0;
        this.eatScore = 0;
        this.recreationalScore = 0;
    }

    /**
     * @return current day counter
     */
    public int getDayNumber() {
        return this.dayNumber;
    }
    /**
     * +1 to eat counter
     */
    public void incrementEatScore() {
        this.eatScore++;
    }
    public int getEatScore(){
        return this.eatScore;
    }

    /**
     * +1 to study counter
     */
    public void incrementStudyScore() {
        this.studyScore++;
    }
    public int getStudyScore(){
        return this.studyScore;
    }

    /**
     * +1 to recreation counter
     */
    public void incrementRecreationalScore() {
        this.recreationalScore++;
    }
    public int getRecreationalScore(){
        return this.recreationalScore;
    }
    /**
     *
     * @return current day's game time
     */

    public float getTime() { return this.time; }

    /**
     *
     * @return current day's energy
     */
    public int getEnergy() { return this.energy; }

    /**
     * Sets current day's energy to param
     * @param energy
     */
    public void setEnergy(int energy)
    {
        if(energy >= 0){
            this.energy = energy;
        }
        else{
            this.energy = 0;
        }
    }

    // todo: Addition - Day class now summarises each day and states the score achieved
    /**
     * Sets current day's time to param
     * @param time
     */
    public void setTime(float time) { this.time = time; }

    public Dictionary<String, Integer> summariseDay(){
        Dictionary<String, Integer> summary= new Hashtable<>();
        summary.put("eat",this.getEatScore());
        summary.put("study",this.getStudyScore());
        summary.put("rec",this.getRecreationalScore());
        return summary;
    }
}

