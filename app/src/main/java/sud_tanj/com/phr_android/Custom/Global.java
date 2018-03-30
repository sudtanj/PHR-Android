/*
 * Create by Sudono Tanjung
 * Copyright (c) 2017. All rights reserved.
 *
 * Last Modified by User on 12/17/17 3:33 PM
 */

package sud_tanj.com.phr_android.Custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.usb.UsbManager;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import cn.wch.ch34xuartdriver.CH34xUARTDriver;
import sud_tanj.com.phr_android.Database.SensorGateway;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 17/12/2017 - 15:33.
 * <p>
 * This class last modified by User
 */

public class Global {
    public static String DATABASE_USER_CHILD = "users";
    private static FirebaseAuth mAuth = null;
    private static FirebaseUser user = null;
    private static SharedPreferences settings = null;
    private static Context context = null;
    private static FirebaseDatabase database = null;
    private static DatabaseReference userDatabase = null;
    private static DatabaseReference mainDatabase = null;
    private static NavigationView navigationView = null;
    private static SensorGateway sensorGateway = null;
    private static FloatingActionButton floatingButton = null;
    private static CH34xUARTDriver driver=null;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context conx) {
        context = conx;
    }

    public static FirebaseAuth getFireBaseAuth() {
        if (mAuth == null)
            mAuth = FirebaseAuth.getInstance();
        return mAuth;
    }

    public static FirebaseUser getFireBaseUser() {
        if (user == null)
            user = getFireBaseAuth().getCurrentUser();
        return user;
    }

    public static SharedPreferences getSettings() {
        return settings;
    }

    public static void setSettings(final Context mainActivity) {
        settings = PreferenceManager.getDefaultSharedPreferences(mainActivity);
    }

    public static void changePreferences(String prefId, String value) {
        if (getSettings().getString(prefId, "").isEmpty())
            getSettings().edit().putString(prefId, value).commit();
    }

    public static FirebaseDatabase getDatabase() {
        if (user == null || mAuth == null)
            getFireBaseUser();
        if (database == null) {
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }
        return database;
    }

    public static DatabaseReference getUserDatabase() {
        if (userDatabase == null)
            userDatabase = getMainDatabase().child(DATABASE_USER_CHILD).child(Global.getFireBaseUser().getUid());
        return userDatabase;
    }

    public static NavigationView getNavigationView() {
        return navigationView;
    }

    public static void setNavigationView(NavigationView navigationView) {
        Global.navigationView = navigationView;
    }

    public static DatabaseReference getMainDatabase() {
        if (database == null)
            getDatabase();
        if (mainDatabase == null) {
            mainDatabase = getDatabase().getReference();
            mainDatabase.keepSynced(true);
        }
        return mainDatabase;
    }

    public static void setMainDatabase(DatabaseReference mainDatabase) {
        Global.mainDatabase = mainDatabase;
    }

    public static SensorGateway getSensorGateway() {
        return sensorGateway;
    }

    public static void setSensorGateway(SensorGateway sensorGateway) {
        Global.sensorGateway = sensorGateway;
    }

    public static FloatingActionButton getFloatingButton() {
        return floatingButton;
    }

    public static void setFloatingButton(FloatingActionButton floatingButton) {
        Global.floatingButton = floatingButton;
    }

    public static CH34xUARTDriver getCH340Driver() {
        if(driver==null)
            driver=new CH34xUARTDriver(
                    (UsbManager) getContext().getSystemService(Context.USB_SERVICE), getContext(),
                    "cn.wch.wchusbdriver.USB_PERMISSION");
        if(driver.UsbFeatureSupported())
            return driver;
        else
            return null;
    }
}
