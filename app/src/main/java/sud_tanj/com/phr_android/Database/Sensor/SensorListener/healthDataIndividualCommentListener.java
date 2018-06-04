/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 6/4/18 9:08 PM
 */

package sud_tanj.com.phr_android.Database.Sensor.SensorListener;

import com.google.firebase.database.DataSnapshot;

import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.SensorSyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 04/06/2018 - 21:08.
 * <p>
 * This class last modified by User
 */
public class healthDataIndividualCommentListener implements SensorSyncable {
    @Override
    public void updateData(SensorData sensor, DataSnapshot dataSnapshot) {
        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
            String analysisId = childSnapshot.getKey();
            if (analysisId.contains(sensor.getSensorInformation().getSensorId()))
                if (!sensor.ishealthDataIndividualComment(analysisId))
                    sensor.addhealthDataIndividualComment(analysisId);
        }
    }

    @Override
    public Boolean isEqual(SensorData sensor, String other) {
        return false;
    }
}

