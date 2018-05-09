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
import sud_tanj.com.phr_android.Health_Data.HealthDataListLayout.HealthDataListActivity;
import sud_tanj.com.phr_android.Health_Data.HealthDataListLayout.HealthDataListRecyclerViewAdapter;
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
public class HealthDataListListener implements Runnable {
    private HealthDataListActivity healthDataListActivity;
    private ProgressBar progressBar;

    public HealthDataListListener(HealthDataListActivity healthDataListActivity) {
        this.healthDataListActivity=healthDataListActivity;
    }

    @Override
    public void run() {
        if(this.healthDataListActivity.getActivity()!=null) {
            if (progressBar == null) {
                this.progressBar = (ProgressBar) this.healthDataListActivity.getActivity().findViewById(R.id.health_data_list_progress_bar);
                this.progressBar.setVisibility(View.VISIBLE);
            }
            if(this.healthDataListActivity.getmAdapter().isDataChanged()){
                this.healthDataListActivity.getmAdapter().notifyDataSetChanged();
                this.progressBar.setVisibility(View.GONE);
            }
            //System.out.println("Health data list="+this.healthDataListActivity.getmAdapter().getItemCount());
            //System.out.println("Health data list="+this.healthDataListActivity.getmAdapter().isDataChanged());
            //System.out.println("Health data list="+Global.getSensorGateway().isReady());
            /**
            if (Global.getSensorGateway().isReady()) {
                    //this.healthDataListActivity.getmAdapter().setmDataset(Global.getSensorGateway().getSensorObject());
                        this.healthDataListActivity.setDataReady(Boolean.TRUE);
                        this.progressBar.setVisibility(View.GONE);
                        //this.healthDataListActivity.getmAdapter().notifyDataSetChanged();
            } else {
                this.progressBar.setVisibility(View.VISIBLE);
                //this.healthDataListActivity.getmAdapter().notifyDataSetChanged();
            }
             */
        }
    }
}
