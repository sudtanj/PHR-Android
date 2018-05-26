/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/26/18 4:54 PM
 */

package sud_tanj.com.phr_android.FragmentHandler.Interface;

import android.view.View;
import android.widget.ProgressBar;

import sud_tanj.com.phr_android.Health_Data.ActiveHealthDataListLayout.ActiveHealthDataListActivity;
import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Handler.Interface.HandlerLoopRunnable;
import sud_tanj.com.phr_android.R;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 26/05/2018 - 16:54.
 * <p>
 * This class last modified by User
 */
public class ActiveHealthDataListListener implements HandlerLoopRunnable {
    private ActiveHealthDataListActivity healthDataListActivity;
    private ProgressBar progressBar;
    private Integer timeToUpdateGui;
    private Boolean firstTime;

    public ActiveHealthDataListListener(ActiveHealthDataListActivity healthDataListActivity) {
        this.healthDataListActivity = healthDataListActivity;
        this.timeToUpdateGui=5;
        firstTime=Boolean.TRUE;
    }

    @Override
    public void run() {
        if (this.healthDataListActivity.getActivity() != null) {
            if (progressBar == null) {
                this.progressBar = (ProgressBar) this.healthDataListActivity.getActivity().findViewById(R.id.health_data_list_progress_bar);
                this.progressBar.setVisibility(View.GONE);
            }
            if (this.healthDataListActivity.getmAdapter().isDataChanged() || Global.getSensorGateway().isReady()) {
                //if(this.firstTime) {
                this.healthDataListActivity.getmAdapter().notifyDataSetChanged();
                //  this.firstTime=Boolean.FALSE;
                //} else {
                //  if(this.timeToUpdateGui==0){
                //    this.healthDataListActivity.getmAdapter().notifyDataSetChanged();
                //   this.timeToUpdateGui=5;
                // }
                //this.timeToUpdateGui=this.timeToUpdateGui-1;
                //System.out.println(this.timeToUpdateGui);
                //  }
                //   this.progressBar.setVisibility(View.GONE);
            }
            /**
             if(timeToUpdateGui<0){
             this.healthDataListActivity.getmAdapter().notifyDataSetChanged();
             this.timeToUpdateGui=100;
             System.out.println(this.timeToUpdateGui);
             }
             this.timeToUpdateGui--;
             */
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

    @Override
    public Boolean isHandlerExpired() {
        return false;
    }
}
