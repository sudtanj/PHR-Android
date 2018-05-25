/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/13/18 4:21 PM
 */

package sud_tanj.com.phr_android.Sensor.HardwareDriver.Interface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;

import sud_tanj.com.phr_android.Custom.Global;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 13/05/2018 - 16:21.
 * <p>
 * This class last modified by User
 */
public class ArduinoReceiver extends BroadcastReceiver {
    private int retval = 0;
    private UsbManager usbManager=(UsbManager)Global.getContext().getSystemService("usb");

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Global.getCH340Driver()!=null) {
            if (!this.usbManager.getDeviceList().isEmpty()) {
                this.openConnection();
            } else {
                this.closeConnection();
            }
        }
    }

    public void openConnection() {
        if (!Global.getCH340Driver().isConnected()) {
            retval = Global.getCH340Driver().ResumeUsbList();
            if (retval == -1)
                Global.getCH340Driver().CloseDevice();
        }
    }

    public void closeConnection() {
        if (Global.getCH340Driver().isConnected()) {
            retval = Global.getCH340Driver().ResumeUsbList();
            if (retval == -1)
                Global.getCH340Driver().CloseDevice();
            else if (retval == 0) {
                Global.getCH340Driver().UartInit();
            }
        }
    }
}
