package com.heshus.tests.manager;


import com.heshus.game.manager.DayManager;
import com.heshus.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.*;

import java.util.Dictionary;
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

    //tests that gameover is set to true after the 7th day
    @Test
    public void gameOverAfterLastDay(){
        DayManager dm = new DayManager();
        for(int i = 0; i<6;i++){
            runDay(dm, 0, 0, 0);
        }
        assertFalse(dm.getGameOver());
        dm.incrementDay();
        assertTrue(dm.getGameOver());
    }

    @Test
    public void missTwoStudyDays() {
        int[] eatArray = {3, 3, 3, 3, 3, 3, 3};
        int[] studyArray = {1, 0, 0, 1, 1, 1, 1};
        int[] recArray = {1, 1, 1, 1, 1, 1, 1};
        DayManager dm = new DayManager();
        for (int i = 0; i < eatArray.length; i++) {
            runDay(dm, eatArray[i], studyArray[i], recArray[i]);
        }

        assertTrue("Passes when missing 2 days of study fails the player",30 > dm.calculateScore());

    }
    public void studyEveryDay(){

        int[] eatArray = new int[]{3, 3, 3, 3, 3, 3, 3};
        int[] studyArray = new int[]{1, 0, 1, 1, 1, 1, 1};
        int[] recArray = new int[]{1, 1, 1, 1, 1, 1, 1};
        DayManager dm = new DayManager();
        for (int i = 0; i < eatArray.length; i++) {
            runDay(dm, eatArray[i], studyArray[i], recArray[i]);
        }
        assertTrue("Passes when missing one day of study does not fail the player,",dm.calculateScore() > 30);

    }

    //
    @Test
    public void overStudying(){
        //represents a base case where one of each activity takes place per day
        DayManager dm1 = new DayManager();
        //represents a case of over-studying where the score should be decreased
        DayManager dm2 = new DayManager();

        int[] studyArray1 = {1,2,1,2,1,2,1};
        int[] studyArray2 = {3,3,3,3,3,3,3};
        int[] eatArray = {2,2,2,2,2,2,2};
        int[] recArray = {1,1,1,1,1,1,1};

        for (int i = 0; i < 7; i++){
            runDay(dm1, eatArray[i], studyArray1[i], recArray[i]);
            runDay(dm2, eatArray[i], studyArray2[i], recArray[i]);
        }
        assertTrue(dm1.calculateScore() > dm2.calculateScore());
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


    @Test
    public void getStreacksTest(){
        DayManager dm = new DayManager();
        //Populating the dictionary

        Dictionary<String,Integer> streakTracker = new Hashtable<String,Integer>();
        streakTracker.put("Bookworm", 3);
        streakTracker.put("GymRat", 5);
        streakTracker.put("Ducks", 3);

        // Call the getStreaks() method
        List<String[]> result = dm.getStreaks();

        // Assert the size and content of the result list
        assertEquals(3, result.size());

        assertNotEquals("Bookworm", result.get(0)[0]);
        assertNotEquals("BookWorm.png", result.get(0)[1]);

        assertEquals("Gym Rat", result.get(1)[0]);
        assertEquals("GymRat.png", result.get(1)[1]);

        assertEquals("Duck Duck Go!", result.get(2)[0]);
        assertEquals("Ducks.png", result.get(2)[1]);

        streakTracker.put("Bookworm", 4);
        assertEquals("Bookworm", result.get(0)[0]);
        assertEquals("BookWorm.png", result.get(0)[1]);
    }


}
