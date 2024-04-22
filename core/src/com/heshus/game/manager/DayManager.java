package com.heshus.game.manager;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Manages how the current day is changed and whether the game has finished
 */
public class DayManager {
    private Day currentDay;
    public boolean gameOver;
    public int overallEatCount = 0;
    public int overallStudyCount = 0;
    public int overallRecreationalCount = 0;

    public static Dictionary<Integer,Dictionary<String,Integer>> statsByDay;

    public DayManager(){
        currentDay = new Day(1, 8, 100);
        statsByDay = new Hashtable<Integer,Dictionary<String,Integer>>();
        gameOver = false;
    }
    /**
     * Controls what happens at the end of the day
     * If the current day is less than or equal to 7 then reset relevant variables
     * Else, the game is over
     */
    public void incrementDay(){
        if(currentDay.getDayNumber() <= 7){
            int dayNum = currentDay.getDayNumber();
            statsByDay.put(dayNum, currentDay.summariseDay());
            overallEatCount += currentDay.getEatScore();
            overallRecreationalCount += currentDay.getRecreationalScore();
            overallStudyCount += currentDay.getStudyScore();
            currentDay = new Day(dayNum+1,8,100);
        }
        else{
            this.setGameOver(true);
        }
    }
    public void incrementStudyScore(){
        overallStudyCount++;
        currentDay.incrementStudyScore();
    }
    public void incrementRecreationalScore(){
        overallRecreationalCount++;
        currentDay.incrementRecreationalScore();
    }
    public void incrementEatScore(){
        overallEatCount++;
        currentDay.incrementEatScore();
    }
    public void setTime(float time) {
        currentDay.setTime(time);
    }
    public void setEnergy(int energy){
        currentDay.setEnergy(energy);
    }
    public int getEnergy(){
        return currentDay.getEnergy();
    }
    public float getTime(){
        return currentDay.getTime();
    }
    public boolean getGameOver() { return this.gameOver; }
    public void setGameOver(boolean state) { this.gameOver = state; }

    public int getDayNumber() {
        return currentDay.getDayNumber();
    }
}
