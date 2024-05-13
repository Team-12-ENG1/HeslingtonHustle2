package com.heshus.tests.manager;

import com.heshus.game.manager.Save;
import com.heshus.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)

public class SaveTests {

    @Test
    public void saveTest(){
        FileHandle fileHandleMock = Mockito.mock(FileHandle.class);
        Json jsonMock = Mockito.mock(Json.class);
        // Call the method to test
        //when(Gdx.files.local(Save.SCORES)).thenReturn(fileHandleMock);

        // Mock the behavior of toJson() method
        String expectedJson = "{\"key\":\"value\"}"; // Example JSON data
        when(jsonMock.toJson(any())).thenReturn(expectedJson);

        // Call the save method
        Save.save();

        // Verify that writeString method is called with expected arguments
        verify(fileHandleMock).writeString(expectedJson, false);

    }
}



