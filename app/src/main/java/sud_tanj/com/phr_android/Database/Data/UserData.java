/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 6/3/18 9:57 AM
 */

package sud_tanj.com.phr_android.Database.Data;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;
import java.util.HashMap;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Data.DataListener.ParentSensorListener;
import sud_tanj.com.phr_android.Database.Data.DataListener.ParentSensorUserDataListener;
import sud_tanj.com.phr_android.Database.Data.DataListener.TimeStampListener;
import sud_tanj.com.phr_android.Database.Data.DataListener.TimeStampUserDataListener;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.DatabaseSyncable;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.UserDataSynchronizer;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.UserHealthDataSynchronizer;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 03/06/2018 - 9:39.
 * <p>
 * This class last modified by User
 */
public abstract class UserData {
    public static final String USER_DATA_TIMESTAMP="timestamp";
    public static final String USER_DATA_PARENT_SENSOR="Parent_Sensor";
    private String dataId = null;
    private Date timeStamp = null;
    private SensorData parentSensor = null;
    private DatabaseReference dataReference = null;
    private UserHealthDataSynchronizer dataReferenceSynchronizer = null;

    public UserData(String dataId, SensorData parentSensor, String reference) {
        this.timeStamp = new Date();
        this.parentSensor = parentSensor;
        if (dataId.isEmpty()) {
            String newHealthDataId = parentSensor.getSensorInformation().getSensorId() + String.valueOf(this.timeStamp.getTime());
            this.dataId = newHealthDataId;
        } else {
            this.dataId = dataId;
        }
        this.dataReference = Global.getUserDatabase().child(reference).child(this.dataId);

        this.dataReferenceSynchronizer = new UserHealthDataSynchronizer(this.dataReference, this);
        this.dataReferenceSynchronizer.add(new TimeStampUserDataListener(),USER_DATA_TIMESTAMP);
        this.dataReferenceSynchronizer.add(new ParentSensorUserDataListener(),USER_DATA_PARENT_SENSOR);
    }

    protected UserHealthDataSynchronizer getDataReferenceSynchronizer() {
        return dataReferenceSynchronizer;
    }

    public String getDataId() {
        return dataId;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStampWithoutSync(Date timeStamp){
        this.timeStamp=timeStamp;
    }
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
        String timeStampText = String.valueOf(timeStamp.getTime());
       this.dataReferenceSynchronizer.changeVariable(timeStampText);
    }


    public SensorData getParentSensor() {
        return parentSensor;
    }

    public void setParentSensor(SensorData parentSensor) {
        this.parentSensor = parentSensor;
        String parentSensorId = this.parentSensor.getSensorInformation().getSensorId();
        this.dataReferenceSynchronizer.changeVariable(parentSensorId);
    }

    public void add(DatabaseSyncable databaseSyncable, String key) {
        this.dataReferenceSynchronizer.add(databaseSyncable, key);
    }

    protected void syncToFirebase(){
        this.dataReferenceSynchronizer.changeVariable(this.parentSensor.getSensorInformation().getSensorId());
        this.dataReferenceSynchronizer.changeVariable(String.valueOf(this.timeStamp.getTime()));
    }
}
