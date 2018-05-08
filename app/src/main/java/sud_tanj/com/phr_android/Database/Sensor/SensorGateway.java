/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/6/18 5:39 PM
 */

package sud_tanj.com.phr_android.Database.Sensor;

import android.hardware.Sensor;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Database.Sensor.SensorGatewayListener.SensorInitializerListener;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.SensorGatewaySynchronizer;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 07/01/2018 - 11:05.
 * <p>
 * This class last modified by User
 */

public class SensorGateway {
    public static final String SENSOR_COLLECTION_KEY="sensor_collection";
    public static final String SENSOR_DATA_CHILD_NAME="sensor";
    private DatabaseReference dataReference = null;
    private ArrayList<SensorData> sensorObject=null;
    private SensorGatewaySynchronizer sensorGatewaySynchronizer=null;

    public SensorGateway(){
        this.setDataReference(Global.getMainDatabase().child(SENSOR_DATA_CHILD_NAME));

        this.sensorObject=new ArrayList<>();

        this.sensorGatewaySynchronizer=new SensorGatewaySynchronizer(this.getDataReference(),this);
        this.sensorGatewaySynchronizer.add(new SensorInitializerListener(),SENSOR_COLLECTION_KEY);
    }

    public SensorData createSensorDataObject(String sensorId){
        if(this.getSensorData(sensorId)==null){
            SensorData temp =new SensorData(sensorId);
            this.sensorObject.add(temp);
            return temp;
        }
        return null;
    }

    public SensorData getSensorData(String sensorId){
        for(SensorData temp:getSensorObject()){
            if (temp.getSensorInformation().getSensorId().contains(sensorId)) {
                return temp;
            }
        }
        return null;
    }

    public SensorData getSensorDataByName(String sensorName){
        for(SensorData temp:getSensorObject()){
            if (temp.getSensorInformation().getSensorName().contains(sensorName)) {
                return temp;
            }
        }
        return null;
    }

    public boolean isSensorIdExist(String sensorId){
        if(this.getSensorData(sensorId)==null)
            return false;
        return true;
    }

    public boolean isSensorNameExist(String sensorName){
        for(SensorData temp:getSensorObject()){
            if (temp.getSensorInformation().getSensorName().contains(sensorName)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<SensorData> getSensorObject(){
        return this.sensorObject;
    }

    public DatabaseReference getDataReference() {
        return dataReference;
    }

    public void setDataReference(DatabaseReference dataReference) {
        this.dataReference = dataReference;
    }

    public Boolean isReady(){
        if(this.getSensorObject()!=null) {
            for (SensorData temp : this.getSensorObject()) {
                if (!temp.isReady()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void resetSensorList(){
        this.sensorObject=new ArrayList<>();
    }

}
