package com.heshus.game.manager;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Manages how the current day is changed and whether the game has finished
 */
public class DayManager {
    // New: added counters for the number of times they performed each activity category
    // as well as making the current day private
    private Day currentDay;
    public boolean gameOver;
    public int overallEatCount = 0;
    public int overallStudyCount = 0;
    public int overallRecreationalCount = 0;

    // New: added attributes to help when calculating scores (to implement game rules)
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
        // New: modified the increment day method to take a day of no study into account
        // and the new game rules of not studying for two days will result in a fail
        int dayNum = currentDay.getDayNumber();
        Dictionary<String,Integer> summary = currentDay.summariseDay();

        //if statement checks for the fail condition of not studying two days in a row
        if(summary.get("study") == 0){
            daysOfNoStudy++;
        }else{
            daysOfNoStudy = 0;
        }
        if(daysOfNoStudy > 1){
            fail = true;
        }
        //Adds the output of the summary function into the dictionary containing the summary for each day
        statsByDay.put(dayNum, summary);
        if(dayNum < 7){
            currentDay = new Day(dayNum+1,8,100);
        }
        else{
            this.setGameOver(true);
        }
    }

    // New: added function to calculate score once the game has ended
    public int endGame(){
        //Logic to endgame and save to leaderboard here
        return calculateScore();
    }

    /**
     * A function that calculates the player's final score after finishing the game
     * @return Player's final score
     */
    // New: added the private functions to calculate the player's score at the end of the game
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

    /**
     * This applies any penalties relating to the player's eating habits
     * @param eat The eating section of the score
     * @return The new eating section after applying possible penalties
     */
    private double applyEatPen(double eat){
        if(overallEatCount == 21){
            eat = eat + 20;
        }
        return eat;
    }
    /**
     * This applies any penalties relating to the player's studying habits
     * @param study The studying section of the score
     * @return The new studying section score after applying possible penalties
     */
    private double applyStudyPen(double study){
        if(overallStudyCount>=8 && overallStudyCount<=10){
            return study * 1.1;
        }
        return study * 0.8;
    }

    /**
     * This applies any penalties relating to the player's recreational activities
     * @param rec The recreational section of the score
     * @return The new recreational section score after applying possible penalties
     */
    private double applyRecPen(double rec){
        if(rec > 9){
            rec = rec * 0.8;
        }
        return rec;
    }

    public void incrementStudyScore(String place){
    // New: Added functions to increment the player's score for each category
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

    // New: Added getters and setters for each of activity's properties as well as the game over and day
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
