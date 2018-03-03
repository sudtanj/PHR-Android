/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 1/5/18 4:30 PM
 */

package sud_tanj.com.phr_android.Database;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import sud_tanj.com.phr_android.Custom.EncryptedString;
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

public class HealthData implements ValueEventListener {
    public static final String HEALTH_DATA_CHILD_NAME = "Health_Data";
    private String healthDataId = null;
    private String values = null;
    private Date timeStamp = null;
    private SensorData parentSensor = null;
    private DatabaseReference dataReference = null;
    private ValueEventListener dataListener = null;
    private String parentSensorString = null;
    private String timeStampString = null;

    public HealthData(SensorData parentSensorargs, String valuesargs) {
        timeStamp = new Date();
        setHealthDataId(parentSensorargs.getSensorId() + String.valueOf(timeStamp.getTime()));
        setDataReference(Global.getUserDatabase().child(HEALTH_DATA_CHILD_NAME).child(getHealthDataId()));
        setParentSensor(parentSensorargs);
        setTimeStamp(timeStamp);
        setValues(valuesargs);
        parentSensorargs.addHealthData(this);
        getDataReference().addValueEventListener(this);
    }

    public HealthData(String healthDataId) {
        setHealthDataId(healthDataId);
        setDataReference(Global.getUserDatabase().child(HEALTH_DATA_CHILD_NAME).child(getHealthDataId()));
        getDataReference().addValueEventListener(this);
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        getDataReference().child("TimeStamp").setValue(String.valueOf(timeStamp.getTime()));
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        //getDataReference().child("Values").setValue(new EncryptedString(values,false).getEncryptedText());
        getDataReference().child("Values").setValue(values);
    }

    public SensorData getParentSensor() {
        return parentSensor;
    }

    public void setParentSensor(SensorData parentSensor) {
        //getDataReference().child("ParentSensor").setValue(new EncryptedString(parentSensor.getSensorId(),false).getEncryptedText());
        getDataReference().child("ParentSensor").setValue(parentSensor.getSensorId());
    }

    public DatabaseReference getDataReference() {
        return dataReference;
    }

    public void setDataReference(DatabaseReference dataReference) {
        this.dataReference = dataReference;
    }

    public String getHealthDataId() {
        return healthDataId;
    }

    public void setHealthDataId(String healthDataId) {
        this.healthDataId = healthDataId;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String temp = dataSnapshot.child("TimeStamp").getValue(String.class);
        if (temp != null) {
            timeStampString = temp;
        }
        timeStamp = new Date(Long.parseLong(timeStampString));
        temp = dataSnapshot.child("Values").getValue().toString();
        //System.out.println(temp);
        if (temp != null) {
            //values = new EncryptedString(temp,true).getDecryptedText();
            values=temp;
        }
        temp = dataSnapshot.child("ParentSensor").getValue(String.class);
        if (temp != null) {
            //parentSensorString = new EncryptedString(temp,true).getDecryptedText();
            parentSensorString = temp;
        }
        parentSensor = Global.getSensorGateway().getSensorData(parentSensorString);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public Boolean isReady()
    {
        if(this.getValues()==null){
            return false;
        }
        if(this.getTimeStamp()==null){
            return false;
        }
        if(this.getParentSensor()==null){
            return false;
        }
        return true;
    }
}
