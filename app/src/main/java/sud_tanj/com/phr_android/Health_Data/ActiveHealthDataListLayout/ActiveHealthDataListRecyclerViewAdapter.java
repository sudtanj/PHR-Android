/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/26/18 4:49 PM
 */

package sud_tanj.com.phr_android.Health_Data.ActiveHealthDataListLayout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.R;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 20/12/2017 - 16:50.
 * <p>
 * This class last modified by User
 */

public class ActiveHealthDataListRecyclerViewAdapter extends RecyclerView
        .Adapter<ActiveHealthDataListHolder> {
    private ArrayList<SensorData> mDataset;
    //private ArrayList<GraphRunnable> handlerRunnable = new ArrayList<GraphRunnable>();
    private ArrayList<String> lastValue = new ArrayList<>();
    private ArrayList<String> valueGui=new ArrayList<>();
    private ArrayList<SensorData> sensorData=new ArrayList<>();
    private ActiveHealthDataListActivity activeHealthDataListActivity;
    private Boolean dataChanged;

    public ActiveHealthDataListRecyclerViewAdapter(ActiveHealthDataListActivity activeHealthDataListActivity) {
        mDataset = Global.getSensorGateway().getSensorObject();
        this.activeHealthDataListActivity = activeHealthDataListActivity;
        this.dataChanged = Boolean.FALSE;
    }

    @Override
    public ActiveHealthDataListHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        return new ActiveHealthDataListHolder(view);
    }


    @Override
    public void onBindViewHolder(ActiveHealthDataListHolder holder, int position) {
        holder.setIsRecyclable(Boolean.FALSE);
        if (Global.getSensorGateway().isReady() && Global.getSensorGateway().getSensorObject().size()>position) {
            if (this.lastValue.size() == 0) {
                for (SensorData tempDataset : Global.getSensorGateway().getSensorObject()) {
                    lastValue.add("0");
                }
            }
            holder.getTitle().setText(this.sensorData.get(position).getSensorInformation().getSensorName());
            HealthData latestHealthData = this.sensorData.get(position).getLatestData();
            if (latestHealthData != null) {
                Double temp = new Double(latestHealthData.getValue());
                if (temp > 0) {
                    ArrayList<String> resultValue = latestHealthData.getValues();
                    ArrayList<String> resultUnit= latestHealthData.getParentSensor().getSensorInformation().getGraphLegend();
                    String putToGui=new String();
                    for(int i=0;i<resultValue.size();i++) {
                        putToGui += resultValue.get(i) + " ";
                        try {
                            putToGui += resultUnit.get(i);
                        } catch (Exception e){

                        }
                        putToGui+="\n";
                    }
                    holder.getValue().setText(putToGui);
                    if(lastValue.size()>position) {
                        if (!lastValue.get(position).equals(putToGui)) {
                            //lastValue.set(position, putToGui);
                            this.dataChanged = Boolean.TRUE;
                        }
                    }
                }
            }
            //Handler tempHandler = new Handler();
            //sensorHandler.add(tempHandler);
            //GraphRunnable sensorRun = new GraphRunnable(tempHandler, delay, mDataset.get(position), holder);
            //handlerRunnable.add(sensorRun);
            //tempHandler.postDelayed(sensorRun, delay);
        }
    }

    public Boolean isDataChanged() {
        Boolean firstTimeRun=Boolean.FALSE;
        if (Global.getSensorGateway().getSensorObject().size() != this.mDataset.size()) {
            this.mDataset = Global.getSensorGateway().getSensorObject();
        }
        if(!Global.getSensorGateway().isReady()){
            return Boolean.FALSE;
        }
        for (int i = 0; i < this.mDataset.size(); i++) {
            try {
                HealthData latestHealthData = this.mDataset.get(i).getLatestData();
                if (latestHealthData != null) {
                    ArrayList<String> mDatasetValue = this.mDataset.get(i).getLatestData().getValues();
                    ArrayList<String> resultUnit= latestHealthData.getParentSensor().getSensorInformation().getGraphLegend();
                    String lastValue = this.lastValue.get(i);
                    String putToGui=new String();
                    for(int j=0;j<mDatasetValue.size();j++) {
                        putToGui += mDatasetValue.get(j) + " ";
                        try {
                            putToGui += resultUnit.get(j);
                        } catch (Exception e){

                        }
                        putToGui+="\n";
                    }
                    if (!putToGui.equals(lastValue)) {
                        if(!this.lastValue.equals("-1")) {
                            if(!this.sensorData.contains(this.mDataset.get(i))){
                                if(!firstTimeRun) {
                                    this.valueGui.clear();
                                    this.sensorData.clear();
                                    firstTimeRun=Boolean.TRUE;
                                }
                                this.valueGui.add(putToGui);
                                this.sensorData.add(this.mDataset.get(i));
                            }
                        }
                        this.lastValue.set(i, putToGui);
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                this.lastValue.add(new String("0"));
            }
        }
        if(firstTimeRun){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public int getItemCount() {
        if (!Global.getSensorGateway().isReady())
            return new Integer(0);
        return this.sensorData.size();
    }

}