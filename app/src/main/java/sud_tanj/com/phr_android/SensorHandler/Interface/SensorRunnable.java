/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/8/18 3:24 PM
 */

package sud_tanj.com.phr_android.SensorHandler.Interface;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Handler.HandlerLoop;
import sud_tanj.com.phr_android.Handler.Interface.HandlerLoopRunnable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 08/05/2018 - 15:24.
 * <p>
 * This class last modified by User
 */
public class SensorRunnable implements HandlerLoopRunnable {
    @Override
    public void run() {
        if (Global.getSensorGateway().isReady()) {
            for (SensorData temp : Global.getSensorGateway().getSensorObject())
                temp.getBackgroundJob().run();
        }

    }

    @Override
    public Boolean isHandlerExpired() {
        return Boolean.FALSE;
    }
}
