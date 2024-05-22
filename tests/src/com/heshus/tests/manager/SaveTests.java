package com.heshus.tests.manager;
import com.heshus.game.manager.Save;
import com.heshus.game.manager.GameData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.heshus.tests.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;
import static com.heshus.game.manager.Save.gd;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)

public class SaveTests {

    //mock objects to simulate the behaviour of real objects in the tests
    private FileHandle mockFileHandle; //mock object for handling file operations
    private Files mockFiles;//mock object for managing file system operations
    private Json mockJson;//mock object for JSON serialization and deserialization
    private GameData mockGameData; //Mock object representing the game´s data

    @Before //Run before each test to set up the necessary objects
    public void setup() {
        mockFileHandle = mock(FileHandle.class);
        mockFiles = mock(Files.class);
        mockJson = mock(Json.class);
        mockGameData = mock(GameData.class);
        Application mockApp = mock(Application.class);

        Gdx.files = mockFiles;
        Gdx.app = mockApp;
        gd = mockGameData;
    }

    @Test
    public void SaveTestSuccess() {
        /*testing the save method*/
        /*when mockFiles.local is called with any string argument, it should return "mockFileHandle
        This simulates the creation of a file handle for a local file"*/
        when(mockFiles.local(anyString())).thenReturn(mockFileHandle);

        /*This line converts the "gd" (game data) object to a JSON string using the "json" utility
        * The resulting JSON string is stored in the "expectedJsonString" variable, representing
        * the expected output of the serialization process*/
        String expectedJsonString = json.toJson(gd);

        /*Calling the method to serialize the game data (gd) to a JSON string and write the file
        * at specified path */
        Save.save("Path");

        /*Verifies that the "writeString" method of "mockFileHandle" was called with "expectedJsonString"
        * and "false" as argument. This confirms that the save operation attempted to write the correct
        * JSOn string to the file appending to the existing content*/
        verify(mockFileHandle).writeString(expectedJsonString, false);
    }

    @Test
    public void SaveTestException() {
        /*testing the save method when an Exception occurs*/
        when(mockFiles.local(anyString())).thenReturn(mockFileHandle);

        doThrow(new RuntimeException("Test Exception")).when(mockFileHandle).writeString(anyString(), eq(false));

        Save.save("testPath");

        verify(Gdx.app).exit();
    }

    @Test
    public void LoadTestSuccess() {

        when(mockFiles.local(anyString())).thenReturn(mockFileHandle);
        when(mockFileHandle.exists()).thenReturn(true);
        when(mockJson.fromJson(eq(GameData.class), eq(mockFileHandle))).thenReturn(mockGameData);

        Save.load("testPath");

        assertNotNull(gd);
    }

    @Test
    public void LoadTestFileNotExists() {
        when(mockFiles.local(anyString())).thenReturn(mockFileHandle);
        when(mockFileHandle.exists()).thenReturn(false);

        Save.load("testPath");

        assertNotNull(gd);
        verify(mockFileHandle, never()).readString();
    }

    @Test
    public void LoadTestException() {
        when(mockFiles.local(anyString())).thenReturn(mockFileHandle);
        doThrow(new RuntimeException("Test Exception")).when(mockFileHandle).readString();

        Save.load("testPath");

        verify(Gdx.app).exit();
    }

    @Test
    public void SaveFileExistsTest() {
        when(mockFiles.internal(anyString())).thenReturn(mockFileHandle);
        when(mockFileHandle.exists()).thenReturn(true);

        assertTrue(Save.saveFileExists());
    }

    @Test
    public void SaveFileNotExistsTest() {
        when(mockFiles.internal(anyString())).thenReturn(mockFileHandle);
        when(mockFileHandle.exists()).thenReturn(false);

        assertFalse(Save.saveFileExists());
    }


}
