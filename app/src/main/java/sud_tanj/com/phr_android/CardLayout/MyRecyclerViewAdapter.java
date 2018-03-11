/*
 * Create by Sudono Tanjung
 * Copyright (c) 2017. All rights reserved.
 *
 * Last Modified by User on 12/20/17 4:50 PM
 */

package sud_tanj.com.phr_android.CardLayout;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.HealthData;
import sud_tanj.com.phr_android.Database.SensorData;
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

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static MyClickListener myClickListener;
    private ArrayList<SensorData> mDataset;
    private ArrayList<Handler> sensorHandler = new ArrayList<Handler>();
    private ArrayList<GraphRunnable> handlerRunnable = new ArrayList<GraphRunnable>();
    private int delay = 12 * 1000;
    private Runnable runnable;
    private int innerPosition;

    public MyRecyclerViewAdapter(ArrayList<SensorData> myDataset) {
        mDataset = myDataset;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public void stopHandler(){
        for(int i=0;i<sensorHandler.size();i++){
            sensorHandler.get(i).removeCallbacks(handlerRunnable.get(i));
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }


    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label.setText(mDataset.get(position).getSensorName());
        Handler tempHandler = new Handler();
        sensorHandler.add(tempHandler);
        GraphRunnable sensorRun = new GraphRunnable(tempHandler, delay, mDataset.get(position), holder);
        handlerRunnable.add(sensorRun);
        tempHandler.postDelayed(sensorRun, delay);
    }

    public void addItem(SensorData dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
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
        TextView label;
        TextView dateTime;
        GraphView graph;

        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.age);
            dateTime = (TextView) itemView.findViewById(R.id.textView2);
            graph = (GraphView) itemView.findViewById(R.id.graph);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}