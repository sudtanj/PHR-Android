/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/8/18 11:06 AM
 */

package sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface;

import com.google.firebase.database.DataSnapshot;

import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Database.Sensor.SensorGateway;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 08/05/2018 - 11:06.
 * <p>
 * This class last modified by User
 */
public interface SensorGatewaySyncable extends DatabaseSyncable {
    public void updateData(SensorGateway sensorGateway, DataSnapshot dataSnapshot);

    public Boolean isEqual(SensorGateway sensorGateway, String other);
}
