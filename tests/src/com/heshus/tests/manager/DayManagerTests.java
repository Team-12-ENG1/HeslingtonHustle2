package com.heshus.tests.manager;


import com.heshus.game.manager.DayManager;
import com.heshus.tests.GdxTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(GdxTestRunner.class)
public class DayManagerTests {

    //tests that the day counter increments when the method is called
    @Test
    public void dayIncrements() {
        DayManager dm = new DayManager();
        int prevDayNum = dm.getDayNumber();
        dm.incrementDay();
        Assert.assertEquals(prevDayNum + 1, dm.getDayNumber());

    }

    //tests that gameover is set to true after the 7th day
    @Test
    public void gameOverAfterLastDay(){
        DayManager dm = new DayManager();
        for(int i = 0; i<6;i++){
            runDay(dm, 0, 0, 0);
        }
        Assert.assertFalse(dm.getGameOver());
        dm.incrementDay();
        Assert.assertTrue(dm.getGameOver());
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

        Assert.assertTrue("Passes when missing 2 days of study fails the player",30 > dm.calculateScore());

    }

    @Test
    public void studyEveryDay(){
        int[] eatArray = new int[]{3, 3, 3, 3, 3, 3, 3};
        int[] studyArray = new int[]{1, 0, 1, 1, 1, 1, 1};
        int[] recArray = new int[]{1, 1, 1, 1, 1, 1, 1};

        DayManager dm = new DayManager();

        for (int i = 0; i < eatArray.length; i++) {
            runDay(dm, eatArray[i], studyArray[i], recArray[i]);
        }
        Assert.assertTrue("Passes when missing one day of study does not fail the player,",dm.calculateScore() > 30);

    }

    //
    @Test
    public void overStudying(){
        //represents a base case where one of each activity takes place per day
        DayManager dm1 = new DayManager();
        //represents a case of over-studying where the score should be decreased
        DayManager dm2 = new DayManager();

        int[] studyArray1 = {1,2,1,1,2,1,1};
        int[] studyArray2 = {2,2,2,2,2,2,2};
        int[] eatArray = {2,2,2,2,2,2,2};
        int[] recArray = {1,1,1,1,1,1,1};

        for (int i = 0; i < 7; i++){
            runDay(dm1, eatArray[i], studyArray1[i], recArray[i]);
            runDay(dm2, eatArray[i], studyArray2[i], recArray[i]);
        }
        Assert.assertTrue(dm1.calculateScore() > dm2.calculateScore());
    }

    @Test
    public void normalPlayScoreTest(){
          DayManager dm = new DayManager();
          int[] studyArray = {1,2,1,1,2,1,1};
          int[] eatArray = {3,3,3,3,3,3,3};
          int[] recArray = {1,1,1,1,1,1,1};
          for (int i = 0; i < studyArray.length; i++) {
              runDay(dm, eatArray[i], studyArray[i], recArray[i]);
          }
          int score = dm.calculateScore();

          Assert.assertTrue(dm.calculateScore()>40);
          Assert.assertTrue(dm.calculateScore()<80);

    }

    @Test
    public void bookwormStreakTest(){
        DayManager dm = new DayManager();

        for(int i = 0; i<3; i++){
            dm.incrementStudyScore("library");
            dm.incrementDay();
        }

        List<String[]> streaks = dm.getStreaks();
        Assert.assertEquals(new ArrayList<String[]>(), streaks);

        for(int i = 0; i<4; i++){
            dm.incrementStudyScore("library");
            dm.incrementDay();
        }

        streaks = dm.getStreaks();
        Assert.assertArrayEquals(streaks.get(0), new String[]{"Bookworm", DayManager.BOOKWORM});

    }
    @Test
    public void ducksStreakTest(){
        DayManager dm = new DayManager();

        for(int i = 0; i<2; i++){
            dm.incrementRecreationalScore("ducks");
            dm.incrementDay();
        }

        List<String[]> streaks = dm.getStreaks();
        Assert.assertEquals(new ArrayList<String[]>(), streaks);

        for(int i = 0; i<2; i++){
            dm.incrementRecreationalScore("ducks");
            dm.incrementDay();
        }

        streaks = dm.getStreaks();
        Assert.assertArrayEquals(streaks.get(0), new String[]{"Duck Duck Go!", DayManager.DUCKS});
    }
    @Test
    public void gymratStreakTest(){
        DayManager dm = new DayManager();

        for(int i = 0; i<4; i++){
            dm.incrementRecreationalScore("gym");
            dm.incrementDay();
        }

        List<String[]> streaks = dm.getStreaks();
        Assert.assertEquals(new ArrayList<String[]>(), streaks);

        for(int i = 0; i<3; i++){
            dm.incrementRecreationalScore("gym");
            dm.incrementDay();
        }

        streaks = dm.getStreaks();
        Assert.assertArrayEquals(streaks.get(0), new String[]{"Gym Rat", DayManager.GYMRAT});

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
