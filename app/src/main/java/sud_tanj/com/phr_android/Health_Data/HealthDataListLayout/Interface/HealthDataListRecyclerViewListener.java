/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/9/18 4:29 PM
 */

package sud_tanj.com.phr_android.Health_Data.HealthDataListLayout.Interface;

import android.content.Intent;
import android.view.View;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Health_Data.HealthDataList;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 09/05/2018 - 16:29.
 * <p>
 * This class last modified by User
 */
public class HealthDataListRecyclerViewListener implements View.OnClickListener {
    private Integer sensorPosition;

    public HealthDataListRecyclerViewListener(Integer sensorPosition) {
        this.sensorPosition = sensorPosition;
    }

    public Integer getSensorPosition() {
        return sensorPosition;
    }

    public void setSensorPosition(Integer sensorPosition) {
        this.sensorPosition = sensorPosition;
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(Global.getContext(), HealthDataList.class);
        i.putExtra("sensorposition", sensorPosition.intValue());
        Global.getContext().startActivity(i);
    }
}
