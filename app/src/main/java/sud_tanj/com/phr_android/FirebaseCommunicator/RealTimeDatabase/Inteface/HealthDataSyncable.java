/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/8/18 9:04 AM
 */

package sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface;

import com.google.firebase.database.DataSnapshot;

import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 08/05/2018 - 9:04.
 * <p>
 * This class last modified by User
 */
public interface HealthDataSyncable extends DatabaseSyncable {
    public void updateData(HealthData healthData, DataSnapshot dataSnapshot);

    public Boolean isEqual(HealthData healthData, String other);
}
