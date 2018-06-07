/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 6/6/18 5:24 PM
 */

package sud_tanj.com.phr_android.Database.Sensor.SensorDataTools.Sorted;

import java.util.ArrayList;
import java.util.Date;

import sud_tanj.com.phr_android.Database.Sensor.SensorData;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 06/06/2018 - 15:57.
 * <p>
 * This class last modified by User
 */
public class DataSorted extends Sorted{
    private ArrayList<String> prevData;
    private Date prevDate;

    public DataSorted(SensorData sensorData){
        super(sensorData);
        this.prevDate=new Date();
        this.prevData=new ArrayList<>();
    }


    public ArrayList<String> getDataOn(Date date){
        ArrayList<String> healthDataTemp = new ArrayList<>();
        String healthIdDate, targetDate;
        Date tempDate;
        for (String healthIdTime : this.getAvailableTimestamp()) {
            tempDate = new Date();
            tempDate.setTime(Long.parseLong(healthIdTime));
            healthIdDate = simpleDateFormat.format(tempDate);
            targetDate = simpleDateFormat.format(date);
            if (targetDate.equals(healthIdDate)) {
                healthDataTemp.add(new String(this.sensorData.getSensorInformation().getSensorId() + healthIdTime));
            }
        }
        return healthDataTemp;
    }

    public ArrayList<String> getDataBetween(Date start, Date end) {
        ArrayList<String> healthDataTemp = new ArrayList<>();
        for (String healthIdTime : this.getAvailableTimestamp()) {
            Date healthDataTimestamp = new Date();
            healthDataTimestamp.setTime(Long.parseLong(healthIdTime));
            if (healthDataTimestamp.after(start) && healthDataTimestamp.before(end)) {
                healthDataTemp.add(new String(this.sensorData.getSensorInformation().getSensorId() + healthIdTime));
            }
        }
        return healthDataTemp;
    }
}
