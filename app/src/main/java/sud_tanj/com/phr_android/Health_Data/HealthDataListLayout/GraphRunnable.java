/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/6/18 5:37 PM
 */

package sud_tanj.com.phr_android.Health_Data.HealthDataListLayout;

import android.os.Handler;

import sud_tanj.com.phr_android.Database.Sensor.SensorData;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 11/03/2018 - 18:46.
 * <p>
 * This class last modified by User
 */

public class GraphRunnable implements Runnable {
    private int delay = -1;
    private SensorData mDataset;
    //private HealthDataListRecyclerViewAdapter.DataObjectHolder holder;
    private Handler sensorHandler;

    public GraphRunnable(Handler sensorHandler, int delay, SensorData mDataset) {
        this.mDataset = mDataset;
        // this.holder=holder;
        this.delay = delay;
        this.sensorHandler = sensorHandler;
    }

    @Override
    public void run() {
        /**
         if(mDataset.isReady()) {
         ArrayList<HealthData> healthData = mDataset.getSensorData();
         DataPoint[] data = new DataPoint[healthData.size()];
         for (int i = 0; i < healthData.size(); i++)
         data[i] = new DataPoint(i, Double.parseDouble(healthData.get(i).getValues()));
         LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);

         if(!holder.graph.getSeries().equals(series))
         holder.graph.addSeries(series);

         }
         sensorHandler.postDelayed(this,delay);
         */
    }
}
