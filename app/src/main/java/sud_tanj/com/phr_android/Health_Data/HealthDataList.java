/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/8/18 8:36 PM
 */

package sud_tanj.com.phr_android.Health_Data;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.R;

public class HealthDataList extends AppCompatActivity {
    private SensorData sensorData;
    private TextView sensorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_data_list);

        Intent intent = getIntent();
        Integer sensorPosition=intent.getIntExtra("sensorposition",0);

        this.sensorData= Global.getSensorGateway().getSensorObject().get(sensorPosition);

        setTitle(this.sensorData.getSensorInformation().getSensorName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(Boolean.TRUE);

        this.sensorName=(TextView)findViewById(R.id.health_data_title);
        this.sensorName.setText(this.sensorData.getSensorInformation().getSensorName());

        GraphView graph = (GraphView) findViewById(R.id.health_data_graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3)
        });
        graph.addSeries(series);
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
    public void onBackPressed(){
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}
