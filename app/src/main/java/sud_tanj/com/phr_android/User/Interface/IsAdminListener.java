/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/19/18 4:42 PM
 */

package sud_tanj.com.phr_android.User.Interface;

import com.google.firebase.database.DataSnapshot;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.DatabaseUtility;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.DatabaseSyncable;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.UserDataSyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 19/05/2018 - 16:42.
 * <p>
 * This class last modified by User
 */
public class IsAdminListener implements UserDataSyncable {
    private Boolean admin;
    public IsAdminListener(Boolean admin) {
        this.admin=admin;
    }

    @Override
    public void updateData(DataSnapshot dataSnapshot) {
        if(dataSnapshot.exists()) {
            Global.setAdmin(DatabaseUtility.convertToBoolean(dataSnapshot));
        }
    }

    @Override
    public Boolean isEqual(String other) {
        return this.admin.toString().equals(other);
    }
}
