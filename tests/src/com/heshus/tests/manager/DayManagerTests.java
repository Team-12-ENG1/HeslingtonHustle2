package com.heshus.tests.manager;


import com.heshus.game.manager.DayManager;
import com.heshus.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class DayManagerTests {

    //tests that the day counter increments when the method is called
    @Test
    public void dayIncrements() {
        DayManager dm = new DayManager();
        int prevDayNum = dm.getDayNumber();
        dm.incrementDay();
        assertEquals(prevDayNum + 1, dm.getDayNumber());
    }

    /*
     * Tests that gameover is set to true after the 7th day
     * UR_SLEEP_FEATURE_TASK
     */
    @Test
    public void gameOverAfterLastDay(){
        DayManager dm = new DayManager();
        //increment 6 times
        dm.incrementDay();
        dm.incrementDay();
        dm.incrementDay();
        dm.incrementDay();
        dm.incrementDay();
        dm.incrementDay();
        assertFalse(dm.getGameOver());
        dm.incrementDay();
        assertTrue(dm.getGameOver());
    }


    // Following are tests for FR_SCORE
    @Test
    public void missTwoStudyDays() {
        int[] eatArray = {3, 3, 3, 3, 3, 3, 3};
        int[] studyArray = {1, 0, 0, 1, 1, 1, 1};
        int[] recArray = {1, 1, 1, 1, 1, 1, 1};
        DayManager dm = runweek(eatArray, studyArray, recArray);

        assertTrue("Passes when missing 2 days of study fails the player",30 > dm.calculateScore());

    }

    @Test
    public void studyEveryDay(){
        int[] eatArray = new int[]{3, 3, 3, 3, 3, 3, 3};
        int[] studyArray = new int[]{1, 0, 1, 1, 1, 1, 1};
        int[] recArray = new int[]{1, 1, 1, 1, 1, 1, 1};
        DayManager dm = runweek(eatArray, studyArray, recArray);
        assertTrue("Passes when missing one day of study does not fail the player,",dm.calculateScore() > 40);
    }


    //
    @Test
    public void overStudying(){

        int[] studyArray1 = {1,2,1,1,2,1,1};
        int[] studyArray2 = {2,2,2,2,2,2,2};
        int[] eatArray = {2,2,2,2,2,2,2};
        int[] recArray = {1,1,1,1,1,1,1};

        //represents a base case where one of each activity takes place per day
        DayManager dm1 = runweek(eatArray, studyArray1, recArray);
        //represents a case of over-studying where the score should be decreased
        DayManager dm2 = runweek(eatArray, studyArray2, recArray);

        assertTrue(dm1.calculateScore() > dm2.calculateScore());
    }

    @Test
    public void normalPlayScoreTest(){
        DayManager dm = new DayManager();
        int[] studyArray = {1,1,1,1,1,1,1};
        int[] eatArray = {3,3,3,3,3,3,3};
        int[] recArray = {1,1,1,1,0,1,1};
        for (int i = 0; i < studyArray.length; i++) {
            runDay(dm, eatArray[i], studyArray[i], recArray[i]);
        }
        int score = dm.calculateScore();
        assertTrue(dm.calculateScore()>40);
        assertTrue(dm.calculateScore()<=100);

    }

    //Test for FR_STREAKS
    @Test
    public void getStreaksTest(){

        DayManager dm = new DayManager();
        //Populating the dictionary

        for (int i=0;i<5;i++) {dm.incrementRecreationalScore("gym");}
        for (int y=0;y<4;y++) {dm.incrementRecreationalScore("ducks");}
        for (int z=0;z<3;z++){dm.incrementStudyScore("library");}

        // Call the getStreaks() method
        List<String[]> result = dm.getStreaks();

        // Assert the size and content of the result list

        assertEquals(2, result.size());

        assertEquals("Gym Rat", result.get(0)[0]);
        assertEquals("GymRat.png", result.get(0)[1]);

        assertEquals("Duck Duck Go!", result.get(1)[0]);
        assertEquals("Ducks.png", result.get(1)[1]);

        for (int z=0;z<4;z++){dm.incrementStudyScore("library");}
        List<String[]> result1 = dm.getStreaks();

        //streakTracker.put("Bookworm", 4);
        assertEquals("Bookworm", result1.get(0)[0]);
        assertEquals("BookWorm.png", result1.get(0)[1]);

    }


    private DayManager runweek(int[] eatArray, int[] studyArray, int[] recArray) {
        DayManager dm = new DayManager();
        runDay(dm, eatArray[0], studyArray[0], recArray[0]);
        runDay(dm, eatArray[1], studyArray[1], recArray[1]);
        runDay(dm, eatArray[2], studyArray[2], recArray[2]);
        runDay(dm, eatArray[3], studyArray[3], recArray[3]);
        runDay(dm, eatArray[4], studyArray[4], recArray[4]);
        runDay(dm, eatArray[5], studyArray[5], recArray[5]);
        runDay(dm, eatArray[6], studyArray[6], recArray[6]);
        return dm;
    }

    /**
     * Simulates a day being played with
     */
    private void runDay(DayManager dm, int eat, int study, int rec){
        boolean dayover = false;
        while (!dayover){
            if (eat > 0){
                dm.incrementEatScore("placeholder");
                eat--;
            }
            if (study > 0){
                dm.incrementStudyScore("placeholder");
                study--;
            }
            if (rec > 0){
                dm.incrementRecreationalScore("placeholder");
                rec--;
            }
            dayover = (rec==0)&&(study==0)&&(eat==0);
        }
        dm.incrementDay();
    }

}
