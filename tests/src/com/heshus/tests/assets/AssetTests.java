package com.heshus.tests.assets;

import static org.junit.Assert.assertTrue;

import com.heshus.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.Gdx;

@RunWith(GdxTestRunner.class)
public class AssetTests {

    @Test
    public void CustomiseSpritesAssets() {
        assertTrue("");
        public static final String CLICK_SOUND = "Sounds/switch2.ogg";
        public static final String FONT = "Fonts/monogram/pixel.fnt";
        public static final String LEFT_ARROW = "UI/keyboard_arrow_left_outline.png" ;
        public static final String RIGHT_ARROW = "UI/keyboard_arrow_right_outline.png" ;
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
