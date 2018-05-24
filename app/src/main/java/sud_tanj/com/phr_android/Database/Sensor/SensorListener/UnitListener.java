/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/24/18 8:53 AM
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
 * Created by Sudono Tanjung on 24/05/2018 - 8:53.
 * <p>
 * This class last modified by User
 */
public class UnitListener implements SensorSyncable {
    @Override
    public void updateData(SensorData sensor, DataSnapshot dataSnapshot) {
        GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
        List<String> value = dataSnapshot.getValue(t);
        SensorInformation sensorInformation=sensor.getSensorInformation();
        if (!sensorInformation.getUnit().equals(value))
            sensorInformation.setUnit((ArrayList<String>) value);
    }

    @Override
    public Boolean isEqual(SensorData sensor, String other) {
        return other.equals(sensor.getSensorInformation().getUnit().toString());
    }
}
