/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 6/4/18 3:16 PM
 */

package sud_tanj.com.phr_android.Handler;

import android.os.AsyncTask;

import sud_tanj.com.phr_android.Health_Data.Interface.HealthDataListGraphListener;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 04/06/2018 - 15:16.
 * <p>
 * This class last modified by User
 */
public class LongOneTimeOperation extends AsyncTask<Void,Void,Void> {
    public void setHealthDataListGraphListener(HealthDataListGraphListener healthDataListGraphListener) {
        this.healthDataListGraphListener = healthDataListGraphListener;
    }

    private HealthDataListGraphListener healthDataListGraphListener;
    public LongOneTimeOperation(HealthDataListGraphListener healthDataListGraphListener){
        this.healthDataListGraphListener=healthDataListGraphListener;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        this.healthDataListGraphListener.run();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        this.healthDataListGraphListener.postExecution();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        this.healthDataListGraphListener.setCancel(Boolean.TRUE);
    }
}
