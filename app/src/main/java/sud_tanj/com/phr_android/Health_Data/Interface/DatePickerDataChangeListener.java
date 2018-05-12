/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/11/18 9:20 PM
 */

package sud_tanj.com.phr_android.Health_Data.Interface;

import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Handler.HandlerLoop;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 11/05/2018 - 21:20.
 * <p>
 * This class last modified by User
 */
public class DatePickerDataChangeListener implements DatePickerDialog.OnDateSetListener {
        private SensorData sensorData;
        private GraphView graph;
        private Button button;

        public DatePickerDataChangeListener(SensorData sensorData, GraphView graph) {
            this.sensorData = sensorData;
            this.graph = graph;
        }

        public void setButton(Button button){
            this.button=button;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
            //dateTextView.setText(simpleDateFormat.format(calendar.getTime()));
            ArrayList<HealthData> healthData = this.sensorData.getHealthDataOn(calendar.getTime());
            ArrayList<String> hourData=this.sensorData.getAvailableTimeOn(calendar.getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
            this.button.setText(simpleDateFormat.format(calendar.getTime()));
            HandlerLoop handlerLoop = new HandlerLoop(5, new HealthDataListGraphListener(this.graph, healthData,hourData));
        }
}
