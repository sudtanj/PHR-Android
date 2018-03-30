/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 3/30/18 8:16 AM
 */

package sud_tanj.com.phr_android.CustomSensor;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.HealthData;
import sud_tanj.com.phr_android.Database.SensorData;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 30/03/2018 - 8:16.
 * <p>
 * This class last modified by User
 */
public class newsensoractivity extends CH340Sensor {
    private Boolean firstTime=true;
    private SensorData sensor=Global.getSensorGateway().getSensorData("NewSensor148");
    private int baudRate = 9600;
    private byte stopBit = 1;
    private byte dataBit= 8;
    private byte parity= 0;
    private byte flowControl= 0;

    @Override
    public Boolean run() {
        if(Global.getCH340Driver()!=null) {
                if (firstTime) {
                    this.openConnection();
                    firstTime = false;
                }
            if(Global.getCH340Driver().isConnected()) {
                Global.getCH340Driver().SetConfig(baudRate, dataBit, stopBit, parity,flowControl);
                //sensor.addHealthData(new HealthData(sensor, this.getDataAtCurrent()));
                String[] temp=this.getDataAtCurrent().split("\n");
                String result="";
                if(temp.length>0)
                result=temp[temp.length-1];
                if(this.isNumeric(result)){
                    System.out.println(result);
                    sensor.addHealthData(new HealthData(sensor, result));
                }

            }
        }
        return true;
    }

    public boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    @Override
    public String getSensorId() {
        return null;
    }
}
