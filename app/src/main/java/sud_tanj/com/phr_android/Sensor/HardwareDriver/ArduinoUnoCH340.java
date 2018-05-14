/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/6/18 5:49 PM
 */

package sud_tanj.com.phr_android.Sensor.HardwareDriver;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Sensor.Interface.EmbeddedScript;
import sud_tanj.com.phr_android.Sensor.Sensor;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 30/03/2018 - 7:25.
 * <p>
 * This class last modified by User
 */
public abstract class ArduinoUnoCH340 extends Sensor {
    private int baudRate = 9600;
    private byte stopBit = 1;
    private byte dataBit = 8;
    private byte parity = 0;
    private byte flowControl = 0;
    private Boolean configSet=Boolean.FALSE;

    public void setAsDefaultConfig(){
        setConfig(baudRate, dataBit, stopBit, parity, flowControl);
    }

    public void setConfig(Integer baudRate,Byte dataBit,Byte stopBit,Byte parity,Byte flowControl) {
        Global.getCH340Driver().SetConfig(baudRate, dataBit, stopBit, parity, flowControl);
    }

    public String getLatestDataFromArduino() {
        byte[] buffer = new byte[4096];
        int length = Global.getCH340Driver().ReadData(buffer, 4096);
        if (length > 0) {
            //String recv = toHexString(buffer, length);
            String recv = new String(buffer, 0, length);
            //String recv = String.valueOf(totalrecv);
            String[] temp = recv.split("\n");
            String result = "";
            if (temp.length > 0)
                result = temp[temp.length - 1];
            if (this.isNumeric(result)){
                return result;
            }
            return "";
        }
        return new String("");
    }

    public Boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public abstract void postDataReceived(String data);

    @Override
    public void run() {
        super.run();
        try {
            if (!this.configSet) {
                this.setAsDefaultConfig();
                this.configSet = Boolean.TRUE;
            }
        } catch (Exception e){

        }
        if (Global.getCH340Driver() != null) {
            if (Global.getCH340Driver().isConnected()) {
                String result=this.getLatestDataFromArduino();
                if(!result.isEmpty())
                    this.postDataReceived(result);
            }
        }
    }
}
