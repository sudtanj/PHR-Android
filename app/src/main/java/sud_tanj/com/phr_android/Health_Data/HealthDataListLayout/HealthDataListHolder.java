/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/9/18 3:20 PM
 */

package sud_tanj.com.phr_android.Health_Data.HealthDataListLayout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sud_tanj.com.phr_android.Health_Data.HealthDataListLayout.Interface.HealthDataListRecyclerViewListener;
import sud_tanj.com.phr_android.R;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 09/05/2018 - 15:20.
 * <p>
 * This class last modified by User
 */
public class HealthDataListHolder extends RecyclerView.ViewHolder {
    private TextView title,value;
    private HealthDataListRecyclerViewListener healthDataListRecyclerViewListener;

    public HealthDataListHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.health_sensor_title);
        value = (TextView) itemView.findViewById(R.id.health_sensor_value);
        healthDataListRecyclerViewListener=new HealthDataListRecyclerViewListener(getAdapterPosition());

        itemView.setOnClickListener(this.healthDataListRecyclerViewListener);
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getValue() {
        return value;
    }

    public void updateOnClick(){
        if(getAdapterPosition()>-1)
            this.healthDataListRecyclerViewListener.setSensorPosition(getAdapterPosition());
    }
}
