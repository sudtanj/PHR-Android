/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 6/3/18 3:36 PM
 */

package sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface;

import com.google.firebase.database.DataSnapshot;

import sud_tanj.com.phr_android.Database.Data.UserData;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 03/06/2018 - 15:36.
 * <p>
 * This class last modified by User
 */
public interface UserHealthDataSyncable extends DatabaseSyncable {
    public void updateData(UserData userData, DataSnapshot dataSnapshot);

    public Boolean isEqual(UserData userData,String other);
}
