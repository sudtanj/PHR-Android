/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/6/18 5:39 PM
 */

package sud_tanj.com.phr_android.Database.Data;


import com.google.firebase.database.DatabaseReference;

import java.util.Date;
import java.util.HashMap;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Data.DataListener.DataValueListener;
import sud_tanj.com.phr_android.Database.Data.DataListener.ParentSensorListener;
import sud_tanj.com.phr_android.Database.Data.DataListener.TimeStampListener;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.HealthDataSynchronizer;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 05/01/2018 - 16:30.
 * <p>
 * This class last modified by User
 */

public class HealthData {
    public static final String HEALTH_DATA_CHILD_NAME = "Health_Data";
    public static final String HEALTH_DATA_CHILD_TIMESTAMP = "TimeStamp";
    public static final String HEALTH_DATA_CHILD_VALUE = "Values";
    public static final String HEALTH_DATA_CHILD_PARENTSENSOR = "ParentSensor";
    private String healthDataId = null;
    protected String values = null;
    private Date timeStamp = null;
    private SensorData parentSensor = null;
    private DatabaseReference dataReference = null;
    private HealthDataSynchronizer dataReferenceSynchronizer = null;

    public HealthData(String healthDataId, SensorData parentSensor) {
        this.timeStamp = new Date();
        this.values = "-1";
        this.parentSensor = parentSensor;

        if (healthDataId.isEmpty()) {
            String newHealthDataId = parentSensor.getSensorInformation().getSensorId() + String.valueOf(this.timeStamp.getTime());
            this.healthDataId = newHealthDataId;
            parentSensor.addHealthData(newHealthDataId);
        } else {
            this.healthDataId = healthDataId;
        }

        this.setDataReference(Global.getUserDatabase().child(HEALTH_DATA_CHILD_NAME).child(this.getHealthDataId()));

        this.dataReferenceSynchronizer = new HealthDataSynchronizer(this.getDataReference(), this);
        this.dataReferenceSynchronizer.add(new TimeStampListener(), HEALTH_DATA_CHILD_TIMESTAMP);
        this.dataReferenceSynchronizer.add(new DataValueListener(), HEALTH_DATA_CHILD_VALUE);
        this.dataReferenceSynchronizer.add(new ParentSensorListener(), HEALTH_DATA_CHILD_PARENTSENSOR);


    }

    public HealthData(SensorData sensorData) {
        this("", sensorData);
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
        String timeStampText = String.valueOf(this.timeStamp.getTime());
        if(this.isValid())
            this.syncToFirebase();
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        if (!this.getParentSensor().getLatestData().getValues().equals(values)) {
            this.values = values;
            this.getParentSensor().setLatestData(this);
            this.syncToFirebase();
        } else {
            this.delete();
        }
    }

    public SensorData getParentSensor() {
        return parentSensor;
    }

    public void setParentSensor(SensorData parentSensor) {
        this.parentSensor = parentSensor;
        String parentSensorId = this.parentSensor.getSensorInformation().getSensorId();
        if(this.isValid())
            this.syncToFirebase();
    }

    private DatabaseReference getDataReference() {
        return dataReference;
    }

    private void setDataReference(DatabaseReference dataReference) {
        this.dataReference = dataReference;
    }

    public String getHealthDataId() {
        return healthDataId;
    }


    public Boolean isReady() {
        if (this.getValues() == null) {
            return false;
        }
        if (this.getTimeStamp() == null) {
            return false;
        }
        if (this.getParentSensor() == null) {
            return false;
        }
        if (this.isValid()) {
            return false;
        }

        return true;
    }

    public Boolean isValid(){
        if (this.getValues().equals("-1")) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void delete() {
        this.getDataReference().removeValue();
        this.getParentSensor().deleteData(this);
    }

    public void syncToFirebase() {
        HashMap<String, Object> temporaryPayload = new HashMap<>();
        temporaryPayload.put(this.HEALTH_DATA_CHILD_PARENTSENSOR, parentSensor.getSensorInformation().getSensorId());
        temporaryPayload.put(this.HEALTH_DATA_CHILD_VALUE, values);
        temporaryPayload.put(this.HEALTH_DATA_CHILD_TIMESTAMP, String.valueOf(timeStamp.getTime()));
        this.dataReferenceSynchronizer.changeVariable(temporaryPayload);
    }
}
