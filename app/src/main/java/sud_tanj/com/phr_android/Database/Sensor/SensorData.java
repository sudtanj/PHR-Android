/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/6/18 5:39 PM
 */

package sud_tanj.com.phr_android.Database.Sensor;

import java.util.ArrayList;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Database.Sensor.SensorListener.HealthDataListener;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.DatabaseSynchronizer;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.SensorSynchronizer;

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
    private SensorInformation sensorInformation = null;
    private ArrayList<HealthData> sensorData = null;
    private SensorEmbeddedScript backgroundJob = null;

    public SensorData(String sensorId) {
        this.sensorInformation = new SensorInformation(sensorId, this);
        this.sensorData = new ArrayList<>();
        this.backgroundJob=new SensorEmbeddedScript(new String(),this);

        //firebase sync
        SensorSynchronizer healthDataManager = new SensorSynchronizer(Global.getUserDatabase(), this);
        healthDataManager.add(new HealthDataListener(), "Health_Data");
    }

    public Boolean isHealthIdExist(String healthId){
        for(HealthData healthData:this.sensorData){
            if(healthData.getHealthDataId().equals(healthId)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public SensorInformation getSensorInformation() {
        return sensorInformation;
    }

    public ArrayList<HealthData> getSensorData() {
        return sensorData;
    }

    public void addHealthData(HealthData values) {
        if(this.isReady())
            this.getSensorData().add(values);
    }

    public Boolean isReady() {
        for (HealthData temp : this.getSensorData()) {
            if (!temp.isReady()) {
                return false;
            }
        }
        return true;
    }

    public void deleteData(HealthData healthData) {
        this.sensorData.remove(healthData);
    }

    public void resetHealthData() {
        this.sensorData = new ArrayList<>();
    }

    public SensorEmbeddedScript getBackgroundJob() {
        return backgroundJob;
    }

    public void setBackgroundJob(SensorEmbeddedScript backgroundJob) {
        this.backgroundJob = backgroundJob;
    }
}
