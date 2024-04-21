package com.heshus.game.manager;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Manages how the current day is changed and whether the game has finished
 */
public class DayManager {
    public Day currentDay;
    public boolean gameOver;
    public int overallEatScore = 0;
    public int overallStudyScore = 0;
    public int overallRecreationalScore = 0;

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
            overallEatScore += currentDay.getEatScore();
            overallRecreationalScore += currentDay.getRecreationalScore();
            overallStudyScore += currentDay.getStudyScore();
            currentDay = new Day(dayNum+1,8,100);
        }
        else{
            this.setGameOver(true);
        }
    }

    public boolean getGameOver() { return this.gameOver; }
    public void setGameOver(boolean state) { this.gameOver = state; }
}
