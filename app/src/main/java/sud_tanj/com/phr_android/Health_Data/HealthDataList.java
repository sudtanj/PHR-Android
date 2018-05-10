/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/8/18 8:36 PM
 */

package sud_tanj.com.phr_android.Health_Data;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Handler.HandlerLoop;
import sud_tanj.com.phr_android.Health_Data.Interface.HealthDataListGraphListener;
import sud_tanj.com.phr_android.MainActivity;
import sud_tanj.com.phr_android.R;

public class HealthDataList extends AppCompatActivity implements com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {
    private SensorData sensorData;
    private TextView sensorName;
    private  SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_data_list);

        Intent intent = getIntent();
        Integer sensorPosition = intent.getIntExtra("sensorposition", 0);

        this.sensorData = Global.getSensorGateway().getSensorObject().get(sensorPosition);

        setTitle(this.sensorData.getSensorInformation().getSensorName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(Boolean.TRUE);

        this.sensorName = (TextView) findViewById(R.id.health_data_title);
        this.sensorName.setText(this.sensorData.getSensorInformation().getSensorName());

        GraphView graph = (GraphView) findViewById(R.id.health_data_graph);
        Button date=(Button) findViewById(R.id.choose_date);

        ArrayList<String> healthDataListDate=this.sensorData.getAvailableTimestamp();
        simpleDateFormat = new SimpleDateFormat("dd MM yyyy", Locale.US);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate(1980, 0, 1, R.style.NumberPickerStyle);
            }
        });


        Date start = new Date();
        start.setTime(Long.parseLong("1525919698899"));
        Date end = new Date();
        end.setTime(Long.parseLong("1525919717094"));
        ArrayList<HealthData> healthData=this.sensorData.getHealthDataBetween(start,end);
        HandlerLoop handlerLoop=new HandlerLoop(5,new HealthDataListGraphListener(graph,healthData));
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
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }


    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(HealthDataList.this).callback(HealthDataList.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        //dateTextView.setText(simpleDateFormat.format(calendar.getTime()));
        System.out.println(simpleDateFormat.format(calendar.getTime()));
    }
}
