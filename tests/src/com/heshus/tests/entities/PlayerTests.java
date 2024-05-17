package com.heshus.tests.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.heshus.game.entities.Player;
import com.heshus.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(GdxTestRunner.class)
public class PlayerTests {
    public final static String TESTMAP = "Testing/testmap.tmx";
    public final static String PLAYERSPRITE = "Testing/player-1.png";
/*
* The following movement tests are here to test for
* UR_CONTROL and FR_CONTROL
* */

    @Test
    public void testAKeyMovement() {
        testMovementX(Input.Keys.A, -200);
    }
    @Test
    public void testDKeyMovement(){
        testMovementX(Input.Keys.D, 200);
    }
    @Test
    public void testLeftKeyMovement(){
        testMovementX(Input.Keys.LEFT, -200);
    }
    @Test
    public void testRightKeyMovement(){
        testMovementX(Input.Keys.RIGHT, 200);
    }
    @Test
    public void testWKeyMovement(){
        testMovementY(Input.Keys.W, 200);
    }
    @Test
    public void testUpKeyMovement(){
        testMovementY(Input.Keys.UP, 200);
    }
    @Test
    public void testSKeyMovement(){
        testMovementY(Input.Keys.S, -200);
    }
    @Test
    public void testDownKeyMovement(){
        testMovementY(Input.Keys.DOWN, -200);
    }

    @Test
    public void testDiagonalMovementWD(){
        Player player = init();
        player.keyDown(Input.Keys.W);
        player.keyDown(Input.Keys.D);
        player.update(0.01f);
        Vector2 vel = player.getVelocity();
        assertTrue("The player moves diagonally when W and D are pressed",
                (vel.x>100&&vel.y>100));
        assertEquals("The player moves diagonally at a speed of 200",
                200, vel.len(), 5);
    }
    @Test
    public void testDiagonalMovementWA(){
        Player player = init();
        player.keyDown(Input.Keys.W);
        player.keyDown(Input.Keys.A);
        player.update(0.01f);
        Vector2 vel = player.getVelocity();
        assertTrue("The player moves diagonally when W and A are pressed",
                (vel.x<-100&&vel.y>100));
        assertEquals("The player moves diagonally at a speed of 200",
                200, vel.len(), 5);
    }
    @Test
    public void testDiagonalMovementSA(){
        Player player = init();
        player.keyDown(Input.Keys.S);
        player.keyDown(Input.Keys.A);
        player.update(0.01f);
        Vector2 vel = player.getVelocity();
        assertTrue("The player moves diagonally when S and A are pressed",
                (vel.x<-100&&vel.y<-100));
        assertEquals("The player moves diagonally at a speed of 200",
                200, vel.len(), 5);
    }
    @Test
    public void testDiagonalMovementSD(){
        Player player = init();
        player.keyDown(Input.Keys.S);
        player.keyDown(Input.Keys.D);
        player.update(0.01f);
        Vector2 vel = player.getVelocity();
        assertTrue("The player moves diagonally when S and D are pressed",
                (vel.x>100&&vel.y<-100));
        assertEquals("The player moves diagonally at a speed of 200",
                200, vel.len(), 5);
    }


    private void testMovementX(int key, float expected){
        Player player = init();
        player.update(0.01f);
        assertEquals("Passes when player's horizontal velocity equals zero when no keys are pressed",
                0, player.getVelocity().x, 0.1);
        player.keyDown(key);
        player.update(0.01f);
        assertEquals("Passes when player moves when key is pressed",
                expected, player.getVelocity().x, 0.1);
        player.keyUp(key);
        player.update(0.01f);
        assertEquals("Passes when player stops moving when key not pressed",
                 0, player.getVelocity().x,0.1);
    }

    private void testMovementY(int key, float expected){
        Player player = init();
        player.update(0.01f);
        assertEquals("Passes when player's vertical velocity equals zero when no keys are pressed",
                0, player.getVelocity().y,0.1);
        player.keyDown(key);
        player.update(0.01f);
        assertEquals("Passes when player moves when key is pressed",
                 expected, player.getVelocity().y,0.1);
        player.keyUp(key);
        player.update(0.01f);
        assertEquals("Passes when player stops moving when key not pressed",
                 0, player.getVelocity().y,0.1);
    }

    private Player init(){
        TiledMap map = new TmxMapLoader().load(TESTMAP);
        return new Player(new Sprite(new Texture(PLAYERSPRITE)),(TiledMapTileLayer) map.getLayers().get(0));
    }
}
