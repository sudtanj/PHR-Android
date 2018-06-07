/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 6/6/18 5:24 PM
 */

package sud_tanj.com.phr_android.Database.Sensor.SensorDataTools.Sorted;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import sud_tanj.com.phr_android.Database.Sensor.SensorData;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 06/06/2018 - 17:03.
 * <p>
 * This class last modified by User
 */
public class TimeSorted extends Sorted {
    private SimpleDateFormat hourDateFormat;
    public TimeSorted(SensorData sensorData) {
        super(sensorData);
    }

    public void matchHour(){
        this.simpleDateFormat=new SimpleDateFormat("dd MM yyyy", Locale.US);
        this.hourDateFormat=new SimpleDateFormat("HH", Locale.US);
    }
    public void matchDay(){
        this.simpleDateFormat=new SimpleDateFormat("MM yyyy", Locale.US);
        this.hourDateFormat=new SimpleDateFormat("dd", Locale.US);
    }

    public void matchMonth(){
        this.simpleDateFormat=new SimpleDateFormat("yyyy", Locale.US);
        this.hourDateFormat=new SimpleDateFormat("MM", Locale.US);
    }

    public ArrayList<String> getAvailableTimeOn(Date date) {
        ArrayList<String> availableTimeTemp = new ArrayList<String>();
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
}
