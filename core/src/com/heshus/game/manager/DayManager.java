package com.heshus.game.manager;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Manages how the current day is changed and whether the game has finished
 */
public class DayManager {
    public Day currentDay;
    public static boolean gameOver = false;
    public int overallEatScore = 0;
    public int overallStudyScore = 0;
    public int overallRecreationalScore = 0;

    public static Dictionary<Integer,Dictionary<String,Integer>> statsByDay;

    public DayManager(){
        currentDay = new Day(1, 8, 100);
        statsByDay = new Hashtable<Integer,Dictionary<String,Integer>>();
    }
    /**
     * Controls what happens at the end of the day
     * If the current day is less than 7 then reset relevant variables
     * If the current day is 7 or greater, the game is over
     */
    public static void incrementDay(){
        if(currentDay.getDayNumber() <= 7){
            int dayNum = currentDay.getDayNumber();
            statsByDay.put(dayNum, currentDay.summariseDay());
            overallEatScore += currentDay.getEatScore();
            overallRecreationalScore += currentDay.getRecreationalScore();
            overallStudyScore += currentDay.getStudyScore();
            currentDay = new Day(dayNum++,8,100);
        }
        else{
            gameOver = true;
        }
    }


}
