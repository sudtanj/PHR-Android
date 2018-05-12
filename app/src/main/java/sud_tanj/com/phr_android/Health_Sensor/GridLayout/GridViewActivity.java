/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/6/18 5:34 PM
 */

package sud_tanj.com.phr_android.Health_Sensor.GridLayout;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.R;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 07/01/2018 - 16:38.
 * <p>
 * This class last modified by User
 */

public class GridViewActivity extends Fragment {
    private GridLayoutManager lLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_card_view, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<SensorData> rowListItem = Global.getSensorGateway().getSensorObject();
        lLayout = new GridLayoutManager(getActivity().getApplicationContext(), 4);

        RecyclerView rView = (RecyclerView) getView().findViewById(R.id.my_recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(getActivity().getApplicationContext(), rowListItem);
        rView.setAdapter(rcAdapter);

        this.getActivity().setTitle(R.string.sensor_data_list_title);

    }

    @Override
    public void onStop() {
        super.onStop();
        Global.getFloatingButton().hide();
    }

    @Override
    public void onResume() {
        super.onResume();
        Global.getFloatingButton().show();
    }

    private List<ItemObject> getAllItemList() {

        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject("United States"));
        /**
         allItems.add(new ItemObject("United States", R.drawable.one));
         allItems.add(new ItemObject("Canada", R.drawable.two));
         allItems.add(new ItemObject("United Kingdom", R.drawable.three));
         allItems.add(new ItemObject("Germany", R.drawable.four));
         allItems.add(new ItemObject("Sweden", R.drawable.five));
         allItems.add(new ItemObject("United Kingdom", R.drawable.six));
         allItems.add(new ItemObject("Germany", R.drawable.seven));
         allItems.add(new ItemObject("Sweden", R.drawable.eight));
         allItems.add(new ItemObject("United States", R.drawable.one));
         allItems.add(new ItemObject("Canada", R.drawable.two));
         allItems.add(new ItemObject("United Kingdom", R.drawable.three));
         allItems.add(new ItemObject("Germany", R.drawable.four));
         allItems.add(new ItemObject("Sweden", R.drawable.five));
         allItems.add(new ItemObject("United Kingdom", R.drawable.six));
         allItems.add(new ItemObject("Germany", R.drawable.seven));
         allItems.add(new ItemObject("Sweden", R.drawable.eight));
         */
        return allItems;
    }
}
