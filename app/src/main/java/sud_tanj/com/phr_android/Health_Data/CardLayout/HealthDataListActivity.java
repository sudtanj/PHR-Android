/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/6/18 5:37 PM
 */

package sud_tanj.com.phr_android.Health_Data.CardLayout;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.ArrayList;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.FragmentHandler.Interface.HealthDataList;
import sud_tanj.com.phr_android.HandlerLoop;
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
    private HealthDataList healthDataList;
    private HealthDataListRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private HandlerLoop handlerLoop;
    private Boolean dataReady;
    public HealthDataListActivity() {
        this.healthDataList=new HealthDataList(this);
        this.handlerLoop=new HandlerLoop(5,this.healthDataList);
        this.dataReady=Boolean.FALSE;
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    public HealthDataListRecyclerViewAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(HealthDataListRecyclerViewAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public Boolean isDataReady() {
        return dataReady;
    }

    public void setDataReady(Boolean dataReady) {
        this.dataReady = dataReady;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_card_view, container, false);
    }

    @Override
    public void onStop() {
        super.onStop();
        ((HealthDataListRecyclerViewAdapter)mAdapter).stopHandler();
        this.handlerLoop.removeCallbacks(this.healthDataList);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        int resId = R.anim.layout_animation_slide_right;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity().getApplicationContext(), resId);
        mRecyclerView.setLayoutAnimation(animation);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


        // Code to Add an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);

        //((MyRecyclerViewAdapter) mAdapter).addItem(new DataObject("aaa","bbb"), 1);

        // Code to remove an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);
    }


    @Override
    public void onResume() {
        super.onResume();
        LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(mRecyclerView.getContext(), R.anim.layout_animation_slide_right);

        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.scheduleLayoutAnimation();
    }

    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
        for (int index = 0; index < 20; index++) {
            DataObject obj = new DataObject("Some Primary Text " + index,
                    "Secondary " + index);
            results.add(index, obj);
        }
        return results;
    }
}