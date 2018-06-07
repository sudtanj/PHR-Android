/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 6/3/18 12:30 PM
 */

package sud_tanj.com.phr_android.Database.Data.DataListener;

import com.google.firebase.database.DataSnapshot;

import sud_tanj.com.phr_android.Database.Data.HealthDataAnalysis;
import sud_tanj.com.phr_android.Database.Data.UserData;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.DatabaseUtility;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.UserDataSyncable;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.UserHealthDataSyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 03/06/2018 - 12:30.
 * <p>
 * This class last modified by User
 */
public class AnalysisListener implements UserHealthDataSyncable {
    @Override
    public void updateData(UserData userData, DataSnapshot dataSnapshot) {
        HealthDataAnalysis healthDataAnalysis=((HealthDataAnalysis)userData);
        String analysis=healthDataAnalysis.getAnalysis();
        String value = DatabaseUtility.convertToString(dataSnapshot);
        if(!analysis.equals(value)){
            healthDataAnalysis.setAnalysisWithoutSync(value);
        }
    }

    @Override
    public Boolean isEqual(UserData userData, String other) {
        HealthDataAnalysis healthDataAnalysis=((HealthDataAnalysis)userData);
        return healthDataAnalysis.getAnalysis().equals(other);
    }
}
