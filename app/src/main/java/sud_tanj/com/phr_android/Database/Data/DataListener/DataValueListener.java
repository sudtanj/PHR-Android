/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/8/18 9:35 AM
 */

package sud_tanj.com.phr_android.Database.Data.DataListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.List;

import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.HealthDataSyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 08/05/2018 - 9:35.
 * <p>
 * This class last modified by User
 */
public class DataValueListener implements HealthDataSyncable {
    @Override
    public void updateData(HealthData healthData, DataSnapshot dataSnapshot) {
        GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
        };
        List<String> value = dataSnapshot.getValue(t);
        if (!healthData.getValues().toString().equals(value.toString()))
            healthData.setValues((ArrayList<String>) value);

    }

    @Override
    public Boolean isEqual(HealthData healthData, String other) {
        return healthData.getValues().toString().equals(other);
    }
}
