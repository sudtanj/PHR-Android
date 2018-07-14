/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 7/14/18 12:45 PM
 */

package sud_tanj.com.phr_android.Sensor.HardwareDriver;

import android.os.AsyncTask;

import org.json.JSONObject;

import sud_tanj.com.phr_android.Custom.JSONParser;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 14/07/2018 - 12:45.
 * <p>
 * This class last modified by User
 */
public class LolinNetworkAsyncTask extends AsyncTask<Void,Void,Void> {
    private LolinESP8266 lolinESP8266=null;
    private JSONParser jsonParser;
    private JSONObject jsonObject;

    public LolinNetworkAsyncTask(LolinESP8266 lolinESP8266) {
        this.lolinESP8266=lolinESP8266;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while(this.lolinESP8266.getmWifi().isConnected() || !isCancelled()) {
            jsonParser = new JSONParser();
            jsonObject = jsonParser.getJSONFromUrl("http://192.168.4.1/getData");
            this.lolinESP8266.setJsonObject(jsonObject);
        }
        return null;
    }
}
