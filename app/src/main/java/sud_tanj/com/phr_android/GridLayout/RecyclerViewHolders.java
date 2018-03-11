/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 1/7/18 4:37 PM
 */

package sud_tanj.com.phr_android.GridLayout;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.SensorData;
import sud_tanj.com.phr_android.R;
import sud_tanj.com.phr_android.ModifySensors;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 07/01/2018 - 16:37.
 * <p>
 * This class last modified by User
 */

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView countryName;
    public ImageView countryPhoto;
    public Switch sensorSwitch;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        countryName = (TextView)itemView.findViewById(R.id.sensor_name);
        countryPhoto = (ImageView)itemView.findViewById(R.id.sensor_picture);
        sensorSwitch = (Switch) itemView.findViewById(R.id.switch1);
    }

    @Override
    public void onClick(View view) {
        SensorData sensor=Global.getSensorGateway().getSensorDataByName(countryName.getText().toString());
        if(sensor.getSensorOwner().contains(Global.getFireBaseUser().getUid())) {
            Intent modifySensor = new Intent(Global.getContext(), ModifySensors.class);
            modifySensor.putExtra("modifySensor", true);
            modifySensor.putExtra("sensorName", countryName.getText());
            Global.getContext().startActivity(modifySensor);
        }
        else {
            Toast.makeText(view.getContext(), "You can't modify sensor that you don't own!", Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
