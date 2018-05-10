/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/8/18 11:05 AM
 */

package sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import sud_tanj.com.phr_android.Database.Sensor.SensorGateway;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.DatabaseSyncable;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.SensorGatewaySyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 08/05/2018 - 11:05.
 * <p>
 * This class last modified by User
 */
public class SensorGatewaySynchronizer extends DatabaseSynchronizer {
    private SensorGateway sensorGateway;

    public SensorGatewaySynchronizer(DatabaseReference database, SensorGateway sensorGateway) {
        super(database);
        this.sensorGateway = sensorGateway;
    }

    @Override
    protected void runDataChange(DataSnapshot dataSnapshot, DatabaseSyncable listener) {
        ((SensorGatewaySyncable) listener).updateData(this.sensorGateway, dataSnapshot);
    }

    @Override
    protected Boolean equals(String value, DatabaseSyncable listener) {
        return ((SensorGatewaySyncable) listener).isEqual(this.sensorGateway, value);
    }
}
