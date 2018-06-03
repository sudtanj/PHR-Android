/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 6/3/18 4:02 PM
 */

package sud_tanj.com.phr_android.Database.Data.DataListener;

import com.google.firebase.database.DataSnapshot;

import java.util.Date;

import sud_tanj.com.phr_android.Database.Data.UserData;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.DatabaseUtility;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.UserHealthDataSyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 03/06/2018 - 16:02.
 * <p>
 * This class last modified by User
 */
public class TimeStampUserDataListener implements UserHealthDataSyncable {
    @Override
    public void updateData(UserData userData, DataSnapshot dataSnapshot) {
        Date timeStamp = userData.getTimeStamp();
        Date value = new Date();
        value.setTime(DatabaseUtility.convertToLong(dataSnapshot));
        if (!timeStamp.equals(value))
            userData.setTimeStampWithoutSync(value);
    }

    @Override
    public Boolean isEqual(UserData userData, String other) {
        return String.valueOf(userData.getTimeStamp().getTime()).equals(other);
    }
}
