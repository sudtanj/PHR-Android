/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/19/18 4:45 PM
 */

package sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.DatabaseSyncable;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.UserDataSyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 19/05/2018 - 16:45.
 * <p>
 * This class last modified by User
 */
public class UserDataSynchronizer extends DatabaseSynchronizer {
    public UserDataSynchronizer(DatabaseReference database) {
        super(database);
    }

    @Override
    protected void runDataChange(DataSnapshot dataSnapshot, DatabaseSyncable listener) {
        ((UserDataSyncable)listener).updateData(dataSnapshot);
    }

    @Override
    protected Boolean equals(String value, DatabaseSyncable listener) {
        return ((UserDataSyncable)listener).isEqual(value);
    }
}
