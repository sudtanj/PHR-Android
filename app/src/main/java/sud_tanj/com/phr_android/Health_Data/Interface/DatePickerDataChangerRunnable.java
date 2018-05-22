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
import java.util.GregorianCalendar;
import java.util.Locale;

import sud_tanj.com.phr_android.Database.Data.HealthData;
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
        ArrayList<HealthData> healthData = this.datePickerDataChangeListener.getSensorData().getHealthDataOn(calendar.getTime());
        ArrayList<String> hourData=this.datePickerDataChangeListener.getSensorData().getAvailableTimeOn(calendar.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        //this.button.setText(simpleDateFormat.format(calendar.getTime()));
        ((HealthDataList)this.datePickerDataChangeListener.getHealthDataList()).setHandlerLoop(healthData,hourData);
    }
}