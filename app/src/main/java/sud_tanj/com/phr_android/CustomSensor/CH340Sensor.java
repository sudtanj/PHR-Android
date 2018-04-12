/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 3/30/18 7:25 AM
 */

package sud_tanj.com.phr_android.CustomSensor;

import sud_tanj.com.phr_android.Custom.EmbeddedScript;
import sud_tanj.com.phr_android.Custom.Global;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 30/03/2018 - 7:25.
 * <p>
 * This class last modified by User
 */
public abstract class CH340Sensor implements EmbeddedScript {
    private int baudRate = 9600;
    private byte stopBit = 1;
    private byte dataBit= 8;
    private byte parity= 0;
    private byte flowControl= 0;
    private int retval=0;

    public Boolean openConnection(){
        if(!Global.getCH340Driver().isConnected()) {
            retval = Global.getCH340Driver().ResumeUsbList();
            if (retval == -1)
                Global.getCH340Driver().CloseDevice();
            else if (retval == 0) {
                if (Global.getCH340Driver().UartInit())
                    return true;
                else
                    return false;
            }
        }
        return true;
    }

    public void closeConnection(){
        Global.getCH340Driver().CloseDevice();
    }

    public void setConfig(){
        Global.getCH340Driver().SetConfig(baudRate, dataBit, stopBit, parity,flowControl);
    }

    public String getDataAtCurrent(){
        byte[] buffer = new byte[4096];
        int length = Global.getCH340Driver().ReadData(buffer, 4096);
        if (length > 0) {
            //String recv = toHexString(buffer, length);
            String recv = new String(buffer, 0, length);
            //String recv = String.valueOf(totalrecv);
            return recv;
        }
        return "no message";
    }
}
