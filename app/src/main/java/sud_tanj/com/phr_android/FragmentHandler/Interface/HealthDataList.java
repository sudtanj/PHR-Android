/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/8/18 4:07 PM
 */

package sud_tanj.com.phr_android.FragmentHandler.Interface;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

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
    private ProgressBar progressBar;

    public HealthDataList(HealthDataListActivity healthDataListActivity) {
        this.healthDataListActivity=healthDataListActivity;
    }

    @Override
    public void run() {
        if(this.healthDataListActivity.getActivity()!=null) {
            if (progressBar == null) {
                this.progressBar = (ProgressBar) this.healthDataListActivity.getActivity().findViewById(R.id.health_data_list_progress_bar);
            }
            if (Global.getSensorGateway().isReady()) {
                this.progressBar.setVisibility(View.GONE);
                this.healthDataListActivity.setDataReady(Boolean.TRUE);
                if (this.healthDataListActivity.getmAdapter() == null) {
                    HealthDataListRecyclerViewAdapter healthDataListRecyclerViewAdapter = new HealthDataListRecyclerViewAdapter(Global.getSensorGateway().getSensorObject(), this.healthDataListActivity);
                    this.healthDataListActivity.setmAdapter(healthDataListRecyclerViewAdapter);
                    this.healthDataListActivity.getmRecyclerView().setAdapter(healthDataListRecyclerViewAdapter);
                } else {
                    if (this.healthDataListActivity.getmAdapter().isDataChanged())
                        this.healthDataListActivity.getmAdapter().notifyDataSetChanged();
                }
                this.healthDataListActivity.getmAdapter().setOnItemClickListener(new HealthDataListRecyclerViewAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Intent i = new Intent(healthDataListActivity.getActivity().getApplicationContext(), sud_tanj.com.phr_android.Health_Data.HealthDataList.class);
                        healthDataListActivity.getActivity().startActivity(i);
                    }
                });
            } else {
                this.progressBar.setVisibility(View.VISIBLE);
            }
        }
    }
}
