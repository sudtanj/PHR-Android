/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 7/14/18 10:38 AM
 */

package sud_tanj.com.phr_android.Sensor.HardwareDriver;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Custom.JSONParser;
import sud_tanj.com.phr_android.Sensor.SensorListener;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 14/07/2018 - 10:38.
 * <p>
 * This class last modified by User
 */
public abstract class LolinESP8266 extends SensorListener {
    private ConnectivityManager connManager;
    private NetworkInfo mWifi;
    private JSONParser jsonParser;
    private JSONObject jsonObject;
    private Boolean newData=false;
    private LolinNetworkAsyncTask lolinNetworkAsyncTask=null;

    public NetworkInfo getmWifi() {
        return mWifi;
    }

    public void postDataReceived(ArrayList<String> receivedDataInOneLoop){
        if(this.getCountDownTimer()!=0){
            return;
        }
    }

    @Override
    public void run() {
        super.run();
        ArrayList<String> dataReceivedInOneLoop=this.getData();
        if(dataReceivedInOneLoop.size()>0) {
            this.postDataReceived(dataReceivedInOneLoop);
        }
    }

    @Override
    protected void syncData() {
        super.syncData();
        connManager = (ConnectivityManager) Global.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            // Do whatever
           // jsonParser=new JSONParser();
           // jsonObject=jsonParser.getJSONFromUrl("http://192.168.4.1/getData");
            if(lolinNetworkAsyncTask==null) {
                lolinNetworkAsyncTask = new LolinNetworkAsyncTask(this);
                lolinNetworkAsyncTask.execute();
            }
            try {
                System.out.println(jsonObject);
                if(this.newData) {
                    this.addData(jsonObject.getJSONObject("data").getString("sensorname"));
                    System.out.println(jsonObject.getJSONObject("data").getString("sensorname"));
                    this.addData(jsonObject.getJSONObject("data").getString("value"), ",");
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
            newData=Boolean.FALSE;
        }
        else {
            lolinNetworkAsyncTask.cancel(Boolean.TRUE);
            lolinNetworkAsyncTask=null;
        }
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        this.newData=Boolean.TRUE;
    }
}
