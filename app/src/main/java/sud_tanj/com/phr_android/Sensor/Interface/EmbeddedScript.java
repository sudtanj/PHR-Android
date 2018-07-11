/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/14/18 3:44 PM
 */

package sud_tanj.com.phr_android.Sensor.Interface;

import java.util.ArrayList;

import sud_tanj.com.phr_android.Database.Sensor.SensorData;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 10/01/2018 - 19:55.
 * <p>
 * This class last modified by User
 */

public interface EmbeddedScript extends Runnable{

    Boolean isScriptRunOnce();

    void analyzeData();

}
