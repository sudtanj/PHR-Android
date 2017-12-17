/*
 * Create by Sudono Tanjung
 * Copyright (c) 2017. All rights reserved.
 *
 * Last Modified by User on 12/14/17 6:51 PM
 */

package sud_tanj.com.phr_android;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

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
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("sud_tanj.com.phr_android", appContext.getPackageName());
    }
}
