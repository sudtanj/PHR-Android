/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/6/18 5:37 PM
 */

package sud_tanj.com.phr_android.Health_Data.HealthDataListLayout;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        this.healthDataListActivity=healthDataListActivity;
        this.dataChanged=Boolean.FALSE;
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
        if(Global.getSensorGateway().isReady()) {
            holder.updateOnClick();
            if(this.lastValue.size()==0){
                for(SensorData tempDataset:Global.getSensorGateway().getSensorObject()){
                    lastValue.add("0");
                }
            }
            holder.getTitle().setText(mDataset.get(position).getSensorInformation().getSensorName());
            ArrayList<HealthData> healthData = mDataset.get(position).getSensorData();
            if (healthData.size() > 0) {
                for (int i = healthData.size() - 1; i > -1; i--) {
                    Double temp = new Double(healthData.get(i).getValues());
                    if (temp > 0) {
                        String result = String.valueOf(temp);
                        holder.getValue().setText(result);
                        if (!lastValue.get(position).equals(result)) {
                            lastValue.set(position, result);
                            this.dataChanged = Boolean.TRUE;
                        }
                        break;
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

    public Boolean isDataChanged(){
        if(Global.getSensorGateway().getSensorObject().size()!=this.mDataset.size()){
            this.mDataset=Global.getSensorGateway().getSensorObject();
        }
        for(int i=0;i<this.getItemCount();i++){
            ArrayList<HealthData> mDatasetHealthData=this.mDataset.get(i).getSensorData();
            try {
                String mDatasetValue=mDatasetHealthData.get(mDatasetHealthData.size()-1).getValues(),
                        lastValue=this.lastValue.get(i);
                if(!mDatasetValue.equals(lastValue)){
                    this.lastValue.set(i,mDatasetValue);
                    return Boolean.TRUE;
                }
            } catch ( IndexOutOfBoundsException e ) {
                this.lastValue.add(new String("0"));
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public int getItemCount() {
        if(!Global.getSensorGateway().isReady())
            return new Integer(0);
        return Global.getSensorGateway().getSensorObject().size();
    }

}