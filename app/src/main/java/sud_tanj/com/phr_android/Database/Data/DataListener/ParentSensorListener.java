/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/8/18 9:40 AM
 */

package sud_tanj.com.phr_android.Database.Data.DataListener;

import com.google.firebase.database.DataSnapshot;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.DatabaseUtility;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.HealthDataSyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 08/05/2018 - 9:40.
 * <p>
 * This class last modified by User
 */
public class ParentSensorListener implements HealthDataSyncable {
    @Override
    public void updateData(HealthData healthData, DataSnapshot dataSnapshot) {
        SensorData healthDataParentSensor = healthData.getParentSensor();
        String value = DatabaseUtility.convertToString(dataSnapshot);
        if (!healthDataParentSensor.getSensorInformation().getSensorName().equals(value))
            healthData.setParentSensor(Global.getSensorGateway().getSensorData(value));
    }

    @Override
    public Boolean isEqual(HealthData healthData, String other) {
        if (healthData.getParentSensor().getSensorInformation().getSensorId().equals(other))
            return Boolean.TRUE;
        return Boolean.FALSE;
    }
}
