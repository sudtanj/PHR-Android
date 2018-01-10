/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 1/7/18 11:05 AM
 */

package sud_tanj.com.phr_android.Database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import sud_tanj.com.phr_android.Custom.EmbeddedScript;
import sud_tanj.com.phr_android.Custom.Global;

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
    private List<String> sensorList = null;
    private DatabaseReference dataReference = null;
    private ArrayList<SensorData> sensorObject=null;

    public SensorGateway(){
        this.setDataReference(Global.getMainDatabase().child(SENSOR_DATA_CHILD_NAME).child(SENSOR_COLLECTION_KEY));
        ValueEventListener dataListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sensorList= new ArrayList<String>();
                setSensorObject(new ArrayList<SensorData>());
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    sensorList.add(childSnapshot.getValue().toString());
                    getSensorObject().add(new SensorData(childSnapshot.getValue().toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        this.getDataReference().addValueEventListener(dataListener);
    }

    public List<String> getSensorList() {
        return sensorList;
    }

    public void setSensorList(List<String> sensorList) {
        this.getDataReference().setValue(sensorList);
    }

    public SensorData createSensorDataObject(String sensorId){
        if(this.getSensorList().indexOf(sensorId)==-1){
            SensorData temp =new SensorData(sensorId);
            addNewSensor(temp);
            return temp;
        }
        return null;
    }

    private void addNewSensor(SensorData sensor){
        this.getSensorList().add(sensor.getSensorId());
        this.setSensorList(this.getSensorList());
    }

    public SensorData getSensorData(String sensorId){
        for(SensorData temp:getSensorObject()){
            if (temp.getSensorId().contains(sensorId)) {
                return temp;
            }
        }
        return null;
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

    public void setSensorObject(ArrayList<SensorData> sensorObject) {
        this.sensorObject = sensorObject;
    }
}
