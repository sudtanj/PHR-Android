/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 1/6/18 10:47 AM
 */

package sud_tanj.com.phr_android.Database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<HealthData> sensorData = null;
    private DatabaseReference dataReference = null;

    public SensorData(String sensorId) {
        this.setSensorId(sensorId);
        this.setDataReference(Global.getUserDatabase().child(SENSOR_DATA_CHILD_NAME).child(this.getSensorId()));
        ValueEventListener dataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot idReference=dataSnapshot.child("Id"),
                        nameReference=dataSnapshot.child("Name"),
                        dataReference=dataSnapshot.child("Data");
                if(dataSnapshot.exists()) {
                    setSensorIdEncrypted(idReference.getValue(String.class));
                    setSensorNameEncrypted(nameReference.getValue(String.class));
                    setSensorData(dataReference.getValue(ArrayList.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        this.getDataReference().addValueEventListener(dataListener);
    }

    //get and set
    public String getSensorName(){
        return sensorName.getDecryptedText();
    }

    protected void setSensorName(String sensorName) {
        this.sensorName = new EncryptedString(sensorName,false);
    }

    protected void setSensorNameEncrypted(String sensorNameEncrypted){
        this.sensorName = new EncryptedString(sensorNameEncrypted,true);
    }

    public String getSensorId() {
        return sensorId.getDecryptedText();
    }

    protected void setSensorId(String sensorId) {
        this.sensorId = new EncryptedString(sensorId,false);
    }


    protected void setSensorIdEncrypted(String sensorId) {
        this.sensorId = new EncryptedString(sensorId,true);
    }

    private DatabaseReference getDataReference() {
        return dataReference;
    }

    private void setDataReference(DatabaseReference dataReference) {
        this.dataReference = dataReference;
    }

    public ArrayList<HealthData> getSensorData() {
        return (ArrayList<HealthData>)sensorData.clone();
    }

    public void addHealthData(HealthData values){
        this.getSensorData().add(values);
    }

    private void setSensorData(ArrayList<HealthData> sensorData) {
        this.sensorData = sensorData;
    }
}
