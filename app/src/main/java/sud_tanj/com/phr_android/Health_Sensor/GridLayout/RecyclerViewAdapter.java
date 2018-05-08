/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/6/18 5:34 PM
 */

package sud_tanj.com.phr_android.Health_Sensor.GridLayout;

import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.List;

import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.R;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 07/01/2018 - 16:41.
 * <p>
 * This class last modified by User
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

private List<SensorData> itemList;
private Context context;
private int positionLocal;

public RecyclerViewAdapter(Context context, List<SensorData> itemList) {
        this.itemList = itemList;
        this.context = context;
        }

@Override
public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_sensor_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
        }

@Override
public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        this.positionLocal=position;
        holder.countryName.setText(itemList.get(position).getSensorInformation().getSensorName());
        holder.sensorSwitch.setChecked(itemList.get(position).getSensorInformation().isSensorActive());
        holder.sensorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                int position=positionLocal;
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        itemList.get(position).getSensorInformation().setSensorActive(b);
                }
        });
       // holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
        }

@Override
public int getItemCount() {
        return this.itemList.size();
        }
        }
