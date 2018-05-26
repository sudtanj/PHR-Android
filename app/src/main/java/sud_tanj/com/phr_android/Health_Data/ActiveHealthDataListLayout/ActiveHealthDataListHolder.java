/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/26/18 4:49 PM
 */

package sud_tanj.com.phr_android.Health_Data.ActiveHealthDataListLayout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sud_tanj.com.phr_android.Health_Data.ActiveHealthDataListLayout.Interface.ActiveHealthDataListRecyclerViewListener;
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
public class ActiveHealthDataListHolder extends RecyclerView.ViewHolder {
    private TextView title, value;
    private ActiveHealthDataListRecyclerViewListener activeHealthDataListRecyclerViewListener;

    public ActiveHealthDataListHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.health_sensor_title);
        value = (TextView) itemView.findViewById(R.id.health_sensor_value);
        activeHealthDataListRecyclerViewListener = new ActiveHealthDataListRecyclerViewListener(getAdapterPosition());

        itemView.setOnClickListener(this.activeHealthDataListRecyclerViewListener);
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getValue() {
        return value;
    }

    public void updateOnClick() {
        if (getAdapterPosition() > -1)
            this.activeHealthDataListRecyclerViewListener.setSensorPosition(getAdapterPosition());
    }
}
