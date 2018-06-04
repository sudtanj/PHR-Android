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
    private ArrayList<LineGraphSeries<DataPoint>> seriesTemp;
    private String analysisTextView;
    private Boolean cancel=Boolean.FALSE;

    public HealthDataListGraphListener(HealthDataList healthDataList, ArrayList<HealthData> healthData, ArrayList<String> hourData, SensorData sensorData, ArrayList<HealthDataAnalysis> healthDataAnalyses) {
        this.healthDataList = healthDataList;
        this.graphView = healthDataList.getGraph();
        this.healthData = healthData;
        this.handlerExpired = Boolean.FALSE;
        this.hourData = hourData;
        this.sensorData = sensorData;
        this.healthDataAnalyses = healthDataAnalyses;
        for(int i=1;i<this.healthData.size();i++){
            if(this.healthData.get(i).getValues().equals(this.healthData.get(i-1).getValues())){
                if(this.hourData.get(i).equals(this.hourData.get(i-1))){
                    this.healthData.remove(i);
                    this.hourData.remove(i);
                    i--;
                }
            }
        }
    }

    public void postExecution() {
        try {
            this.graphView.removeAllSeries();
            this.graphView.getLegendRenderer().setVisible(true);
            this.graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
            for(LineGraphSeries<DataPoint> series:seriesTemp)
                this.graphView.addSeries(series);
        } catch (Exception e) {

        }
        this.healthDataList.getAnalysis().setText(analysisTextView);
        this.onStop();
    }

    @Override
    public void run() {
        analysisTextView = new String();
        for (int i = 0; i < this.healthDataAnalyses.size(); ) {
            if (!this.healthDataAnalyses.get(i).getAnalysis().isEmpty()) {
                analysisTextView += "- " + this.healthDataAnalyses.get(i).getAnalysis() + ". \n";
                i++;
            }
        }
        ArrayList<LineGraphSeries<DataPoint>> healthDataSeries = new ArrayList<LineGraphSeries<DataPoint>>();
        ArrayList<ArrayList<DataPoint>> healthDataPoint = new ArrayList<ArrayList<DataPoint>>();
        for (int i = 0; i < healthData.size(); i++) {
            if(cancel){
                return;
            }
            if(healthData.get(i).isValid()) {
                ArrayList<String> healthDataList = healthData.get(i).getValues();
                if (healthDataList.size() > 0) {

                    for (int j = 0; j < healthDataList.size(); j++) {
                        try {
                            healthDataSeries.get(healthDataList.size() - 1);
                        } catch (Exception e) {
                            LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<DataPoint>();
                            lineGraphSeries.setDrawDataPoints(true);
                            Random rnd = new Random();
                            lineGraphSeries.setColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
                            healthDataSeries.add(lineGraphSeries);
                        }
                        try {
                            healthDataPoint.get(j);
                        } catch (Exception e) {
                            healthDataPoint.add(new ArrayList<DataPoint>());
                        }
                        try {
                            String value = healthDataList.get(j);
                            String valueResult = new String();
                            for (int z = 0; z < value.length(); z++) {
                                if (!this.isNumeric(String.valueOf(value.charAt(z)))) {
                                    break;
                                }
                                valueResult += value.charAt(z);
                            }
                            DataPoint newData = new DataPoint(Integer.parseInt(this.hourData.get(i)), Integer.parseInt(valueResult));
                            healthDataPoint.get(j).add(newData);
                            //System.out.println(j);
                            //System.out.println(healthDataPoint.get(j));
                        } catch (Exception e) {

                        }
                        //ArrayList<DataPoint> dataPointsHealth = new ArrayList<>();
                        //dataPointsHealth.add(new DataPoint(Integer.parseInt(this.hourData.get(i)), Double.parseDouble(healthDataList.get(j))));
                    }
                }
            } else {
                i-=1;
            }

            // LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(dataPointsHealth.toArray(new DataPoint[dataPointsHealth.size()]));
            //this.graphView.addSeries(series);
        }
        seriesTemp=new ArrayList<>();
        for (int i = 0; i < healthDataSeries.size(); i++) {
            if(cancel){
                return;
            }
            ArrayList<DataPoint> healthTemp = healthDataPoint.get(i);
            seriesTemp.add(healthDataSeries.get(i));
            seriesTemp.get(i).resetData(healthTemp.toArray(new DataPoint[healthTemp.size()]));
            try {
                seriesTemp.get(i).setTitle(this.sensorData.getSensorInformation().getGraphLegend().get(i));
            } catch (Exception e) {

            }
        }

        //}
    }

    public Boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public void onStop() {
        if(this.healthData!=null) {
            this.healthData.clear();
            this.healthData = null;
        }
        if(this.hourData!=null) {
            this.hourData.clear();
            this.hourData = null;
        }
    }

    @Override
    public Boolean isHandlerExpired() {
        return this.handlerExpired;
    }

    public void setCancel(Boolean cancel) {
        this.cancel = cancel;
    }
}
