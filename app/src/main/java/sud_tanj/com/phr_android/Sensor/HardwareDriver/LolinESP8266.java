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

import com.dezlum.codelabs.getjson.GetJson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sud_tanj.com.phr_android.Custom.Global;
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
    private JsonObject jsonObject;
    private Boolean newData=false;
    private GetJson getJson=null;
    private JsonParser jsonParser=null;

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
        if(getJson==null){
            getJson=new GetJson();
        }
        if(jsonParser==null){
            jsonParser=new JsonParser();
        }
        if (getJson.isConnected(Global.getContext())) {
            try {
                jsonObject=jsonParser.parse(getJson.AsString("http://192.168.4.1/getData")).getAsJsonObject();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                if(this.newData) {
                    this.addData(jsonObject.getAsJsonObject("data").get("sensorname").getAsString());
                    this.addData(jsonObject.getAsJsonObject("data").get("value").getAsString());
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
            newData=Boolean.FALSE;
        }
    }
}
