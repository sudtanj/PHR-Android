/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/7/18 8:13 PM
 */

package sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase;

import com.google.firebase.database.DataSnapshot;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 07/05/2018 - 20:13.
 * <p>
 * This class last modified by User
 */
public class DatabaseUtility {
    public static String convertToString(DataSnapshot dataSnapshot) {
        return dataSnapshot.getValue(String.class);
    }

    public static Boolean convertToBoolean(DataSnapshot dataSnapshot) {
        String value = dataSnapshot.getValue().toString();
        return Boolean.valueOf(value);
    }

    public static Long convertToLong(DataSnapshot dataSnapshot) {
        return Long.parseLong(dataSnapshot.getValue(String.class));
    }
}
