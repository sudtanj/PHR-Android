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
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.DatabaseUtility;
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
        GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
        try {
            List<String> value = dataSnapshot.getValue(t);
            if (!healthData.getValues().equals(value))
                healthData.setValues((ArrayList<String>) value);
        } catch (Exception e){
            String value = DatabaseUtility.convertToString(dataSnapshot);
            if(!healthData.getValue().equals(value)){
                ArrayList<String> temp=new ArrayList<>();
                temp.add(value);
                healthData.setValues(temp);
            }
        }

    }

    @Override
    public Boolean isEqual(HealthData healthData, String other) {
        if (healthData.getValues().equals(other))
            return Boolean.TRUE;
        return Boolean.FALSE;
    }
}
