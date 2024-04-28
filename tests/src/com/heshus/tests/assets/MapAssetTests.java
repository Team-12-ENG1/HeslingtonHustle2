package com.heshus.tests.assets;

import static org.junit.Assert.assertTrue;

import com.heshus.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.Gdx;

@RunWith(GdxTestRunner.class)
public class MapAssetTests {

    @Test
    public void DuckExists() {
        assertTrue("Passes when Duck.png is present",
                Gdx.files.internal("../assets/expandedMap/Duck.png").exists());
        assertTrue("Passes when duck.tsx is present",
                Gdx.files.internal("../assets/expandedMap/Duck/png").exists());
    }

    @Test
    public void EatExists(){
        assertTrue("Passes when eat.png is present",
                Gdx.files.internal("../assets/expandedMap/Eat.png").exists());
        assertTrue("Passes when EatTile.png exists",
                Gdx.files.internal("../assets/expandedMap/EatTile.png").exists());
    }

    @Test
    public void CollisionFileExists(){
        assertTrue("Passes when Collision.tsx is present",
                Gdx.files.internal("../assets/expandedMap/Collision.tsx").exists());
    }

}
