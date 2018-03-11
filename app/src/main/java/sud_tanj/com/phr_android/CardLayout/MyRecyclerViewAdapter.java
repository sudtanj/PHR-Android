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
    private ArrayList<SensorData> mDataset;
    private static MyClickListener myClickListener;
    private ArrayList<Handler> sensorHandler=new ArrayList<Handler>();
    private int delay = 12*1000;
    private Runnable runnable;
    private DataObjectHolder innerHolder;
    private int innerPosition;

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

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(ArrayList<SensorData> myDataset) {
        mDataset = myDataset;
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
        System.out.println("Position "+position);
        System.out.println("array size "+sensorHandler.size());
        if(sensorHandler.size()<(position+1)){
            Handler tempHandler = new Handler();
            sensorHandler.add(tempHandler);
            GraphRunnable sensorRun=new GraphRunnable(tempHandler,delay,mDataset.get(position),holder);
            tempHandler.postDelayed(sensorRun,delay);
        }
        /**
        try {
            sensorHandler.get(position);
        } catch (IndexOutOfBoundsException e){
            Handler tempHandler = new Handler();
            sensorHandler.add(tempHandler);
            GraphRunnable sensorRun=new GraphRunnable(tempHandler,delay,mDataset.get(position),holder);
            tempHandler.postDelayed(sensorRun,delay);
            sensorHandler.get(position).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(mDataset.get(position).isReady()) {
                        ArrayList<HealthData> healthData = mDataset.get(position).getSensorData();
                        DataPoint[] data = new DataPoint[healthData.size()];
                        for (int i = 0; i < healthData.size(); i++) {
                            data[i] = new DataPoint(i, Double.parseDouble(healthData.get(i).getValues()));
                        }
                        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);
                        if(!innerHolder.graph.getSeries().equals(series))
                            innerHolder.graph.addSeries(series);
                    }
                    runnable=this;
                    sensorHandler.get(position).postDelayed(runnable,delay);
                }
            },delay);
        }*/

        //holder.dateTime.setText(mDataset.get(position).getmText2());
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
        if(mDataset==null){
            return 0;
        }
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}