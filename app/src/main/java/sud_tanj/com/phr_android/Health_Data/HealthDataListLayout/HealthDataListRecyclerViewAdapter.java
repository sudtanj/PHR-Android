/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/6/18 5:37 PM
 */

package sud_tanj.com.phr_android.Health_Data.HealthDataListLayout;

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

public class HealthDataListRecyclerViewAdapter extends RecyclerView
        .Adapter<HealthDataListHolder> {
    private ArrayList<SensorData> mDataset;
    //private ArrayList<GraphRunnable> handlerRunnable = new ArrayList<GraphRunnable>();
    private ArrayList<String> lastValue = new ArrayList<>();
    private HealthDataListActivity healthDataListActivity;
    private Boolean dataChanged;

    public HealthDataListRecyclerViewAdapter(HealthDataListActivity healthDataListActivity) {
        mDataset = Global.getSensorGateway().getSensorObject();
        this.healthDataListActivity = healthDataListActivity;
        this.dataChanged = Boolean.FALSE;
    }

    @Override
    public HealthDataListHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        return new HealthDataListHolder(view);
    }


    @Override
    public void onBindViewHolder(HealthDataListHolder holder, int position) {
        holder.setIsRecyclable(Boolean.FALSE);
        if (Global.getSensorGateway().isReady() && Global.getSensorGateway().getSensorObject().size()>position) {
            if (this.lastValue.size() == 0) {
                for (SensorData tempDataset : Global.getSensorGateway().getSensorObject()) {
                    lastValue.add("0");
                }
            }
            holder.updateOnClick();
            holder.getTitle().setText(Global.getSensorGateway().getSensorObject().get(position).getSensorInformation().getSensorName());
            HealthData latestHealthData = Global.getSensorGateway().getSensorObject().get(position).getLatestData();
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
                            lastValue.set(position, putToGui);
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
        if (Global.getSensorGateway().getSensorObject().size() != this.mDataset.size()) {
            this.mDataset = Global.getSensorGateway().getSensorObject();
        }
        for (int i = 0; i < this.getItemCount(); i++) {
            try {
                HealthData latestHealthData = this.mDataset.get(i).getLatestData();
                if (latestHealthData != null) {
                    String mDatasetValue = this.mDataset.get(i).getLatestData().getValue(),
                            lastValue = this.lastValue.get(i);
                    if (!mDatasetValue.equals(lastValue)) {
                        this.lastValue.set(i, mDatasetValue);
                        return Boolean.TRUE;
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                this.lastValue.add(new String("0"));
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public int getItemCount() {
        if (!Global.getSensorGateway().isReady())
            return new Integer(0);
        return Global.getSensorGateway().getSensorObject().size();
    }

}