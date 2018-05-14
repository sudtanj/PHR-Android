/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/6/18 5:49 PM
 */

package sud_tanj.com.phr_android.Sensor.HardwareDriver;

import com.physicaloid.lib.Physicaloid;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Sensor.Interface.EmbeddedScript;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 14/01/2018 - 19:38.
 * <p>
 * This class last modified by User
 */

public abstract class ArduinoUno implements EmbeddedScript {
    private Physicaloid mPhysicaloid = null;

    /**
     * With getmPhysicaloid, you will get the Arduino object to be able to interact with the
     * Arduino hardware
     *
     * @param
     * @return Arduino Object
     */
    protected Physicaloid getmPhysicaloid() {
        if (mPhysicaloid == null)
            mPhysicaloid = new Physicaloid(Global.getContext());
        return mPhysicaloid;
    }

    /**
     * writeCodeToArduinoUno is able to write an already compiled Arduino Uno code
     * to the Arduino Hardware. Due to the limitation on the Android OS, Arduino code
     * can't be compiled on runtime.
     *
     * @param
     * @param codeInHex
     */
    protected void writeCodeToArduinoUno(String codeInHex) {
        if (getmPhysicaloid().open()) {
            byte[] buf = codeInHex.getBytes();
            getmPhysicaloid().write(buf, buf.length);
            getmPhysicaloid().close();
        }
    }

    /**
     * Arduino have the ability to do output via write function in arduino code.
     * This method will be able to capture that output directly from the arduino.
     * This method is run one time only which means it only get the data  at the specific
     * time when it's called once.
     *
     * @param
     * @return String
     */
    protected String getOutputFromArduinoUno() {
        String str = new String("");
        if (mPhysicaloid.open()) {
            byte[] buf = new byte[256];

            mPhysicaloid.read(buf, buf.length);
            str = new String(buf);

            mPhysicaloid.close();
        }
        return str;
    }
}
