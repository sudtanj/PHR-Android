/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 1/6/18 10:47 AM
 */

package sud_tanj.com.phr_android.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import sud_tanj.com.phr_android.Custom.EncryptedString;
import sud_tanj.com.phr_android.Custom.Global;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 06/01/2018 - 10:47.
 * <p>
 * This class last modified by User
 */

public class SensorData {
    public static final String SENSOR_DATA_CHILD_NAME="Sensor_Data";
    private EncryptedString sensorId = null;
    private EncryptedString sensorName = null;
    private DatabaseReference dataReference = Global.getUserDatabase().child(SENSOR_DATA_CHILD_NAME);

    public SensorData(String sensorId,String sensorName) {
        this.setSensorId(sensorId);
        this.setSensorName(sensorName);
    }

    public void syncData(){
        Gson gson = new Gson();
        getDataReference().child(this.getSensorId()).setValue(gson.toJson(this.getClass()));
    }

    //get and set
    public String getSensorName(){
        return sensorName.toString();
    }

    public void setSensorName(String sensorName) {
        this.sensorName = new EncryptedString(sensorName);
    }

    public String getSensorId() {
        return sensorId.toString();
    }

    public void setSensorId(String sensorId) {
        this.sensorId = new EncryptedString(sensorId);
    }

    public DatabaseReference getDataReference() {
        return dataReference;
    }

    public void setDataReference(DatabaseReference dataReference) {
        this.dataReference = dataReference;
    }
}
