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

    private int daysOfNoStudy = 0;
    private boolean fail = false;

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
        int dayNum = currentDay.getDayNumber();
        Dictionary<String,Integer> summary = currentDay.summariseDay();
        if(summary.get("study") == 0){
            daysOfNoStudy++;
        }else{
            daysOfNoStudy = 0;
        }
        if(daysOfNoStudy > 1){
            fail = true;
        }
        statsByDay.put(dayNum, summary);
        if(dayNum < 7){
            currentDay = new Day(dayNum+1,8,100);
        }
        else{
            this.setGameOver(true);
        }
    }
    public int endGame(){
        //Logic to endgame and save to leaderboard here
        return calculateScore();
    }
    public int calculateScore(){
        if(fail){
            return 0;
        }
        double eat = 3 * overallEatCount;
        double study = 0;
        double rec = 8 * overallRecreationalCount;

        eat = applyEatPen(eat);
        study = applyStudyPen(study);
        rec = applyRecPen(rec);

        return (int) (eat + rec + study)/3;
    }
    private double applyEatPen(double eat){
        if(overallEatCount == 21){
            eat = eat + 20;
        }
        return eat;
    }
    private double applyStudyPen(double study){
        if(overallStudyCount>=8 && overallStudyCount<=10){
            return study * 1.1;
        }
        return study * 0.8;
    }
    private double applyRecPen(double rec){
        if(rec > 9){
            rec = rec * 0.8;
        }
        return rec;
    }

    public void incrementStudyScore(String place){
        overallStudyCount++;
        currentDay.incrementStudyScore(place);
    }
    public void incrementRecreationalScore(String place){
        overallRecreationalCount++;
        currentDay.incrementRecreationalScore(place);
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
