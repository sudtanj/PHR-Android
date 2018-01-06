/*
 * Create by Sudono Tanjung
 * Copyright (c) 2017. All rights reserved.
 *
 * Last Modified by User on 12/17/17 3:33 PM
 */

package sud_tanj.com.phr_android.Custom;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.securepreferences.SecurePreferences;

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
    public static String DATABASE_USER_CHILD="users";
    private static FirebaseAuth mAuth=null;
    private static FirebaseUser user = null;
    private static SecurePreferences settings=null;
    private static Context context=null;
    private static FirebaseDatabase database = null;
    private static DatabaseReference userDatabase = null;

    public static Context getContext() {
        return context;
    }


    public static FirebaseAuth getFireBaseAuth(){
        if(mAuth==null)
            mAuth=FirebaseAuth.getInstance();
        return mAuth;
    }
    public static FirebaseUser getFireBaseUser(){
        if(user==null)
            user=getFireBaseAuth().getCurrentUser();
        return user;
    }
    public static void setContext(Context conx){
        context=conx;
    }
    public static SecurePreferences getSettings(){
        return settings;
    }
    public static void setSettings(final Context mainActivity, final String settingPath){
        settings=new SecurePreferences(mainActivity, Global.getFireBaseUser().getUid(), settingPath);
    }
    public static void changePreferences(String prefId,String value){
        if(getSettings().getString(prefId,"").isEmpty())
            getSettings().edit().putString(prefId,value).commit();
    }

    public static FirebaseDatabase getDatabase() {
        if(user==null || mAuth==null)
            getFireBaseUser();
        if(database==null)
            database=FirebaseDatabase.getInstance();
        return database;
    }

    public static DatabaseReference getUserDatabase() {
        if(database==null)
            getDatabase();
        if(userDatabase==null)
            userDatabase=getDatabase().getReference().child(DATABASE_USER_CHILD).child(Global.getFireBaseUser().getUid());
        return userDatabase;
    }
}
