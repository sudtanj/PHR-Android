/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/8/18 9:23 AM
 */

package sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.DatabaseSyncable;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.SensorSyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 08/05/2018 - 9:23.
 * <p>
 * This class last modified by User
 */
public class SensorSynchronizer extends DatabaseSynchronizer {
    private SensorData sensorData;

    public SensorSynchronizer(DatabaseReference database, SensorData sensorData) {
        super(database);
        this.sensorData = sensorData;

    }

    @Override
    protected void runDataChange(DataSnapshot dataSnapshot, DatabaseSyncable listener) {
        ((SensorSyncable) listener).updateData(this.sensorData, dataSnapshot);
    }

    @Override
    protected Boolean equals(String value, DatabaseSyncable listener) {
        return ((SensorSyncable) listener).isEqual(this.sensorData, value);
    }
}
