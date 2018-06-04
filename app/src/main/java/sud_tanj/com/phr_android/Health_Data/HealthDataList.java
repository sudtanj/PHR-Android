/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/8/18 8:36 PM
 */

package sud_tanj.com.phr_android.Health_Data;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Data.DoctorCommentData;
import sud_tanj.com.phr_android.Database.Data.IndividualCommentData;
import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Database.Data.HealthDataAnalysis;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Handler.LongOneTimeOperation;
import sud_tanj.com.phr_android.Health_Data.Interface.DatePickerDataChangeListener;
import sud_tanj.com.phr_android.Health_Data.Interface.HealthDataListGraphListener;
import sud_tanj.com.phr_android.Health_Data.Interface.SortByDayListener;
import sud_tanj.com.phr_android.Health_Data.Interface.SortByMonthListener;
import sud_tanj.com.phr_android.Health_Data.Interface.SortByYearListener;
import sud_tanj.com.phr_android.R;

public class HealthDataList extends AppCompatActivity {
    private SensorData sensorData;
    private TextView sensorName;
    private GraphView graph;
    private TextView sensorAnalysis;
    private SimpleDateFormat simpleDateFormat;
    private HealthDataListGraphListener healthDataListGraphListener;
    private LongOneTimeOperation longOneTimeOperation;

    public Integer getSortBy() {
        return sortBy;
    }

    public void setSortBy(Integer sortBy) {
        this.sortBy = sortBy;
    }

    private Integer sortBy=0;
    private DatePicker datePicker;
    private TextView analysis;
    private EditText commentData;
    private Spinner commentRole;
    public SensorData getSensorData() {
        return sensorData;
    }

    public GraphView getGraph() {
        return graph;
    }

    public void setHandlerLoop(ArrayList<HealthData> healthData, ArrayList<String> hourData, ArrayList<HealthDataAnalysis> analysis) {
        if(this.longOneTimeOperation!=null){
            this.healthDataListGraphListener.onStop();
            this.longOneTimeOperation.cancel(Boolean.TRUE);
        }
        healthDataListGraphListener=new HealthDataListGraphListener(this,healthData,hourData,sensorData,analysis);
        this.longOneTimeOperation=new LongOneTimeOperation(healthDataListGraphListener);
        this.longOneTimeOperation.execute();
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
        this.graph = (GraphView) findViewById(R.id.health_data_graph);
        this.sensorAnalysis = (TextView) findViewById(R.id.health_data_analysist);

        this.datePicker = (DatePicker) findViewById(R.id.date_picker);
        this.analysis=(TextView) findViewById(R.id.analysis_summary);

        commentData=(EditText) findViewById(R.id.comment);
        commentRole=(Spinner) findViewById(R.id.role);
        Button sendCommentButton=(Button) findViewById(R.id.send_comment);

        sendCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String role=commentRole.getSelectedItem().toString();
                if(role.equals("Doctor")){
                    DoctorCommentData comment=new DoctorCommentData(sensorData);
                    comment.setComment(commentData.getText().toString());
                } else {
                    IndividualCommentData comment=new IndividualCommentData(sensorData);
                    comment.setComment(commentData.getText().toString());
                }
                commentData.setText(new String());
            }
        });

        Button sortByYear=(Button) findViewById(R.id.sort_by_year);
        sortByYear.setOnClickListener(new SortByYearListener(datePicker,graph,this));
        Button sortByMonth=(Button) findViewById(R.id.sort_by_month);
        sortByMonth.setOnClickListener(new SortByMonthListener(datePicker,graph,this));
        Button sortByDay=(Button) findViewById(R.id.sort_by_day);
        sortByDay.setOnClickListener(new SortByDayListener(datePicker,graph,this));

        sortByDay.performClick();

    }

    @Override
    protected void onStart() {
        super.onStart();
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(24);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        graph.getViewport().setScrollable(true);

        graph.getGridLabelRenderer().setVerticalAxisTitle("Value");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Hour");


        //ArrayList<String> healthDataListDate=this.sensorData.getAvailableTimestamp();
        //DatePickerListener datePickerListener=new DatePickerListener(this,new DatePickerDataChangeListener(this.sensorData,graph));
        //datePickerListener.getDateSetListener().setButton(date);
        //date.setOnClickListener(datePickerListener);
        Calendar calendar= Calendar.getInstance();
        //ArrayList<HealthData> healthData=new ArrayList<>();
        Date dateNow=new Date();
        if(this.sensorData.getSensorData().size()>0) {
            dateNow = this.sensorData.getLatestData().getTimeStamp();
            calendar.setTime(dateNow);
        }
        /**
        if(this.sensorData.getSensorData().size()>0) {
            if(healthData.size()==0) {
                dateNow=this.sensorData.getLatestData().getTimeStamp();
                healthData=this.sensorData.getHealthDataOn(dateNow);
                calendar.setTime(dateNow);
                 while ((healthData = this.sensorData.getHealthDataOn(dateNow)).size() == 0) {
                 calendar.add(Calendar.DATE, -1);
                 dateNow = calendar.getTime();
                 }
            }
        }
        */
        Integer year=calendar.get(Calendar.YEAR);
        Integer monthOfYear=calendar.get(Calendar.MONTH);
        Integer dayOfMonth=calendar.get(Calendar.DATE);
        datePicker.init(year, monthOfYear, dayOfMonth, new DatePickerDataChangeListener(this));
        datePicker.updateDate(year,monthOfYear,dayOfMonth);
        //ArrayList<String> hourData=this.sensorData.getAvailableTimeOn(dateNow);
        //date.setText(simpleDateFormat.format(dateNow));

        //this.setHandlerLoop(healthData,hourData);


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
        if(this.longOneTimeOperation!=null){
            healthDataListGraphListener.onStop();
            this.longOneTimeOperation.cancel(Boolean.TRUE);
        }
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    public TextView getAnalysis() {
        return analysis;
    }
}
