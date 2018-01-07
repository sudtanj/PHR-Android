/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 1/5/18 4:30 PM
 */

package sud_tanj.com.phr_android.Database;


import com.google.firebase.database.DatabaseReference;

import sud_tanj.com.phr_android.Custom.Global;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 05/01/2018 - 16:30.
 * <p>
 * This class last modified by User
 */

public class HealthData  {
    public static final String HEALTH_DATA_CHILD_NAME="Health_Data";
    private String data=null;
    private SensorData parentSensor=null;
    private DatabaseReference dataReference = Global.getUserDatabase().child(HEALTH_DATA_CHILD_NAME);


    public HealthData(){

    }
    public HealthData(String data){
        this.data=data;
    }
}
