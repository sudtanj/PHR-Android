/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/6/18 5:35 PM
 */

package sud_tanj.com.phr_android.Health_Sensor;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.riddhimanadib.formmaster.FormBuilder;
import me.riddhimanadib.formmaster.model.BaseFormElement;
import me.riddhimanadib.formmaster.model.FormElementTextSingleLine;
import me.riddhimanadib.formmaster.model.FormHeader;
import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.R;

public class ModifySensor extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FormBuilder mFormBuilder;
    private FormElementTextSingleLine elementSensorId;
    private FormElementTextSingleLine elementName;
    private FormElementTextSingleLine elementEmbedded;
    private FormElementTextSingleLine elementGraphLegend;
    private Bundle data;
    private SensorData currentSensor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_sensors);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.appHeader)));

        data = getIntent().getExtras();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mFormBuilder = new FormBuilder(getApplicationContext(), mRecyclerView);

        FormHeader header=null;
        if (data.getBoolean("modifySensor")){
            header = FormHeader.createInstance("Editing Existing Sensor");
            currentSensor = Global.getSensorGateway().getSensorDataByName(data.getString("sensorName"));
            elementSensorId = FormElementTextSingleLine.createInstance().setTitle("Sensor ID").setValue(currentSensor.getSensorInformation().getSensorId()).setHint("Sensor Id");
            this.getSupportActionBar().setTitle(currentSensor.getSensorInformation().getSensorName()+"'s Information");
        }
        if( header ==null ) {
            header = FormHeader.createInstance("Adding New Sensor");
        }
        elementName = FormElementTextSingleLine.createInstance().setTitle("SensorListener Name").setValue("").setHint("Enter the name of the sensor");
        elementEmbedded = FormElementTextSingleLine.createInstance().setTitle("Embeddedscript Name").setValue("").setHint("Tell the script name that use for the sensor loader");
        elementGraphLegend = FormElementTextSingleLine.createInstance().setTitle("Graph Legend").setValue("").setHint("Seperate with ,. example=systolic,diastolic ");

        List<BaseFormElement> formItems = new ArrayList<>();
        formItems.add(header);
        if (data.getBoolean("modifySensor")) {
            formItems.add(elementSensorId);
        }
        formItems.add(elementName);
        formItems.add(elementEmbedded);
        formItems.add(elementGraphLegend);

        mFormBuilder.addFormElements(formItems);

        if (data.getBoolean("modifySensor")) {
            elementName.setValue(currentSensor.getSensorInformation().getSensorName());
            elementEmbedded.setValue(currentSensor.getBackgroundJob().getName());
            ArrayList<String> graphLegend=currentSensor.getSensorInformation().getGraphLegend();
            elementGraphLegend.setValue(TextUtils.join(",",graphLegend));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.clear();
        getMenuInflater().inflate(R.menu.right_menu, menu);
        if (data.getBoolean("modifySensor")) {
            MenuItem deleteButton = menu.findItem(R.id.sensor_edit_delete);
            deleteButton.setVisible(Boolean.TRUE);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.sensor_edit_delete:
                if (data.getBoolean("modifySensor")) {
                    currentSensor.delete();
                    finish();
                    return true;
                }
                return true;
            case R.id.action_save:
                //Do Whatever you want to do here.
                if (data.getBoolean("modifySensor")) {
                    currentSensor.getSensorInformation().setSensorName(elementName.getValue());
                    currentSensor.getBackgroundJob().setName(elementEmbedded.getValue());
                    String[] graphLegend = elementGraphLegend.getValue().split(",");
                    ArrayList<String> temp=new ArrayList<>();
                    for(int i=0;i<graphLegend.length;i++){
                        temp.add(graphLegend[i]);
                    }
                    currentSensor.getSensorInformation().setGraphLegend(temp);
                    finish();
                    return true;
                }
                if (!Global.getSensorGateway().isSensorNameExist(elementName.getValue())) {
                    SensorData temp = null;
                    while (temp == null)
                        temp = Global.getSensorGateway().createSensorDataObject(elementName.getValue() + String.valueOf(new Random().nextInt(1000)));
                    temp.getSensorInformation().setSensorName(elementName.getValue());
                    temp.getBackgroundJob().setName(elementEmbedded.getValue());
                    temp.getSensorInformation().setSensorOwner(Global.getFireBaseUser().getUid());
                    String[] graphLegend = elementGraphLegend.getValue().split(",");
                    for(int i=0;i<graphLegend.length;i++){
                        temp.getSensorInformation().addGraphLegend(graphLegend[i]);
                    }
                    Toast.makeText(getApplicationContext(), "SensorListener added succesfully!", Toast.LENGTH_SHORT);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "SensorListener failed to be added! please change to other name", Toast.LENGTH_SHORT);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
