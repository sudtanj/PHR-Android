/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/11/18 9:20 PM
 */

package sud_tanj.com.phr_android.Health_Data.Interface;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.jjoe64.graphview.GraphView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.Handler;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Handler.HandlerLoop;
import sud_tanj.com.phr_android.Health_Data.HealthDataList;
import sud_tanj.com.phr_android.R;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 11/05/2018 - 21:20.
 * <p>
 * This class last modified by User
 */
public class DatePickerDataChangeListener implements DatePicker.OnDateChangedListener {
    public SensorData getSensorData() {
        return sensorData;
    }
    private DatePickerDataChangerRunnable datePickerDataChangerRunnable;
    private HandlerLoop handlerLoop;
    private SensorData sensorData;
        private GraphView graph;
        private Button button;

    public HealthDataList getHealthDataList() {
        return healthDataList;
    }

    private HealthDataList healthDataList;

        public DatePickerDataChangeListener(HealthDataList healthDataList) {
            this.healthDataList=healthDataList;
            this.sensorData = healthDataList.getSensorData();
            this.graph = healthDataList.getGraph();
        }

        public void setButton(Button button){
            this.button=button;
        }


    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        if(handlerLoop!=null){
            handlerLoop.removeCallbacks(datePickerDataChangerRunnable);
            handlerLoop.removeCallbacksAndMessages(null);
        }
            datePickerDataChangerRunnable=new DatePickerDataChangerRunnable(this,datePicker);
            handlerLoop=new HandlerLoop(5,datePickerDataChangerRunnable);

       /**
        GregorianCalendar calendar = new GregorianCalendar(datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth());

        //dateTextView.setText(simpleDateFormat.format(calendar.getTime()));
        ArrayList<HealthData> healthData = this.sensorData.getHealthDataOn(calendar.getTime());
        ArrayList<String> hourData=this.sensorData.getAvailableTimeOn(calendar.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        //this.button.setText(simpleDateFormat.format(calendar.getTime()));
        ((HealthDataList)this.healthDataList).setHandlerLoop(healthData,hourData);
       // HandlerLoop handlerLoop = new HandlerLoop(5, new HealthDataListGraphListener(this.graph, healthData,hourData));
    */}
}
