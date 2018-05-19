/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/19/18 4:43 PM
 */

package sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface;

import com.google.firebase.database.DataSnapshot;

import sud_tanj.com.phr_android.Database.Sensor.SensorData;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 19/05/2018 - 16:43.
 * <p>
 * This class last modified by User
 */
public interface UserDataSyncable extends DatabaseSyncable {
    public void updateData(DataSnapshot dataSnapshot);

    public Boolean isEqual(String other);
}
