/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/7/18 7:56 PM
 */

package sud_tanj.com.phr_android.Database.Sensor.SensorListener;

import com.google.firebase.database.DataSnapshot;

import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Database.Sensor.SensorEmbeddedScript;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.DatabaseUtility;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.DatabaseSyncable;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.SensorSyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 07/05/2018 - 19:47.
 * <p>
 * This class last modified by User
 */
public class EmbeddedScriptListener implements SensorSyncable {

    @Override
    public void updateData(SensorData sensor, DataSnapshot dataSnapshot) {
        String scriptName=DatabaseUtility.convertToString(dataSnapshot);
        if(sensor.getBackgroundJob()==null)
            sensor.setBackgroundJob(new SensorEmbeddedScript(scriptName,sensor));
        else {
            if (!sensor.getBackgroundJob().getName().equals(scriptName))
                sensor.getBackgroundJob().setName(scriptName);
        }
    }

    @Override
    public Boolean isEqual(SensorData sensor, String other) {
        if(sensor.getBackgroundJob().getName().equals(other))
            return true;
        return false;
    }

}
