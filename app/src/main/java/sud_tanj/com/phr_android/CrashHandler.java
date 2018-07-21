/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 7/15/18 9:45 AM
 */

package sud_tanj.com.phr_android;

import android.app.Activity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.emredavarci.noty.Noty;
import com.osama.firecrasher.CrashListener;

import sud_tanj.com.phr_android.Custom.Global;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 15/07/2018 - 9:45.
 * <p>
 * This class last modified by User
 */
public class CrashHandler extends CrashListener {
    @Override
    public void onCrash(Throwable throwable, Activity activity) {
        // show your own message
        FrameLayout fragmentLayout=(FrameLayout) ((Activity)Global.getContext()).findViewById(R.id.fragment_container);
        Noty.init(Global.getContext(),throwable.getMessage(),fragmentLayout,Noty.WarningStyle.ACTION);
        //Toast.makeText(activity, throwable.getMessage(), Toast.LENGTH_SHORT).show();

        // start the recovering process
        recover(activity);

        //Send to Fabric Crashlytics
        Crashlytics.logException(throwable);
    }
}
