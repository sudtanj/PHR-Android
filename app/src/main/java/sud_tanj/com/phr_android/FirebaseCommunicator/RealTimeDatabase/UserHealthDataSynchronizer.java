/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 6/3/18 3:34 PM
 */

package sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import sud_tanj.com.phr_android.Database.Data.UserData;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.DatabaseSyncable;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.UserDataSyncable;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.UserHealthDataSyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 03/06/2018 - 15:34.
 * <p>
 * This class last modified by User
 */
public class UserHealthDataSynchronizer extends DatabaseSynchronizer {
    private UserData userData;
    public UserHealthDataSynchronizer(DatabaseReference database, UserData userData) {
        super(database);
        this.userData=userData;
    }

    @Override
    protected void runDataChange(DataSnapshot dataSnapshot, DatabaseSyncable listener) {
        ((UserHealthDataSyncable)listener).updateData(this.userData,dataSnapshot);
    }

    @Override
    protected Boolean equals(String value, DatabaseSyncable listener) {
        return ((UserHealthDataSyncable)listener).isEqual(this.userData,value);
    }
}
