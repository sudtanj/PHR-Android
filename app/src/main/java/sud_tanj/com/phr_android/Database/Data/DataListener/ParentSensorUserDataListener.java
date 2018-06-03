/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 6/3/18 4:01 PM
 */

package sud_tanj.com.phr_android.Database.Data.DataListener;

import com.google.firebase.database.DataSnapshot;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Data.UserData;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.DatabaseUtility;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.UserHealthDataSyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 03/06/2018 - 16:01.
 * <p>
 * This class last modified by User
 */
public class ParentSensorUserDataListener implements UserHealthDataSyncable {
    @Override
    public void updateData(UserData userData, DataSnapshot dataSnapshot) {
        SensorData healthDataParentSensor = userData.getParentSensor();
        String value = DatabaseUtility.convertToString(dataSnapshot);
        if (!healthDataParentSensor.getSensorInformation().getSensorName().equals(value))
            userData.setParentSensor(Global.getSensorGateway().getSensorData(value));
    }

    @Override
    public Boolean isEqual(UserData userData, String other) {
        return userData.getParentSensor().getSensorInformation().getSensorId().equals(other);
    }
}
