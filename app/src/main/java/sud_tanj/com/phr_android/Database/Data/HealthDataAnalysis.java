/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 6/3/18 9:37 AM
 */

package sud_tanj.com.phr_android.Database.Data;

import sud_tanj.com.phr_android.Database.Data.DataListener.AnalysisListener;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 03/06/2018 - 9:37.
 * <p>
 * This class last modified by User
 */
public class HealthDataAnalysis extends UserData{
    public static final String HEALTH_DATA_ANALYSIS_NAME = "Health_Data_Analysis";
    public static final String HEALTH_DATA_ANALYSIS_DATA_NAME="Data";
    private String analysis;

    public HealthDataAnalysis(SensorData parentSensor) {
        this("",parentSensor);
    }

    public HealthDataAnalysis(String dataId, SensorData parentSensor) {
        super(dataId, parentSensor, HEALTH_DATA_ANALYSIS_NAME);
        this.analysis=new String();
        this.add(new AnalysisListener(),HEALTH_DATA_ANALYSIS_DATA_NAME);
        if(dataId.isEmpty())
            this.getParentSensor().addHealthDataAnalysis(this.getDataId());

    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
        this.syncToFirebase();
    }

    @Override
    protected void syncToFirebase() {
        super.syncToFirebase();
        this.getDataReferenceSynchronizer().changeVariable(analysis);
    }
}
