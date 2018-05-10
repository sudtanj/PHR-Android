/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/10/18 10:25 AM
 */

package sud_tanj.com.phr_android.Handler;

import android.os.Handler;

import java.util.concurrent.TimeUnit;

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
public class HandlerLoop extends Handler implements Runnable {
    private HandlerLoopRunnable runnable = null;
    private Long secondsDelay = null;

    public HandlerLoop(Integer secondsDelay, HandlerLoopRunnable runnable) {
        super();
        this.runnable = runnable;
        this.secondsDelay = TimeUnit.SECONDS.toMillis(secondsDelay);
        this.postDelayed(this, this.secondsDelay);
    }

    @Override
    public void run() {
        this.runnable.run();
        if(!this.runnable.isHandlerExpired())
            this.postDelayed(this, this.secondsDelay);
    }
}
