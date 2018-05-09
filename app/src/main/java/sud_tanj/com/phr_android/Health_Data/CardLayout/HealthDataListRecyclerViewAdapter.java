/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/6/18 5:37 PM
 */

package sud_tanj.com.phr_android.Health_Data.CardLayout;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

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
        .Adapter<HealthDataListRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static MyClickListener myClickListener;
    private ArrayList<SensorData> mDataset;
    private ArrayList<Handler> sensorHandler = new ArrayList<Handler>();
    private ArrayList<GraphRunnable> handlerRunnable = new ArrayList<GraphRunnable>();
    private ArrayList<String> lastValue = new ArrayList<>();
    private int delay = 12 * 1000;
    private Runnable runnable;
    private int innerPosition;
    private HealthDataListActivity healthDataListActivity;
    private Boolean dataChanged;

    public HealthDataListRecyclerViewAdapter(ArrayList<SensorData> myDataset, HealthDataListActivity healthDataListActivity) {
        mDataset = myDataset;
        this.healthDataListActivity=healthDataListActivity;
        for(SensorData tempDataset:myDataset){
            lastValue.add("0");
        }
        this.dataChanged=Boolean.FALSE;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public void stopHandler(){
        if(sensorHandler.size()>0) {
            for (int i = 1; i <= sensorHandler.size(); i++) {
                //sensorHandler.get(i).removeCallbacks(handlerRunnable.get(i));
            }
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        return new DataObjectHolder(view);
    }


    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.title.setText(mDataset.get(position).getSensorInformation().getSensorName());
        ArrayList<HealthData> healthData=mDataset.get(position).getSensorData();
        if(healthData.size()>0) {
            for(int i=healthData.size()-1;i>-1;i--) {
                Double temp=new Double(healthData.get(i).getValues());
                if (temp>0) {
                    String result=String.valueOf(temp);
                    holder.value.setText(result);
                    if(!lastValue.get(position).equals(result)) {
                        lastValue.set(position, result);
                        this.dataChanged=Boolean.TRUE;
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

    public void addItem(SensorData dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    public Boolean isDataChanged(){
        if(this.dataChanged.equals(Boolean.TRUE)){
            this.dataChanged=Boolean.FALSE;
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public int getItemCount() {
        if (mDataset == null) {
            return 0;
        }
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView title,value;
        /**
         TextView dateTime;
         GraphView graph;
         */
        public DataObjectHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.health_sensor_title);
            value = (TextView) itemView.findViewById(R.id.health_sensor_value);
            /**
             dateTime = (TextView) itemView.findViewById(R.id.textView2);
             graph = (GraphView) itemView.findViewById(R.id.graph);
             */
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}