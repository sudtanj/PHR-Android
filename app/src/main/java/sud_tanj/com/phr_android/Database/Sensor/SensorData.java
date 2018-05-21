/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/6/18 5:39 PM
 */

package sud_tanj.com.phr_android.Database.Sensor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Database.Sensor.SensorListener.HealthDataListener;
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
    private ArrayList<String> sensorData = null;
    private SensorEmbeddedScript backgroundJob = null;
    private HealthData latestData = null;

    public SensorData(String sensorId) {
        this.sensorInformation = new SensorInformation(sensorId, this);
        this.sensorData = new ArrayList<>();
        this.backgroundJob = new SensorEmbeddedScript(new String(), this);

        //firebase sync
        SensorSynchronizer healthDataManager = new SensorSynchronizer(Global.getUserDatabase(), this);
        healthDataManager.add(new HealthDataListener(), "Health_Data");
    }

    public Boolean isHealthIdExist(String healthId) {
        return this.sensorData.contains(healthId);
    }

    public SensorInformation getSensorInformation() {
        return sensorInformation;
    }

    public ArrayList<String> getSensorData() {
        return sensorData;
    }

    public HealthData getLatestData() {
        if (this.latestData == null) {
            if (this.getSensorData().size() > 0) {
                String healthDataId = this.sensorData.get(this.sensorData.size() - 1);
                this.latestData = new HealthData(healthDataId, this);
            }
        }
        return this.latestData;
    }

    public void setLatestData(HealthData latestData) {
        this.latestData = latestData;
    }

    public void addHealthData(String values) {
        this.getSensorData().add(values);
    }

    public Boolean isReady() {
        if (this.sensorData != null)
            return Boolean.TRUE;
        return Boolean.FALSE;
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

    public ArrayList<String> getAvailableTimestamp() {
        ArrayList<String> temp = new ArrayList<>();
        for (String healthId : this.sensorData) {
            String tempHealthId = healthId.replace(this.getSensorInformation().getSensorId(), "");
            temp.add(tempHealthId);
        }
        return temp;
    }

    public ArrayList<String> getAvailableTimeOn(Date date) {
        ArrayList<String> availableTimeTemp = new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy", Locale.US),
                hourDateFormat = new SimpleDateFormat("HH", Locale.US);
        simpleDateFormat.format(date);
        String healthIdDate, targetDate;
        Date tempDate;
        for (String healthIdTime : this.getAvailableTimestamp()) {
            tempDate = new Date();
            tempDate.setTime(Long.parseLong(healthIdTime));
            healthIdDate = simpleDateFormat.format(tempDate);
            targetDate = simpleDateFormat.format(date);
            if (targetDate.equals(healthIdDate)) {
                availableTimeTemp.add(hourDateFormat.format(tempDate));
            }
        }
        return availableTimeTemp;
    }

    public ArrayList<HealthData> getHealthDataOn(Date date) {
        ArrayList<HealthData> healthDataTemp = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy", Locale.US);
        simpleDateFormat.format(date);
        String healthIdDate, targetDate;
        for (String healthIdTime : this.getAvailableTimestamp()) {
            Date tempDate = new Date();
            tempDate.setTime(Long.parseLong(healthIdTime));
            healthIdDate = simpleDateFormat.format(tempDate);
            targetDate = simpleDateFormat.format(date);
            if (targetDate.equals(healthIdDate)) {
                healthDataTemp.add(new HealthData(new String(this.getSensorInformation().getSensorId() + healthIdTime), this));
            }
        }
        return healthDataTemp;
    }

    public ArrayList<HealthData> getHealthDataBetween(Date start, Date end) {
        ArrayList<HealthData> healthDataTemp = new ArrayList<>();
        for (String healthIdTime : this.getAvailableTimestamp()) {
            Date healthDataTimestamp = new Date();
            healthDataTimestamp.setTime(Long.parseLong(healthIdTime));
            if (healthDataTimestamp.after(start) && healthDataTimestamp.before(end)) {
                healthDataTemp.add(new HealthData(new String(this.getSensorInformation().getSensorId() + healthIdTime), this));
            }
        }
        return healthDataTemp;
    }

    public void delete() {
        Global.getSensorGateway().deleteSensorObject(this);
        this.getSensorInformation().getDataReference().removeValue();
    }
}
