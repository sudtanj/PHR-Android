/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 1/7/18 4:41 PM
 */

package sud_tanj.com.phr_android.GridLayout;

import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sud_tanj.com.phr_android.R;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 07/01/2018 - 16:41.
 * <p>
 * This class last modified by User
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

private List<ItemObject> itemList;
private Context context;

public RecyclerViewAdapter(Context context, List<ItemObject> itemList) {
        this.itemList = itemList;
        this.context = context;
        }

@Override
public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_sensor_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
        }

@Override
public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.countryName.setText(itemList.get(position).getName());
       // holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
        }

@Override
public int getItemCount() {
        return this.itemList.size();
        }
        }
