/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 6/6/18 5:24 PM
 */

package sud_tanj.com.phr_android.Database.Sensor.SensorDataTools.Sorted;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import sud_tanj.com.phr_android.Database.Sensor.SensorData;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 06/06/2018 - 16:55.
 * <p>
 * This class last modified by User
 */
public class Sorted {
    public static final int SORTED_WITH_HOUR=0,SORTED_WITH_DAY=1,SORTED_WITH_MONTH=2,SORTED_WITH_YEAR=3;
    protected SimpleDateFormat simpleDateFormat;
    protected ArrayList<String> prevQuery,data;
    protected Integer arraySize;
    protected SensorData sensorData;

    public Sorted(SensorData sensorData) {
        this.sensorData = sensorData;
        this.data=new ArrayList<>();
        this.arraySize=this.data.size();
        this.prevQuery=new ArrayList<>();
    }

    public void setHealthDataSortedMatch(int sortedWith){
        System.out.println("Switch case "+sortedWith);
        switch(sortedWith){
            case SORTED_WITH_HOUR:
                this.matchHour();
                break;
            case SORTED_WITH_DAY:
                this.matchDay();
                break;
            case SORTED_WITH_MONTH:
                this.matchMonth();
                break;
            case SORTED_WITH_YEAR:
                this.matchYear();
                break;
            default:
                break;
        }
    }

    public void setDateFormat(String dateFormat){
        simpleDateFormat=new SimpleDateFormat(dateFormat, Locale.US);
    }

    public void matchHour(){
        this.setDateFormat("dd MM yyyy HH");
    }

    public void matchDay(){
        this.setDateFormat("dd MM yyyy");
    }

    public void matchMonth(){
        this.setDateFormat("MM yyyy");
    }

    public void matchYear(){
        this.setDateFormat("yyyy");
    }

    public void setData(ArrayList<String> data){
        this.data=data;
        this.arraySize=data.size();
    }

    public ArrayList<String> getAvailableTimestamp() {
        ArrayList<String> temp = new ArrayList<>();
        for (String healthId : this.data) {
            String tempHealthId = healthId.replace(this.sensorData.getSensorInformation().getSensorId(), "");
            temp.add(tempHealthId);
        }
        return temp;
    }
}
