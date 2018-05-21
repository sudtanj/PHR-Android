/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/7/18 9:12 PM
 */

package sud_tanj.com.phr_android.Database.Sensor;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Sensor.SensorListener.EmbeddedScriptListener;
import sud_tanj.com.phr_android.Database.Sensor.SensorListener.GraphLegendListener;
import sud_tanj.com.phr_android.Database.Sensor.SensorListener.NameListener;
import sud_tanj.com.phr_android.Database.Sensor.SensorListener.OwnerListener;
import sud_tanj.com.phr_android.Database.Sensor.SensorListener.StatusListener;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.SensorSynchronizer;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 07/05/2018 - 21:12.
 * <p>
 * This class last modified by User
 */
public class SensorInformation {
    public static final String SENSOR_DATA_CHILD_NAME = "sensor";
    private String sensorId = null, sensorName = null, sensorOwner = null;
    private Boolean sensorActive = null;
    private SensorData sensorData = null;
    private DatabaseReference dataReference = null, userDataReference = null;
    private ArrayList<String> graphLegend;
    private SensorSynchronizer sensorInformation, sensorStatus;

    public SensorInformation(String sensorId, SensorData sensorData) {
        this.setDataReference(Global.getMainDatabase().child(SENSOR_DATA_CHILD_NAME).child(sensorId));
        this.setUserDataReference(Global.getUserDatabase().child("sensors").child(sensorId));

        sensorInformation = new SensorSynchronizer(this.getDataReference(), sensorData);
        sensorStatus = new SensorSynchronizer(this.getUserDataReference(), sensorData);

        this.sensorId = sensorId;
        this.sensorData = sensorData;
        this.sensorActive = true;
        this.sensorName = new String();
        this.sensorOwner = new String();
        this.graphLegend = new ArrayList<>();

        //firebase sync
        sensorInformation.add(new NameListener(), "Name");
        sensorInformation.add(new OwnerListener(), "Owner");
        sensorInformation.add(new EmbeddedScriptListener(), "EmbeddedScript");
        sensorInformation.add(new GraphLegendListener(),"GraphLegend");

        //sensorStatus.add(new StatusListener(), "SensorActive");
    }

    public ArrayList<String> getGraphLegend() {
        return graphLegend;
    }

    public void setGraphLegend(ArrayList<String> graphLegend) {
        this.graphLegend = graphLegend;
        this.sensorInformation.changeVariable(graphLegend);
        //this.getDataReference().child("GraphLegend").setValue(this.graphLegend);
    }

    public void addGraphLegend(String graphLegend){
        this.graphLegend.add(graphLegend);
        this.sensorInformation.changeVariable(this.graphLegend);
    }

    public void setSensorActive(Boolean sensorActive) {
        this.sensorActive = sensorActive;
        sensorStatus.changeVariable(String.valueOf(this.sensorActive));
    }

    public String getSensorId() {
        return sensorId;
    }

    private void setSensorId(String sensorId) {
        this.sensorId = sensorId;
        sensorInformation.changeVariable(this.sensorId);
    }

    public String getSensorOwner() {
        return sensorOwner;
    }

    public void setSensorOwner(String sensorOwner) {
        this.sensorOwner = sensorOwner;
        sensorInformation.changeVariable(this.sensorOwner);
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
        sensorInformation.changeVariable(this.sensorName);
    }

    public DatabaseReference getUserDataReference() {
        return userDataReference;
    }

    public void setUserDataReference(DatabaseReference userDataReference) {
        this.userDataReference = userDataReference;
    }

    public DatabaseReference getDataReference() {
        return dataReference;
    }

    private void setDataReference(DatabaseReference dataReference) {
        this.dataReference = dataReference;
    }

    public Boolean isSensorActive() {
        return this.sensorActive;
    }
}
