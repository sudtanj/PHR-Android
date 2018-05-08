/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/8/18 4:07 PM
 */

package sud_tanj.com.phr_android.FragmentHandler.Interface;

import android.app.FragmentTransaction;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Health_Data.CardLayout.HealthDataListActivity;
import sud_tanj.com.phr_android.Health_Data.CardLayout.HealthDataListRecyclerViewAdapter;
import sud_tanj.com.phr_android.R;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 08/05/2018 - 16:07.
 * <p>
 * This class last modified by User
 */
public class HealthDataList implements Runnable {
    private HealthDataListActivity healthDataListActivity;

    public HealthDataList(HealthDataListActivity healthDataListActivity) {
        this.healthDataListActivity=healthDataListActivity;
    }

    @Override
    public void run() {
        if(Global.getSensorGateway().isReady()) {
            this.healthDataListActivity.setDataReady(Boolean.TRUE);
            if(this.healthDataListActivity.getmAdapter()==null) {
                HealthDataListRecyclerViewAdapter healthDataListRecyclerViewAdapter = new HealthDataListRecyclerViewAdapter(Global.getSensorGateway().getSensorObject(), this.healthDataListActivity);
                this.healthDataListActivity.setmAdapter(healthDataListRecyclerViewAdapter);
                this.healthDataListActivity.getmRecyclerView().setAdapter(healthDataListRecyclerViewAdapter);
            } else {
                if(this.healthDataListActivity.getmAdapter().isDataChanged())
                    this.healthDataListActivity.getmAdapter().notifyDataSetChanged();
            }
        }
    }
}
