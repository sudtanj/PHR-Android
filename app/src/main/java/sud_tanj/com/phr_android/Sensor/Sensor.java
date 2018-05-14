/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/14/18 3:42 PM
 */

package sud_tanj.com.phr_android.Sensor;

import sud_tanj.com.phr_android.Sensor.Interface.EmbeddedScript;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 14/05/2018 - 15:42.
 * <p>
 * This class last modified by User
 */
public abstract class Sensor implements EmbeddedScript {
    private Boolean runOnce=Boolean.FALSE;
    @Override
    public void run() {
        if(runOnce){
            return;
        }
        if(this.isScriptRunOnce()){
            runOnce=Boolean.TRUE;
        }
    }
}
