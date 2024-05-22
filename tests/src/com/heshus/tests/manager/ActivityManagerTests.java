package com.heshus.tests.manager;


import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.heshus.game.manager.ActivityManager;
import com.heshus.game.manager.DayManager;
import com.heshus.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ActivityManagerTests {

    /*
    * Is a map with 4 objects that a player can interact with:
    * (16,48) - recreation, 5 energy, 2 time
    * (48,48) - eat, 10 energy, 1 time
    * (16,16) - study, 30 energy, 3 time
    * (48,16) - sleep
    *
    * The following test for
    * UR_CONTROL,
    * UR_CHOOSE_TASKS,
    * FR_STATIC_TIME,
    * FR_UNIVERSITY_TASKS and
    * FR_ENERGY_TIME_MANAGEMENT
    * */
    public static final String TEST_MAP = "Testing/testingmap.tmx";

    @Test
    public void performRecreationalActivity() {
        float x = 16;
        float y = 48;
        DayManager dm = new DayManager();
        ActivityManager am = createActivityManager(TEST_MAP, dm);
        Rectangle rectangle = createRectangle(x,y);

        // activity should not be performed
        am.checkActivity(rectangle, false, x, y);
        assertEquals(8, dm.getTime(), 0.0);

        //activity should be performed
        am.checkActivity(rectangle, true, x, y);
        assertEquals(95, dm.getEnergy(), 0.0);
        assertEquals(10, dm.getTime(), 0.0);

    }

    @Test
    public void performStudyActivity(){
        float x = 16;
        float y = 16;
        DayManager dm = new DayManager();
        ActivityManager am = createActivityManager(TEST_MAP, dm);
        Rectangle rectangle = createRectangle(x,y);

        am.checkActivity(rectangle,false,x,y);
        assertEquals(8, dm.getTime(), 0.0);
        assertEquals(100,dm.getEnergy(), 0.0);

        am.checkActivity(rectangle, true, x, y);
        assertEquals(70, dm.getEnergy(), 0.0);
        assertEquals(11, dm.getTime(), 0.0);
    }

    //Test for FR_ENERGY_TIME_MANAGEMENT
    @Test
    public void notEnoughTime(){
        DayManager dm = new DayManager();
        ActivityManager am = createActivityManager(TEST_MAP, dm);
        Rectangle rectangle = createRectangle(16,16);
        dm.setTime(23);
        am.checkActivity(rectangle, true, 16,16);
        assertEquals(23, dm.getTime(), 0.0);
    }

    @Test
    public void notEnoughEnergy(){
        DayManager dm = new DayManager();
        ActivityManager am = createActivityManager(TEST_MAP, dm);
        Rectangle rectangle = createRectangle(16,16);
        dm.setEnergy(3);
        float time = dm.getTime();
        am.checkActivity(rectangle, true, 16,16);
        assertEquals(3, dm.getEnergy(), 0.0);
    }

    // Test for FR_STUDYING_RESTRICTIONS
    @Test
    public void studyTwice(){
        DayManager dm = new DayManager();
        ActivityManager am = createActivityManager(TEST_MAP, dm);
        Rectangle studyRectangle = createRectangle(16,16);
        Rectangle sleepRectangle = createRectangle(48,16);
        am.checkActivity(studyRectangle, true, 16, 16);
        am.checkActivity(sleepRectangle, true, 48, 16);

        am.checkActivity(studyRectangle, true, 16, 16);
        /*
         * Because the player studied the previous day the player
         * should not be able to study twice today.
         * The time should remain the same after attempting to study again.
         */
        float time = dm.getTime();
        // 2nd study shouldn't happen as it is the same day
        am.checkActivity(studyRectangle, true, 16, 16);
        assertEquals(time, dm.getTime(), 0.0);
    }


    @Test
    public void performEatActivity(){
        float x = 48;
        float y = 48;
        DayManager dm = new DayManager();
        ActivityManager am = createActivityManager(TEST_MAP, dm);
        Rectangle rectangle = createRectangle(x,y);

        am.checkActivity(rectangle,false,x,y);
        assertEquals(8, dm.getTime(), 0.0);
        assertEquals(100,dm.getEnergy(), 0.0);

        am.checkActivity(rectangle, true, x, y);
        assertEquals(90, dm.getEnergy(), 0.0);
        assertEquals(9, dm.getTime(), 0.0);
    }

    @Test
    public void performSleepTest() {
        float x = 48;
        float y = 16;
        DayManager dm = new DayManager();
        ActivityManager am = createActivityManager(TEST_MAP, dm);
        Rectangle rectangle = createRectangle(x, y);

        am.checkActivity(rectangle, false, x, y);
        assertEquals(1, dm.getDayNumber(), 0);

        am.checkActivity(rectangle, true, x, y);
        assertEquals(2, dm.getDayNumber(), 0);
    }

    private ActivityManager createActivityManager(String mapPath, DayManager dm){
        TiledMap map = new TmxMapLoader().load(mapPath);
        MapLayer mapLayer = map.getLayers().get("Activities");
        return new ActivityManager(mapLayer, dm);

    }

    private Rectangle createRectangle(float X, float Y){
        return new Rectangle(X,Y, 1,1);
    }
}
