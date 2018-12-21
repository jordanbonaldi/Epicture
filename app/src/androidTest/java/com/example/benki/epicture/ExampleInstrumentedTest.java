package com.example.benki.epicture;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.benki.epicture.Application.Epicture;
import com.example.benki.epicture.ImgurAPI.RequestManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.benki.epicture", appContext.getPackageName());
    }

    @Test
    public void testAccessToken() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Epicture epicture = (Epicture) appContext.getApplicationContext();
        epicture.setAccessToken("example-access-token");

        assertEquals("example-access-token", ((Epicture)appContext.getApplicationContext()).getAccessToken());
    }

    @Test
    public void testClientId() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Epicture epicture = (Epicture) appContext.getApplicationContext();
        epicture.setCliendid("example-client-id");

        assertEquals("example-client-id", ((Epicture)appContext.getApplicationContext()).getCliendid());
    }

    @Test
    public void testRequestManager() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Epicture epicture = (Epicture) appContext.getApplicationContext();
        epicture.setAccessToken("example-access-token");
        epicture.setCliendid("example-client-id");
        epicture.setUsername("benki");
        epicture.createManager();
        RequestManager requestManager = epicture.getManager();

        assertEquals(requestManager, ((Epicture)appContext.getApplicationContext()).getManager());
    }
}
