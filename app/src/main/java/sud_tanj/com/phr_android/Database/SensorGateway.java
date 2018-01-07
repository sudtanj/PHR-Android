/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 1/7/18 11:05 AM
 */

package sud_tanj.com.phr_android.Database;

import java.util.ArrayList;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 07/01/2018 - 11:05.
 * <p>
 * This class last modified by User
 */

public class SensorGateway {
    private ArrayList<SensorData> sensorList = null;

    private ArrayList<SensorData> getSensorList() {
        return sensorList;
    }

    private void setSensorList(ArrayList<SensorData> sensorList) {
        this.sensorList = sensorList;
    }
}
