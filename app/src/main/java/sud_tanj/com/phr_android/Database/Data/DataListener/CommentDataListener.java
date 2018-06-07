/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 6/4/18 9:05 PM
 */

package sud_tanj.com.phr_android.Database.Data.DataListener;

import com.google.firebase.database.DataSnapshot;

import sud_tanj.com.phr_android.Database.Data.IndividualCommentData;
import sud_tanj.com.phr_android.Database.Data.UserData;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.DatabaseUtility;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.UserHealthDataSyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 04/06/2018 - 21:05.
 * <p>
 * This class last modified by User
 */
public class CommentDataListener implements UserHealthDataSyncable {
    @Override
    public void updateData(UserData userData, DataSnapshot dataSnapshot) {
        IndividualCommentData healthDataAnalysis=((IndividualCommentData)userData);
        String analysis=healthDataAnalysis.getComment();
        String value = DatabaseUtility.convertToString(dataSnapshot);
        if(!analysis.equals(value)){
            healthDataAnalysis.setCommentWithoutSync(value);
        }
    }

    @Override
    public Boolean isEqual(UserData userData, String other) {
        IndividualCommentData healthDataAnalysis=((IndividualCommentData)userData);
        return healthDataAnalysis.getComment().equals(other);
    }
}
