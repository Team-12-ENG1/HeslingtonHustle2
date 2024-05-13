package com.heshus.tests.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.heshus.game.entities.Player;
import com.heshus.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class PlayerTests {
    public final static String TESTMAP = "Testing/testmap.tmx";
    public final static String PLAYERSPRITE = "Testing/player-1.png";
    private TiledMap map;

    @Test
    public void testLeftMovement(){
        Player player = init();
        player.update(0.01f);
        assertEquals("Passes when player's horizontal velocity equals zero when no keys are pressed",
                player.getVelocity().x, 0, 0.1);
        player.keyDown(Input.Keys.A);
        player.update(0.01f);
        assertEquals("Passes when player velocity is -200 when A is pressed",
                player.getVelocity().x, -200, 0.1);
        player.keyUp(Input.Keys.A);
    }

    private Player init(){
        map = new TmxMapLoader().load(TESTMAP);
        return new Player(new Sprite(new Texture(PLAYERSPRITE)),(TiledMapTileLayer) map.getLayers().get(0));
    }
}
