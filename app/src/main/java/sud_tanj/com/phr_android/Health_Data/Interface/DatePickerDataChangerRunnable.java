/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/19/18 6:42 PM
 */

package sud_tanj.com.phr_android.Health_Data.Interface;

import android.view.View;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Database.Data.HealthDataAnalysis;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Database.Sensor.SensorDataTools.Sorted.Sorted;
import sud_tanj.com.phr_android.Handler.Interface.HandlerLoopRunnable;
import sud_tanj.com.phr_android.Health_Data.HealthDataList;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 19/05/2018 - 18:42.
 * <p>
 * This class last modified by User
 */
public class DatePickerDataChangerRunnable implements HandlerLoopRunnable {
    private DatePickerDataChangeListener datePickerDataChangeListener;
    private DatePicker datePicker;
    private ArrayList<HealthData> healthData=new ArrayList<>();
    private ArrayList<String> hourData=new ArrayList<>();
    private ArrayList<String> analysis=new ArrayList<>();
    private ArrayList<String> individualComment=new ArrayList<>();
    private ArrayList<String> doctorComment=new ArrayList<>();

    public DatePickerDataChangerRunnable(DatePickerDataChangeListener datePickerDataChangeListener, DatePicker datePicker) {
        this.datePickerDataChangeListener = datePickerDataChangeListener;
        this.datePicker = datePicker;
    }

    @Override
    public Boolean isHandlerExpired() {
        return Boolean.TRUE;
    }

    @Override
    public void run() {
        GregorianCalendar calendar = new GregorianCalendar(datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth());

        //dateTextView.setText(simpleDateFormat.format(calendar.getTime()));
        SensorData sensorDataTemp=this.datePickerDataChangeListener.getSensorData();
        Date dateTime=calendar.getTime();
        if(this.datePickerDataChangeListener.getHealthDataList().getSortBy().equals(0)) {
            healthData = sensorDataTemp.getHealthDataOn(dateTime);
            hourData = sensorDataTemp.getAvailableTimeOn(dateTime);
            analysis = sensorDataTemp.getHealthDataAnalysisOn(dateTime, Sorted.SORTED_WITH_DAY);
            individualComment= sensorDataTemp.getHealthDataIndividualCommentOn(dateTime,Sorted.SORTED_WITH_DAY);
            doctorComment=sensorDataTemp.getHealthDataDoctorCommentOn(dateTime,Sorted.SORTED_WITH_DAY);
        } else if(this.datePickerDataChangeListener.getHealthDataList().getSortBy().equals(1)){
            healthData = sensorDataTemp.getHealthDataOnMonth(dateTime);
            hourData = sensorDataTemp.getAvailableDayOn(dateTime);
            analysis = sensorDataTemp.getHealthDataAnalysisOn(dateTime, Sorted.SORTED_WITH_MONTH);
            individualComment=sensorDataTemp.getHealthDataIndividualCommentOn(dateTime,Sorted.SORTED_WITH_MONTH);
            doctorComment=sensorDataTemp.getHealthDataDoctorCommentOn(dateTime,Sorted.SORTED_WITH_MONTH);
        } else {
            healthData = sensorDataTemp.getHealthDataOnYear(dateTime);
            hourData = sensorDataTemp.getAvailableMonthOn(dateTime);
            analysis = sensorDataTemp.getHealthDataAnalysisOn(dateTime, Sorted.SORTED_WITH_YEAR);
            individualComment=sensorDataTemp.getHealthDataIndividualCommentOn(dateTime,Sorted.SORTED_WITH_YEAR);
            doctorComment=sensorDataTemp.getHealthDataDoctorCommentOn(dateTime,Sorted.SORTED_WITH_YEAR);
        }


        //write to display

        //this.button.setText(simpleDateFormat.format(calendar.getTime()));
        ((HealthDataList)this.datePickerDataChangeListener.getHealthDataList()).setHandlerLoop(this);
    }

    public ArrayList<HealthData> getHealthData() {
        return healthData;
    }

    public ArrayList<String> getHourData() {
        return hourData;
    }

    public ArrayList<String> getAnalysis() {
        return analysis;
    }

    public ArrayList<String> getIndividualComment() {
        return individualComment;
    }

    public ArrayList<String> getDoctorComment() {
        return doctorComment;
    }
}
