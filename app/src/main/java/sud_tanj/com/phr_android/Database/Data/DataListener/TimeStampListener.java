/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/8/18 8:30 AM
 */

package sud_tanj.com.phr_android.Database.Data.DataListener;

import com.google.firebase.database.DataSnapshot;

import java.util.Date;

import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Database.Sensor.SensorInformation;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.DatabaseUtility;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.DatabaseSyncable;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.HealthDataSyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 08/05/2018 - 8:30.
 * <p>
 * This class last modified by User
 */
public class TimeStampListener implements HealthDataSyncable {

    @Override
    public void updateData(HealthData healthData, DataSnapshot dataSnapshot) {
        Date timeStamp=healthData.getTimeStamp();
        Date value= new Date();
        value.setTime(DatabaseUtility.convertToLong(dataSnapshot));
        if(!timeStamp.equals(value))
            healthData.setTimeStamp(value);
    }

    @Override
    public Boolean isEqual(HealthData healthData, String other) {
        if(String.valueOf(healthData.getTimeStamp().getTime()).equals(other)){
            return true;
        }
        return false;
    }
}
