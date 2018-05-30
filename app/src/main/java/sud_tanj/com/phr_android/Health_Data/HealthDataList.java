/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/8/18 8:36 PM
 */

package sud_tanj.com.phr_android.Health_Data;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Handler.HandlerLoop;
import sud_tanj.com.phr_android.Health_Data.Interface.DatePickerDataChangeListener;
import sud_tanj.com.phr_android.Health_Data.Interface.DatePickerListener;
import sud_tanj.com.phr_android.Health_Data.Interface.HealthDataListGraphListener;
import sud_tanj.com.phr_android.Health_Data.Interface.SortByDayListener;
import sud_tanj.com.phr_android.Health_Data.Interface.SortByMonthListener;
import sud_tanj.com.phr_android.Health_Data.Interface.SortByYearListener;
import sud_tanj.com.phr_android.MainActivity;
import sud_tanj.com.phr_android.R;

public class HealthDataList extends AppCompatActivity {
    private SensorData sensorData;
    private TextView sensorName;
    private GraphView graph;
    private TextView sensorAnalysis;
    private SimpleDateFormat simpleDateFormat;
    private HealthDataListGraphListener healthDataListGraphListener;
    private HandlerLoop handlerLoop;

    public SensorData getSensorData() {
        return sensorData;
    }

    public GraphView getGraph() {
        return graph;
    }

    public void setHandlerLoop(ArrayList<HealthData> healthData,ArrayList<String> hourData) {
        if(this.handlerLoop!=null){
            this.handlerLoop.removeCallbacks(healthDataListGraphListener);
            this.handlerLoop.removeCallbacksAndMessages(null);
        }
        healthDataListGraphListener=new HealthDataListGraphListener(graph,healthData,hourData,sensorData);
        this.handlerLoop = new HandlerLoop(5, healthDataListGraphListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_data_list);

        Intent intent = getIntent();
        Integer sensorPosition = intent.getIntExtra("sensorposition", 0);
        try {
            this.sensorData = Global.getSensorGateway().getSensorObject().get(sensorPosition);
        } catch(Exception e){
            Toast.makeText(getApplicationContext(),R.string.error_health_list_data,Toast.LENGTH_SHORT);
            finish();
        }
        setTitle(this.sensorData.getSensorInformation().getSensorName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(Boolean.TRUE);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.appHeader)));

        this.sensorName = (TextView) findViewById(R.id.health_data_title);
        this.sensorName.setText(this.sensorData.getSensorInformation().getSensorName());
        graph = (GraphView) findViewById(R.id.health_data_graph);
        this.sensorAnalysis = (TextView) findViewById(R.id.health_data_analysist);

        DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);

        Button sortByYear=(Button) findViewById(R.id.sort_by_year);
        sortByYear.setOnClickListener(new SortByYearListener(datePicker,graph));
        Button sortByMonth=(Button) findViewById(R.id.sort_by_month);
        sortByMonth.setOnClickListener(new SortByMonthListener(datePicker,graph));
        Button sortByDay=(Button) findViewById(R.id.sort_by_day);
        sortByDay.setOnClickListener(new SortByDayListener(datePicker,graph));

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(24);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        graph.getViewport().setScrollable(true);


        //ArrayList<String> healthDataListDate=this.sensorData.getAvailableTimestamp();
        //DatePickerListener datePickerListener=new DatePickerListener(this,new DatePickerDataChangeListener(this.sensorData,graph));
        //datePickerListener.getDateSetListener().setButton(date);
        //date.setOnClickListener(datePickerListener);
        Calendar calendar= Calendar.getInstance();

        ArrayList<HealthData> healthData=null;
        Date dateNow= new Date();
        while ((healthData=this.sensorData.getHealthDataOn(dateNow)).size()==0){
            calendar.add(Calendar.DATE,-1);
            dateNow=calendar.getTime();
        }
        Integer year=calendar.get(Calendar.YEAR);
        Integer monthOfYear=calendar.get(Calendar.MONTH);
        Integer dayOfMonth=calendar.get(Calendar.DATE);
        datePicker.init(year, monthOfYear, dayOfMonth, new DatePickerDataChangeListener(this));
        ArrayList<String> hourData=this.sensorData.getAvailableTimeOn(dateNow);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        //date.setText(simpleDateFormat.format(dateNow));
        this.setHandlerLoop(healthData,hourData);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(this.handlerLoop!=null){
            this.handlerLoop.removeCallbacks(healthDataListGraphListener);
            this.handlerLoop.removeCallbacksAndMessages(null);
            this.handlerLoop=null;
        }
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

}
