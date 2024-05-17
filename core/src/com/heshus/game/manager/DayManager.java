package com.heshus.game.manager;

import com.heshus.game.engine.Play;
import java.util.*;
import static com.heshus.game.engine.Play.GAME_OVER;

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

    private final Dictionary<String,Integer> streakTracker;

    public static Dictionary<Integer,Dictionary<String,Integer>> statsByDay;

    public DayManager(){
        currentDay = new Day(1, 8, 100);
        statsByDay = new Hashtable<Integer,Dictionary<String,Integer>>();
        gameOver = false;
        streakTracker = new Hashtable<String,Integer>();
        //Add streaks that are going to be tracked below:
        streakTracker.put("GymRat", 0);
        streakTracker.put("Ducks",0);
        streakTracker.put("Bookworm", 0);
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

        // if statement checks for the fail condition of not studying two days in a row
        if(summary.get("study") == 0){
            daysOfNoStudy++;
        }else{
            daysOfNoStudy = 0;
        }
        if(daysOfNoStudy > 1){
            fail = true;
        }
        // Adds the output of the summary function into the dictionary containing the summary for each day
        statsByDay.put(dayNum, summary);
        if(dayNum < 7){
            currentDay = new Day(dayNum+1,8,100);
        }
        else{
            this.setGameOver(true);
            endGame();
        }
    }

    // New: added function to calculate score once the game has ended
    public List<String[]> endGame(){
        // Logic to endgame
        List<String[]> scoreAndStreaks = new ArrayList<String[]>();
        Integer score = calculateScore();
        scoreAndStreaks.add(new String[] {String.valueOf(score)});
        scoreAndStreaks.addAll(getStreaks());
        Play.state = GAME_OVER;
        return scoreAndStreaks;
    }
    public List<String[]> getStreaks(){
        List<String[]> streaks = new ArrayList<String[]>();
        if(this.streakTracker.get("Bookworm") >= 4){
            streaks.add(new String[] {"Bookworm","BookWorm.png"});
        }
        if(this.streakTracker.get("GymRat") >= 5){
            streaks.add(new String[]{"Gym Rat", "GymRat.png"});
        }
        if(this.streakTracker.get("Ducks") >= 3){
            streaks.add(new String[] {"Duck Duck Go!" , "Ducks.png"});
        }
        return streaks;
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
        double study = 5 * overallStudyCount;
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
        int uniqueTimes = getUniquePlaces("Eating");
        if(uniqueTimes == 21){
            return 100;
        }
        if(overallEatCount == 21){
            eat = eat + 20;
        }
        if(uniqueTimes >= 14) {
            eat += 15;
        }
        return Math.min((int)eat,100);
    }
    /**
     * This applies any penalties relating to the player's studying habits
     * @param study The studying section of the score
     * @return The new studying section score after applying possible penalties
     */
    private double applyStudyPen(double study){
        if(overallStudyCount < 7){
            return study;
        }
        if(overallStudyCount>=8 && overallStudyCount<=11){
            study += 20;
        }else{
            study *= 0.7;
        }
        study += 4 * (int)(getUniquePlaces("Study")/7);
        return Math.min((int)study,100);
    }

    /**
     * This applies any penalties relating to the player's recreational activities
     * @param rec The recreational section of the score
     * @return The new recreational section score after applying possible penalties
     */
    private double applyRecPen(double rec){
        rec = rec * getUniquePlaces("Rec")/7 + 0.5;
        if(overallRecreationalCount > 10){
            rec = rec * 0.8;
        }
        return Math.min((int)rec,100);
    }
    private int getUniquePlaces(String activity){
        //Returns the number of unique places/times(for eating) a given activity was completed
        String key = "unique" + activity;
        int uniquePlaces = 0;
        for(int i = 1; i < 7; i++) {
            uniquePlaces += statsByDay.get(i).get(key);
        }
        return uniquePlaces;
    }

    public void incrementStudyScore(String place){
        // New: Added functions to increment the player's score for each category
        overallStudyCount++;
        if (place.equals("library")) {
            int bookWormCount = this.streakTracker.get("Bookworm");
            this.streakTracker.put("Bookworm", bookWormCount+1);
        }
        currentDay.incrementStudyScore(place);
    }

    public void incrementRecreationalScore(String place){
        if(Objects.equals(place, "gym")){
            int gymCount = this.streakTracker.get("GymRat");
            this.streakTracker.put("GymRat", gymCount+1);
        } else if (Objects.equals(place, "ducks")){
            int duckCount = this.streakTracker.get("Ducks");
            this.streakTracker.put("Ducks",duckCount+1);
        }
        overallRecreationalCount++;
        currentDay.incrementRecreationalScore(place);
    }
    public void incrementEatScore(String place){
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
