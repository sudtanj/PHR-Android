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

import java.util.ArrayList;

import sud_tanj.com.phr_android.Custom.EmbeddedScript;
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
    public static final String SENSOR_DATA_CHILD_NAME="sensor";
    private String sensorId = null;
    private String sensorName = null;
    private Boolean sensorActive = null;
    private String sensorOwner = null;
    private ArrayList<HealthData> sensorData = null;
    private DatabaseReference dataReference = null;
    private DatabaseReference userDataReference=null;
    private EmbeddedScript scriptListener= null;
    private String scriptListenerName= null;

    protected SensorData(String sensorId) {
        this.setSensorId(sensorId);
        sensorActive=false;
        this.setDataReference(Global.getMainDatabase().child(SENSOR_DATA_CHILD_NAME).child(this.getSensorId()));
        this.setUserDataReference(Global.getUserDatabase().child("sensors").child(this.getSensorId()));
        ValueEventListener healthDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataReference=dataSnapshot.child("Health_Data");
                setSensorData(new ArrayList<HealthData>());
                for (DataSnapshot childSnapshot: dataReference.getChildren()) {
                    if(childSnapshot.getKey().toString().contains(getSensorId()))
                        getSensorData().add(new HealthData(childSnapshot.getKey().toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Global.getUserDatabase().addValueEventListener(healthDataListener);
        ValueEventListener userDataListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot sensorReference=dataSnapshot.child("SensorActive");
                if(sensorReference.getValue()!=null){
                    sensorActive=sensorReference.getValue(Boolean.class);
                }
                else {
                    sensorReference.getRef().setValue(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        this.getUserDataReference().addValueEventListener(userDataListener);
        ValueEventListener dataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot nameReference=dataSnapshot.child("Name"),
                        scriptReference=dataSnapshot.child("EmbeddedScript"),
                        ownerReference=dataSnapshot.child("Owner");
                if(dataSnapshot.exists()) {
                    setSensorNameEncrypted(nameReference.getValue(String.class));
                    setEmbeddedScriptListener(scriptReference.getValue(String.class));
                    scriptListenerName=scriptReference.getValue(String.class);
                    sensorOwner=ownerReference.getValue(String.class);
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
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = new String(sensorName);
        this.getDataReference().child("Name").setValue(this.sensorName.toString());
    }

    protected void setSensorNameEncrypted(String sensorNameEncrypted){
        this.sensorName = new String(sensorNameEncrypted);
    }

    public String getSensorId() {
        return sensorId;
    }

    protected void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }


    protected void setSensorIdEncrypted(String sensorId) {
        this.sensorId = sensorId;
    }

    private DatabaseReference getDataReference() {
        return dataReference;
    }

    private void setDataReference(DatabaseReference dataReference) {
        this.dataReference = dataReference;
    }

    public ArrayList<HealthData> getSensorData() {
        return sensorData;
    }

    public void addHealthData(HealthData values){
        this.getSensorData().add(values);
    }

    private void setEmbeddedScriptListener(String scriptName){
        if(scriptName!=null) {
            try {
                Class listener = Class.forName("sud_tanj.com.phr_android.CustomSensor." + scriptName);
                scriptListener = (EmbeddedScript) (listener.newInstance());
            } catch (ClassNotFoundException e) {

            } catch (IllegalAccessException e) {

            } catch (InstantiationException e) {

            }
        }
    }

    public void runScriptListener(){
        if(getSensorActive()){
            scriptListener.run();
        }
    }



    private void setSensorData(ArrayList<HealthData> sensorData) {
        this.sensorData = sensorData;
    }

    public EmbeddedScript getScriptListener() {
        return scriptListener;
    }

    public String getScriptListenerName() {
        return this.scriptListenerName;
    }

    public void setScriptListener(String scriptListenerId) {
        getDataReference().child("EmbeddedScript").setValue(scriptListenerId);
    }

    public Boolean getSensorActive() {
        return sensorActive;
    }

    public void setSensorActive(Boolean sensorActive) {
        getUserDataReference().child("SensorActive").setValue(sensorActive);
    }

    public DatabaseReference getUserDataReference() {
        return userDataReference;
    }

    public void setUserDataReference(DatabaseReference userDataReference) {
        this.userDataReference = userDataReference;
    }
    public Boolean isReady(){
        for(HealthData temp:this.getSensorData()){
            if(!temp.isReady()){
                return false;
            }
        }
        return true;
    }

    public String getSensorOwner() {
        return sensorOwner;
    }

    public void setSensorOwner(String sensorOwner) {
        this.getDataReference().child("Owner").setValue(sensorOwner);
    }

    public void deleteData(HealthData healthData){
        this.sensorData.remove(healthData);
    }
}
