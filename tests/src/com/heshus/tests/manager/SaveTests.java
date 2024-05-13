package com.heshus.tests.manager;

import com.heshus.game.manager.Save;
import com.heshus.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import org.mockito.Mockito;

import static com.heshus.game.manager.Save.gd;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)

public class SaveTests {

    @Test
    public void saveTest(){
        String SCORES = "data/scores.json";
        FileHandle out = mock(FileHandle.class);
        Json json = mock(Json.class);
        when(Gdx.files.local(SCORES)).thenReturn(out);
        String jsonScores = "your_json_here"; // Replace with actual JSON
        when(json.toJson(any())).thenReturn(jsonScores);
        assertThrows(Exception.class, Save::save);

    }
}



