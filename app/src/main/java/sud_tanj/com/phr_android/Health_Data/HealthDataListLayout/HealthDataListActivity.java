/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/6/18 5:37 PM
 */

package sud_tanj.com.phr_android.Health_Data.HealthDataListLayout;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import sud_tanj.com.phr_android.FragmentHandler.Interface.HealthDataListListener;
import sud_tanj.com.phr_android.Handler.HandlerLoop;
import sud_tanj.com.phr_android.R;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 20/12/2017 - 16:49.
 * <p>
 * This class last modified by User
 */

public class HealthDataListActivity extends Fragment {

    private static String LOG_TAG = "CardViewActivity";
    private RecyclerView mRecyclerView;
    private HealthDataListListener healthDataList;
    private HealthDataListRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private HandlerLoop handlerLoop;
    private Boolean dataReady;
    private ProgressBar progressBar;

    public HealthDataListActivity() {
        this.healthDataList = new HealthDataListListener(this);
        this.handlerLoop = new HandlerLoop(5, this.healthDataList);
        this.dataReady = Boolean.FALSE;
    }

    public HealthDataListRecyclerViewAdapter getmAdapter() {
        return mAdapter;
    }


    public Boolean isDataReady() {
        return dataReady;
    }

    public void setDataReady(Boolean dataReady) {
        this.dataReady = dataReady;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (this.handlerLoop != null) {
            this.handlerLoop = new HandlerLoop(5, this.healthDataList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_card_view, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (this.healthDataList != null) {
            this.handlerLoop.removeCallbacks(this.healthDataList);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // if (this.getmAdapter() != null)
        //   ((HealthDataListRecyclerViewAdapter)mAdapter).stopHandler();
        if (this.healthDataList != null)
            this.handlerLoop.removeCallbacks(this.healthDataList);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.progressBar = (ProgressBar) getActivity().findViewById(R.id.health_data_list_progress_bar);
        this.progressBar.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        int resId = R.anim.layout_animation_slide_right;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity().getApplicationContext(), resId);
        mRecyclerView.setLayoutAnimation(animation);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HealthDataListRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        this.getActivity().setTitle(R.string.health_data_list_title);


    }


    @Override
    public void onResume() {
        super.onResume();
        LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(mRecyclerView.getContext(), R.anim.layout_animation_slide_right);

        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.scheduleLayoutAnimation();
    }

}