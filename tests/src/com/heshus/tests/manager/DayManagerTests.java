package com.heshus.tests.manager;


import com.heshus.game.manager.DayManager;
import org.junit.Test;

import static org.junit.Assert.*;


public class DayManagerTests {

    //tests that the day counter increments when the method is called
    @Test
    public void dayIncrements() {
        DayManager dm = new DayManager();
        int prevDayNum = dm.currentDay.getDayNumber();
        dm.incrementDay();
        assertEquals(prevDayNum + 1, dm.currentDay.getDayNumber());

    }

    //tests that gameover is set to true after the 7th day
    @Test
    public void gameOverAfterLastDay(){
        DayManager dm = new DayManager();
        for(int i = 0; i<7;i++){
            dm.incrementDay();
        }
        assertFalse(dm.getGameOver());
        dm.incrementDay();
        assertTrue(dm.getGameOver());
    }
}
