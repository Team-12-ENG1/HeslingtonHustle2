package com.heshus.tests.manager;

import com.heshus.game.manager.Day;
import com.heshus.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(GdxTestRunner.class)
public class DayTest {
    //tests that the Eat Score increments when the method is called
    @Test
    public void incrementEatScoreTest(){
        Day day = new Day(1,8,100);
        int prevEatScore = day.getEatScore();
        day.incrementEatScore();
        assertEquals(prevEatScore + 1, day.getEatScore());
        prevEatScore = day.getEatScore();
        day.incrementEatScore();
        assertEquals(prevEatScore + 1, day.getEatScore());

    }
    // tests that the Study Score is incremented after calling the method
    @Test
    public void incrementStudyScoreTest(){
        Day day = new Day(1,8,100);
        int prevStudyScore = day.getStudyScore();
        day.incrementStudyScore("library");
        assertEquals(prevStudyScore + 1, day.getStudyScore());
        prevStudyScore = day.getStudyScore();
        day.incrementStudyScore("library");
        assertEquals(prevStudyScore + 1, day.getStudyScore());
    }

    // tests that the recreational score is incremented after calling the method
    @Test
    public void incrementRecreationalScoreTest(){
        Day day = new Day(1,8,100);
        int prevRecreationalScore = day.getRecreationalScore();
        day.incrementRecreationalScore("gym");
        assertEquals(prevRecreationalScore + 1, day.getRecreationalScore());
        prevRecreationalScore = day.getRecreationalScore();
        day.incrementRecreationalScore("ducks");
        assertEquals(prevRecreationalScore + 1, day.getRecreationalScore());
    }
    //checks the method returns the correct string after entering the time
    @Test
    public void convertTimeTest(){
        Day day = new Day(1,8,100);
        assertEquals("morning",day.convertTime((float)8.0));
        Day day1 = new Day(1,12,100);
        assertEquals("afternoon",day1.convertTime((float)12.0));
        Day day2 = new Day(1,17,100);
        assertEquals("evening",day2.convertTime((float)17.0));

    }

}
