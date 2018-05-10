/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/8/18 8:56 AM
 */

package sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.DatabaseSyncable;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.HealthDataSyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 08/05/2018 - 8:56.
 * <p>
 * This class last modified by User
 */
public class HealthDataSynchronizer extends DatabaseSynchronizer {
    private HealthData healthData;

    public HealthDataSynchronizer(DatabaseReference database, HealthData healthData) {
        super(database);
        this.healthData = healthData;
    }

    @Override
    protected void runDataChange(DataSnapshot dataSnapshot, DatabaseSyncable listener) {
        ((HealthDataSyncable) listener).updateData(this.healthData, dataSnapshot);
    }

    @Override
    protected Boolean equals(String value, DatabaseSyncable listener) {
        return ((HealthDataSyncable) listener).isEqual(this.healthData, value);
    }
}
