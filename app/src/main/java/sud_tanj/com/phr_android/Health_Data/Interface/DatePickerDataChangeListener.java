/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/11/18 9:20 PM
 */

package sud_tanj.com.phr_android.Health_Data.Interface;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;

import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Handler.HandlerLoop;
import sud_tanj.com.phr_android.Health_Data.HealthDataList;

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
    private DatePickerDataChangerRunnable datePickerDataChangerRunnable;
    private HandlerLoop handlerLoop;
    private SensorData sensorData;
    private GraphView graph;
    private Button button;
    private TextView analysis;
    private HealthDataList healthDataList;

    public DatePickerDataChangeListener(HealthDataList healthDataList) {
        this.healthDataList = healthDataList;
        this.sensorData = healthDataList.getSensorData();
        this.graph = healthDataList.getGraph();
        this.analysis=healthDataList.getAnalysis();
    }

    public SensorData getSensorData() {
        return sensorData;
    }

    public HealthDataList getHealthDataList() {
        return healthDataList;
    }

    public void setButton(Button button) {
        this.button = button;
    }


    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        if (handlerLoop != null) {
            handlerLoop.removeCallbacks(datePickerDataChangerRunnable);
            handlerLoop.removeCallbacksAndMessages(null);
        }
        datePickerDataChangerRunnable = new DatePickerDataChangerRunnable(this, datePicker);
        handlerLoop = new HandlerLoop(5, datePickerDataChangerRunnable);

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
