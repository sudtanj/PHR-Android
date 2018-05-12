/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/10/18 10:20 AM
 */

package sud_tanj.com.phr_android.Health_Data.Interface;

import com.google.firebase.database.Transaction;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Handler.HandlerLoop;
import sud_tanj.com.phr_android.Handler.Interface.HandlerLoopRunnable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 10/05/2018 - 10:20.
 * <p>
 * This class last modified by User
 */
public class HealthDataListGraphListener implements HandlerLoopRunnable {
    private GraphView graphView;
    private ArrayList<HealthData> healthData;
    private Boolean handlerExpired;
    private ArrayList<String> hourData;

    public HealthDataListGraphListener(GraphView graph, ArrayList<HealthData> healthData, ArrayList<String> hourData) {
        this.graphView=graph;
        this.healthData=healthData;
        this.handlerExpired=Boolean.FALSE;
        this.hourData=hourData;
    }

    @Override
    public void run() {
        this.handlerExpired=Boolean.TRUE;
        for(HealthData temp:this.healthData){
            if(!temp.isValid()){
                this.handlerExpired=Boolean.FALSE;
                break;
            }
        }
        if(this.handlerExpired){
            this.graphView.removeAllSeries();
            ArrayList<DataPoint> dataPointsHealth=new ArrayList<>();
            for(int i=0;i<healthData.size();i++) {
                dataPointsHealth.add(new DataPoint(Integer.parseInt(this.hourData.get(i)),Double.parseDouble(healthData.get(i).getValues())));
            }

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointsHealth.toArray(new DataPoint[dataPointsHealth.size()]));
            this.graphView.addSeries(series);
        }
    }

    @Override
    public Boolean isHandlerExpired() {
        return this.handlerExpired;
    }
}
