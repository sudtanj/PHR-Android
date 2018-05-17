/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/8/18 11:09 AM
 */

package sud_tanj.com.phr_android.Database.Sensor.SensorGatewayListener;

import com.google.firebase.database.DataSnapshot;

import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Database.Sensor.SensorGateway;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.SensorGatewaySyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 08/05/2018 - 11:09.
 * <p>
 * This class last modified by User
 */
public class SensorInitializerListener implements SensorGatewaySyncable {
    @Override
    public void updateData(SensorGateway sensorGateway, DataSnapshot dataSnapshot) {
        sensorGateway.resetSensorList();
        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
            sensorGateway.getSensorObject().add(new SensorData(childSnapshot.getKey()));
        }
    }

    @Override
    public Boolean isEqual(SensorGateway sensorGateway, String other) {
        return false;
    }
}
