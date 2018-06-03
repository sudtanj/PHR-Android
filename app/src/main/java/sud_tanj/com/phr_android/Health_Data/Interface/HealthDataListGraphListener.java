/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/10/18 10:20 AM
 */

package sud_tanj.com.phr_android.Health_Data.Interface;

import android.graphics.Color;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Random;

import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Database.Data.HealthDataAnalysis;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Handler.Interface.HandlerLoopRunnable;
import sud_tanj.com.phr_android.Health_Data.HealthDataList;

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
    private SensorData sensorData;
    private Boolean handlerExpired;
    private ArrayList<String> hourData;
    private ArrayList<HealthDataAnalysis> healthDataAnalyses;
    private HealthDataList healthDataList;

    public HealthDataListGraphListener(HealthDataList healthDataList, ArrayList<HealthData> healthData, ArrayList<String> hourData, SensorData sensorData, ArrayList<HealthDataAnalysis> healthDataAnalyses) {
        this.healthDataList = healthDataList;
        this.graphView = healthDataList.getGraph();
        this.healthData = healthData;
        this.handlerExpired = Boolean.FALSE;
        this.hourData = hourData;
        this.sensorData = sensorData;
        this.healthDataAnalyses = healthDataAnalyses;
    }

    @Override
    public void run() {
        this.handlerExpired = Boolean.TRUE;
        /**
         for(HealthData temp:this.healthData){
         if(!temp.isValid()){
         this.handlerExpired=Boolean.FALSE;
         break;
         }
         }
         if(this.handlerExpired){
         */
        String analysisTextView = new String();
        for (int i = 0; i < this.healthDataAnalyses.size(); ) {
            if (!this.healthDataAnalyses.get(i).getAnalysis().isEmpty()) {
                analysisTextView += "- " + this.healthDataAnalyses.get(i).getAnalysis() + ". \n";
                i++;
            }
        }

        this.healthDataList.getAnalysis().setText(analysisTextView);
        this.graphView.removeAllSeries();
        ArrayList<LineGraphSeries<DataPoint>> healthDataSeries = new ArrayList<LineGraphSeries<DataPoint>>();
        ArrayList<ArrayList<DataPoint>> healthDataPoint = new ArrayList<ArrayList<DataPoint>>();
        for (int i = 0; i < healthData.size(); i++) {
            ArrayList<String> healthDataList = healthData.get(i).getValues();
            try {
                healthDataSeries.get(healthDataList.size() - 1);
            } catch (Exception e) {
                LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<DataPoint>();
                lineGraphSeries.setDrawDataPoints(true);
                Random rnd = new Random();
                lineGraphSeries.setColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
                healthDataSeries.add(lineGraphSeries);
            }

            for (int j = 0; j < healthDataList.size(); j++) {
                try {
                    healthDataPoint.get(j);
                } catch (Exception e) {
                    healthDataPoint.add(new ArrayList<DataPoint>());
                }
                try {
                    DataPoint newData = new DataPoint(Integer.parseInt(this.hourData.get(i)), Double.parseDouble(healthDataList.get(j)));
                    healthDataPoint.get(j).add(newData);
                } catch (Exception e) {

                }
                //ArrayList<DataPoint> dataPointsHealth = new ArrayList<>();
                //dataPointsHealth.add(new DataPoint(Integer.parseInt(this.hourData.get(i)), Double.parseDouble(healthDataList.get(j))));
            }

            // LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(dataPointsHealth.toArray(new DataPoint[dataPointsHealth.size()]));
            //this.graphView.addSeries(series);
        }
        for (int i = 0; i < healthDataSeries.size(); i++) {
            ArrayList<DataPoint> healthTemp = healthDataPoint.get(i);
            LineGraphSeries<DataPoint> seriesTemp = healthDataSeries.get(i);
            seriesTemp.resetData(healthTemp.toArray(new DataPoint[healthTemp.size()]));
            try {
                seriesTemp.setTitle(this.sensorData.getSensorInformation().getGraphLegend().get(i));
                this.graphView.getLegendRenderer().setVisible(true);
                this.graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
            } catch (Exception e) {

            }
            this.graphView.addSeries(seriesTemp);
        }

        //}
    }

    @Override
    public Boolean isHandlerExpired() {
        return this.handlerExpired;
    }
}
