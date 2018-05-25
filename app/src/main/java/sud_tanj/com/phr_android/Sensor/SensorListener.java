/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/14/18 3:42 PM
 */

package sud_tanj.com.phr_android.Sensor;

import android.icu.text.Collator;
import android.os.Build;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Sensor.Interface.EmbeddedScript;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 14/05/2018 - 15:42.
 * <p>
 * This class last modified by User
 */
public abstract class SensorListener implements EmbeddedScript {
    private Boolean runOnce = Boolean.FALSE;
    private ArrayList<String> healthData = new ArrayList<>();
    private Boolean openDataStream = Boolean.FALSE;
    private Integer resetCountDownValue=3;

    public Integer getCountDownTimer() {
        return countDownTimer;
    }

    private Integer countDownTimer=resetCountDownValue;

    public SensorData getSensorData() {
        return sensorData;
    }

    public void setSensorData(SensorData sensorData) {
        this.sensorData = sensorData;
    }

    private SensorData sensorData = null;

    @Override
    public void run() {
        if (runOnce) {
            return;
        }
        if (this.isScriptRunOnce()) {
            runOnce = Boolean.TRUE;
        }
        this.syncData();
        this.clearRemainingData();
    }

    public Boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    protected ArrayList<String> getData() {
        ArrayList<String> result = new ArrayList<>();
        if (this.isDataAvailable()) {
            try {
                if(this.healthData.get(0).equals(this.sensorData.getSensorInformation().getSensorId())) {
                    this.healthData.remove(0);
                }
            } catch (Exception e){

            }
            while(!this.healthData.get(0).equals(this.getSensorData().getSensorInformation().getSensorId())){
                result.add(this.healthData.get(0));
                this.healthData.remove(0);
            }
        }
        return result;
    }

    public void clearRemainingData() {
        String result = new String();
        if (this.isDataAvailable()) {
            result = this.healthData.get(0);
            while (!Global.getSensorGateway().isSensorIdExist(result)) {
                result = this.healthData.get(0);
                this.healthData.remove(0);
            }
        }
    }

    public Boolean isDataAvailable() {
        return (this.healthData.size() > 0);
    }

    public void addData(String data) {
        if (Global.getSensorGateway().isSensorIdExist(data)) {
            if (this.sensorData.getSensorInformation().getSensorId().equals(data)) {
                this.openDataStream = Boolean.TRUE;
                //this.healthData.add(this.sensorData.getSensorInformation().getSensorId());
            } else {
                this.openDataStream = Boolean.FALSE;
            }
        }
        if (this.openDataStream) {
            if (this.isAsciiPrintable(data) || this.isNumeric(data)) {
                this.healthData.add(data);
            }
        }
    }

    public void addData(String data, String dataSplitterRegex) {
        String[] temp = data.split(dataSplitterRegex);
        for (int i = 0; i < temp.length; i++) {
            this.addData(temp[i]);
        }
    }

    public void addData(String[] data) {
        ArrayList<String> temp = new ArrayList<>(Arrays.asList(data));
        for (String dataTemp : temp) {
            this.addData(dataTemp);
        }
    }

    private Boolean isAsciiPrintable(String str) {
        if (str == null) {
            return Boolean.FALSE;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (isAsciiPrintable(str.charAt(i)) == false) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    private Boolean isAsciiPrintable(char ch) {
        return ch >= 32 && ch < 127;
    }

    protected void syncData(){
        if(this.countDownTimer==0){
            this.countDownTimer=resetCountDownValue;
        } else {
            this.countDownTimer--;
            return;
        }
    }
}
