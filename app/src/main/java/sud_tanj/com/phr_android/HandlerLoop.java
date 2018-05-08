/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/8/18 4:04 PM
 */

package sud_tanj.com.phr_android;

import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Handler;

import java.util.concurrent.TimeUnit;

import sud_tanj.com.phr_android.SensorHandler.Interface.SensorRunnable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 08/05/2018 - 15:24.
 * <p>
 * This class last modified by User
 */
public class HandlerLoop extends Handler implements Runnable {
    private Runnable runnable=null;
    private Long secondsDelay=null;
    public HandlerLoop(Integer secondsDelay,Runnable runnable) {
        super();
        this.runnable=runnable;
        this.secondsDelay= TimeUnit.SECONDS.toMillis(secondsDelay);
        this.postDelayed(this,this.secondsDelay);
    }

    @Override
    public void run() {
        this.runnable.run();
        this.postDelayed(this,this.secondsDelay);
    }
}
