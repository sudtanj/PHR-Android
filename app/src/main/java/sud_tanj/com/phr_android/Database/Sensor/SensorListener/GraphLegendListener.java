/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/21/18 6:13 PM
 */

package sud_tanj.com.phr_android.Database.Sensor.SensorListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.List;

import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Database.Sensor.SensorInformation;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.SensorSyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 21/05/2018 - 18:13.
 * <p>
 * This class last modified by User
 */
public class GraphLegendListener implements SensorSyncable {
    @Override
    public void updateData(SensorData sensor, DataSnapshot dataSnapshot) {
        GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
        List<String> value = dataSnapshot.getValue(t);
        SensorInformation sensorInformation=sensor.getSensorInformation();
        if (!sensorInformation.getGraphLegend().equals(value))
            sensorInformation.setGraphLegend((ArrayList<String>) value);
    }

    @Override
    public Boolean isEqual(SensorData sensor, String other) {
        return other.equals(sensor.getSensorInformation().getGraphLegend().toString());
    }
}
