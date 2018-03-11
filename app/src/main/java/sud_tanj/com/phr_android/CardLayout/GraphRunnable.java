/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 3/11/18 6:46 PM
 */

package sud_tanj.com.phr_android.CardLayout;

import android.os.Handler;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.HealthData;
import sud_tanj.com.phr_android.Database.SensorData;

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
    private int delay=-1;
    private SensorData mDataset;
    private MyRecyclerViewAdapter.DataObjectHolder holder;
    private Handler sensorHandler;
    public GraphRunnable(Handler sensorHandler, int delay, SensorData mDataset, MyRecyclerViewAdapter.DataObjectHolder holder){
        this.mDataset=mDataset;
        this.holder=holder;
        this.delay=delay;
        this.sensorHandler=sensorHandler;
    }
    @Override
    public void run() {
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
    }
}
