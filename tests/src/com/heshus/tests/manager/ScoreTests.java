package com.heshus.tests.manager;

import com.heshus.game.manager.Score;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScoreTests {

    @Test
    public void compareScores(){
        Score lower = new Score("name", 10);
        Score upper = new Score("name", 90);
        assertEquals(-1, lower.compareTo(upper));
        assertEquals(1, upper.compareTo(lower));
    }
}
