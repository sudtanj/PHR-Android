/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 1/7/18 4:38 PM
 */

package sud_tanj.com.phr_android.GridLayout;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.SensorData;
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

        RecyclerView rView = (RecyclerView)getView().findViewById(R.id.my_recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(getActivity().getApplicationContext(), rowListItem);
        rView.setAdapter(rcAdapter);
    }



    private List<ItemObject> getAllItemList(){

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
