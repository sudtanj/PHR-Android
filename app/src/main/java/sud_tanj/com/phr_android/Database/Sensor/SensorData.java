/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/6/18 5:39 PM
 */

package sud_tanj.com.phr_android.Database.Sensor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Data.DoctorCommentData;
import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Database.Data.HealthDataAnalysis;
import sud_tanj.com.phr_android.Database.Data.IndividualCommentData;
import sud_tanj.com.phr_android.Database.Sensor.SensorListener.HealthDataAnalysisListener;
import sud_tanj.com.phr_android.Database.Sensor.SensorListener.HealthDataDoctorComment;
import sud_tanj.com.phr_android.Database.Sensor.SensorListener.healthDataIndividualCommentListener;
import sud_tanj.com.phr_android.Database.Sensor.SensorListener.HealthDataListener;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.SensorSynchronizer;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 06/01/2018 - 10:47.
 * <p>
 * This class last modified by User
 */

public class SensorData {
    private SensorInformation sensorInformation = null;
    private ArrayList<String> sensorData = null;
    private SensorEmbeddedScript backgroundJob = null;
    private HealthData latestData = null;
    private HealthDataAnalysis latestDataAnalysis=null;
    private DoctorCommentData doctorCommentData=null;
    private IndividualCommentData individualCommentData=null;
    private ArrayList<String> healthDataAnalysis=null;
    private ArrayList<String> healthDataIndividualComment=null;
    private ArrayList<String> healthDataDoctorComment=null;

    public SensorData(String sensorId) {
        this.sensorInformation = new SensorInformation(sensorId, this);
        this.sensorData = new ArrayList<>();
        this.healthDataAnalysis=new ArrayList<>();
        this.healthDataIndividualComment=new ArrayList<>();
        this.healthDataDoctorComment=new ArrayList<>();
        this.backgroundJob = new SensorEmbeddedScript(new String(), this);

        //firebase sync
        SensorSynchronizer healthDataManager = new SensorSynchronizer(Global.getUserDatabase(), this);
        healthDataManager.add(new HealthDataListener(), "Health_Data");
        healthDataManager.add(new HealthDataAnalysisListener(),"Health_Data_Analysis");
        healthDataManager.add(new healthDataIndividualCommentListener(),"Health_Data_Comment");
    }

    public HealthData getHealthData(String healthId){
        HealthData healthData=new HealthData(healthId,this);
        while(!healthData.isValid());
        return healthData;
    }

    public Boolean ishealthDataIndividualComment(String healthCommentId){
        return this.healthDataIndividualComment.contains(healthCommentId);
    }

    public void addhealthDataIndividualComment(String healthCommentId){
        this.healthDataIndividualComment.add(healthCommentId);
    }


    public Boolean ishealthDataDoctorComment(String healthCommentId){
        return this.healthDataDoctorComment.contains(healthCommentId);
    }

    public void addhealthDataDoctorComment(String healthCommentId){
        this.healthDataDoctorComment.add(healthCommentId);
    }
    public void addHealthDataAnalysis(String healthId){
        healthDataAnalysis.add(healthId);
    }

    public HealthDataAnalysis getLatestHealthDataAnalysis(){
        if (this.latestDataAnalysis == null) {
            if (this.healthDataAnalysis.size() > 0) {
                String healthDataId = this.healthDataAnalysis.get(this.healthDataAnalysis.size() - 1);
                this.latestDataAnalysis = new HealthDataAnalysis(healthDataId, this);
            }
        }
        return this.latestDataAnalysis;
    }


    public IndividualCommentData getLatestIndividualCommentData(){
        if (this.individualCommentData == null) {
            if (this.healthDataIndividualComment.size() > 0) {
                String healthDataId = this.healthDataIndividualComment.get(this.healthDataIndividualComment.size() - 1);
                this.individualCommentData = new IndividualCommentData(healthDataId, this);
            }
        }
        return this.individualCommentData;
    }

    public DoctorCommentData getLatestDoctorCommentData(){
        if (this.doctorCommentData == null) {
            if (this.healthDataDoctorComment.size() > 0) {
                String healthDataId = this.healthDataDoctorComment.get(this.healthDataDoctorComment.size() - 1);
                this.doctorCommentData = new DoctorCommentData(healthDataId, this);
            }
        }
        return this.doctorCommentData;
    }

    public void setTodayIndividualCommentData(String analysis){
        IndividualCommentData healthDataAnalysis=this.getLatestIndividualCommentData();
        if(healthDataAnalysis==null){
            healthDataAnalysis=new IndividualCommentData(this);
            if(!healthDataAnalysis.getComment().equals(analysis)) {
                healthDataAnalysis.setComment(analysis);
            }
            this.individualCommentData=healthDataAnalysis;
        } else {
            Date date=new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy", Locale.US);
            String healthIdDate, targetDate;
            Date tempDate =new Date();
            ArrayList<String> availableDate=this.getAvailableIndividualCommentTimestamp();
            if(availableDate.size()>0) {
                String latestDate = availableDate.get(availableDate.size() - 1);
                tempDate.setTime(Long.valueOf(latestDate));
            }
            targetDate=simpleDateFormat.format(date);
            healthIdDate=simpleDateFormat.format(tempDate);
            if(healthIdDate.equals(targetDate)){
                if(!healthDataAnalysis.getComment().equals(analysis))
                    healthDataAnalysis.setComment(analysis);
            }
            else {
                healthDataAnalysis=new IndividualCommentData(this);
                if(!healthDataAnalysis.getComment().equals(analysis)) {
                    healthDataAnalysis.setComment(analysis);
                }
                this.individualCommentData=healthDataAnalysis;
            }
        }
    }

    public void setTodayDoctorCommentData(String analysis){
        DoctorCommentData healthDataAnalysis=this.getLatestDoctorCommentData();
        if(healthDataAnalysis==null){
            healthDataAnalysis=new DoctorCommentData(this);
            if(!healthDataAnalysis.getComment().equals(analysis)) {
                healthDataAnalysis.setComment(analysis);
            }
            this.doctorCommentData=healthDataAnalysis;
        } else {
            Date date=new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy", Locale.US);
            String healthIdDate, targetDate;
            Date tempDate =new Date();
            ArrayList<String> availableDate=this.getAvailableDoctorCommentTimestamp();
            if(availableDate.size()>0) {
                String latestDate = availableDate.get(availableDate.size() - 1);
                tempDate.setTime(Long.valueOf(latestDate));
            }
            targetDate=simpleDateFormat.format(date);
            healthIdDate=simpleDateFormat.format(tempDate);
            if(healthIdDate.equals(targetDate)){
                if(!healthDataAnalysis.getComment().equals(analysis))
                    healthDataAnalysis.setComment(analysis);
            }
            else {
                healthDataAnalysis=new DoctorCommentData(this);
                if(!healthDataAnalysis.getComment().equals(analysis)) {
                    healthDataAnalysis.setComment(analysis);
                }
                this.doctorCommentData=healthDataAnalysis;
            }
        }
    }

    public void setTodayHealthDataAnalysis(String analysis){
        HealthDataAnalysis healthDataAnalysis=this.getLatestHealthDataAnalysis();
        if(healthDataAnalysis==null){
            healthDataAnalysis=new HealthDataAnalysis(this);
            if(!healthDataAnalysis.getAnalysis().equals(analysis)) {
                healthDataAnalysis.setAnalysis(analysis);
            }
            this.latestDataAnalysis=healthDataAnalysis;
        } else {
            Date date=new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy", Locale.US);
            String healthIdDate, targetDate;
            Date tempDate =new Date();
            ArrayList<String> availableDate=this.getAvailableAnalysisTimestamp();
            if(availableDate.size()>0) {
                String latestDate = availableDate.get(availableDate.size() - 1);
                tempDate.setTime(Long.valueOf(latestDate));
            }
            targetDate=simpleDateFormat.format(date);
            healthIdDate=simpleDateFormat.format(tempDate);
            if(healthIdDate.equals(targetDate)){
                if(!healthDataAnalysis.getAnalysis().equals(analysis))
                    healthDataAnalysis.setAnalysis(analysis);
            }
            else {
                healthDataAnalysis=new HealthDataAnalysis(this);
                if(!healthDataAnalysis.getAnalysis().equals(analysis)) {
                    healthDataAnalysis.setAnalysis(analysis);
                }
                this.latestDataAnalysis=healthDataAnalysis;
            }
        }
    }

    public Boolean isHealthDataAnalysisExist(String value){
        return healthDataAnalysis.contains(value);
    }

    public Boolean isHealthIdExist(String healthId) {
        return this.sensorData.contains(healthId);
    }

    public SensorInformation getSensorInformation() {
        return sensorInformation;
    }

    public ArrayList<String> getSensorData() {
        return sensorData;
    }

    public HealthData getLatestData() {
        if (this.latestData == null) {
            if (this.getSensorData().size() > 0) {
                String healthDataId = this.sensorData.get(this.sensorData.size() - 1);
                this.latestData = new HealthData(healthDataId, this);
            }
        }
        return this.latestData;
    }

    public void setLatestData(HealthData latestData) {
        this.latestData = latestData;
    }

    public void addHealthData(String values) {
        this.getSensorData().add(values);
    }

    public Boolean isReady() {
        if (this.sensorData != null)
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

    public void deleteData(HealthData healthData) {
        this.sensorData.remove(healthData);
    }

    public void resetHealthData() {
        this.sensorData = new ArrayList<>();
    }

    public SensorEmbeddedScript getBackgroundJob() {
        return backgroundJob;
    }

    public void setBackgroundJob(SensorEmbeddedScript backgroundJob) {
        this.backgroundJob = backgroundJob;
    }

    public ArrayList<String> getAvailableTimestamp() {
        ArrayList<String> temp = new ArrayList<>();
        Collections.sort(this.sensorData);
        for (String healthId : this.sensorData) {
            String tempHealthId = healthId.replace(this.getSensorInformation().getSensorId(), "");
            temp.add(tempHealthId);
        }
        return temp;
    }

    public ArrayList<String> getAvailableIndividualCommentTimestamp() {
        ArrayList<String> temp = new ArrayList<>();
        Collections.sort(this.healthDataIndividualComment);
        for (String healthId : this.healthDataIndividualComment) {
            String tempHealthId = healthId.replace(this.getSensorInformation().getSensorId(), "");
            temp.add(tempHealthId);
        }
        return temp;
    }

    public ArrayList<String> getAvailableDoctorCommentTimestamp() {
        ArrayList<String> temp = new ArrayList<>();
        Collections.sort(this.healthDataDoctorComment);
        for (String healthId : this.healthDataDoctorComment) {
            String tempHealthId = healthId.replace(this.getSensorInformation().getSensorId(), "");
            temp.add(tempHealthId);
        }
        return temp;
    }

    public ArrayList<String> getAvailableAnalysisTimestamp() {
        ArrayList<String> temp = new ArrayList<>();
        for (String healthId : this.healthDataAnalysis) {
            String tempHealthId = healthId.replace(this.getSensorInformation().getSensorId(), "");
            temp.add(tempHealthId);
        }
        return temp;
    }

    public ArrayList<String> getAvailableTimeOn(Date date) {
        ArrayList<String> availableTimeTemp = new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy", Locale.US),
                hourDateFormat = new SimpleDateFormat("HH", Locale.US);
        simpleDateFormat.format(date);
        String healthIdDate, targetDate;
        Date tempDate;
        for (String healthIdTime : this.getAvailableTimestamp()) {
            tempDate = new Date();
            tempDate.setTime(Long.parseLong(healthIdTime));
            healthIdDate = simpleDateFormat.format(tempDate);
            targetDate = simpleDateFormat.format(date);
            if (targetDate.equals(healthIdDate)) {
                availableTimeTemp.add(hourDateFormat.format(tempDate));
            }
        }
        return availableTimeTemp;
    }


    public ArrayList<String> getAvailableDayOn(Date date) {
        ArrayList<String> availableTimeTemp = new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM yyyy", Locale.US),
                hourDateFormat = new SimpleDateFormat("dd", Locale.US);
        simpleDateFormat.format(date);
        String healthIdDate, targetDate;
        Date tempDate;
        for (String healthIdTime : this.getAvailableTimestamp()) {
            tempDate = new Date();
            tempDate.setTime(Long.parseLong(healthIdTime));
            healthIdDate = simpleDateFormat.format(tempDate);
            targetDate = simpleDateFormat.format(date);
            if (targetDate.equals(healthIdDate)) {
                availableTimeTemp.add(hourDateFormat.format(tempDate));
            }
        }
        return availableTimeTemp;
    }

    public ArrayList<String> getAvailableMonthOn(Date date) {
        ArrayList<String> availableTimeTemp = new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy", Locale.US),
                hourDateFormat = new SimpleDateFormat("MM", Locale.US);
        simpleDateFormat.format(date);
        String healthIdDate, targetDate;
        Date tempDate;
        for (String healthIdTime : this.getAvailableTimestamp()) {
            tempDate = new Date();
            tempDate.setTime(Long.parseLong(healthIdTime));
            healthIdDate = simpleDateFormat.format(tempDate);
            targetDate = simpleDateFormat.format(date);
            if (targetDate.equals(healthIdDate)) {
                availableTimeTemp.add(hourDateFormat.format(tempDate));
            }
        }
        return availableTimeTemp;
    }

    public ArrayList<HealthData> getHealthDataOnYear(Date date){
        ArrayList<HealthData> healthDataTemp = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy", Locale.US);
        simpleDateFormat.format(date);
        String healthIdDate, targetDate,prevDate=new String();
        for (String healthIdTime : this.getAvailableTimestamp()) {
            Date tempDate = new Date();
            tempDate.setTime(Long.parseLong(healthIdTime));
            healthIdDate = simpleDateFormat.format(tempDate);
            targetDate = simpleDateFormat.format(date);
            if (targetDate.equals(healthIdDate)) {
                healthDataTemp.add(new HealthData(new String(this.getSensorInformation().getSensorId() + healthIdTime), this));
            }
        }
        return healthDataTemp;
    }

    public ArrayList<HealthData> getHealthDataOnMonth(Date date){
        ArrayList<HealthData> healthDataTemp = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM yyyy", Locale.US);
        simpleDateFormat.format(date);
        String healthIdDate, targetDate;
        for (String healthIdTime : this.getAvailableTimestamp()) {
            Date tempDate = new Date();
            tempDate.setTime(Long.parseLong(healthIdTime));
            healthIdDate = simpleDateFormat.format(tempDate);
            targetDate = simpleDateFormat.format(date);
            if (targetDate.equals(healthIdDate)) {
                healthDataTemp.add(new HealthData(new String(this.getSensorInformation().getSensorId() + healthIdTime), this));
            }
        }
        return healthDataTemp;
    }

    public ArrayList<HealthData> getHealthDataOn(Date date) {
        ArrayList<HealthData> healthDataTemp = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy", Locale.US);
        simpleDateFormat.format(date);
        String healthIdDate, targetDate;
        for (String healthIdTime : this.getAvailableTimestamp()) {
            Date tempDate = new Date();
            tempDate.setTime(Long.parseLong(healthIdTime));
            healthIdDate = simpleDateFormat.format(tempDate);
            targetDate = simpleDateFormat.format(date);
            if (targetDate.equals(healthIdDate)) {
                healthDataTemp.add(new HealthData(new String(this.getSensorInformation().getSensorId() + healthIdTime), this));
            }
        }
        return healthDataTemp;
    }

    public ArrayList<String> getHealthDataIndividualCommentOn(Date date) {
        ArrayList<String> healthDataTemp = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy", Locale.US);
        simpleDateFormat.format(date);
        String healthIdDate, targetDate;
        for (String healthIdTime : this.getAvailableIndividualCommentTimestamp()) {
            Date tempDate = new Date();
            tempDate.setTime(Long.parseLong(healthIdTime));
            healthIdDate = simpleDateFormat.format(tempDate);
            targetDate = simpleDateFormat.format(date);
            if (targetDate.equals(healthIdDate)) {
                healthDataTemp.add(new String(this.getSensorInformation().getSensorId() + healthIdTime));
            }
        }
        return healthDataTemp;
    }

    public ArrayList<HealthDataAnalysis> getHealthDataAnalysisOn(Date date) {
        ArrayList<HealthDataAnalysis> healthDataTemp = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy", Locale.US);
        simpleDateFormat.format(date);
        String healthIdDate, targetDate;
        for (String healthIdTime : this.getAvailableAnalysisTimestamp()) {
            Date tempDate = new Date();
            tempDate.setTime(Long.parseLong(healthIdTime));
            healthIdDate = simpleDateFormat.format(tempDate);
            targetDate = simpleDateFormat.format(date);
            if (targetDate.equals(healthIdDate)) {
                healthDataTemp.add(new HealthDataAnalysis(new String(this.getSensorInformation().getSensorId() + healthIdTime), this));
            }
        }
        return healthDataTemp;
    }

    public ArrayList<HealthDataAnalysis> getHealthDataAnalysisOnMonth(Date date) {
        ArrayList<HealthDataAnalysis> healthDataTemp = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM yyyy", Locale.US);
        simpleDateFormat.format(date);
        String healthIdDate, targetDate;
        for (String healthIdTime : this.getAvailableAnalysisTimestamp()) {
            Date tempDate = new Date();
            tempDate.setTime(Long.parseLong(healthIdTime));
            healthIdDate = simpleDateFormat.format(tempDate);
            targetDate = simpleDateFormat.format(date);
            if (targetDate.equals(healthIdDate)) {
                healthDataTemp.add(new HealthDataAnalysis(new String(this.getSensorInformation().getSensorId() + healthIdTime), this));
            }
        }
        return healthDataTemp;
    }

    public ArrayList<HealthDataAnalysis> getHealthDataAnalysisOnYear(Date date) {
        ArrayList<HealthDataAnalysis> healthDataTemp = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM yyyy", Locale.US);
        simpleDateFormat.format(date);
        String healthIdDate, targetDate;
        for (String healthIdTime : this.getAvailableAnalysisTimestamp()) {
            Date tempDate = new Date();
            tempDate.setTime(Long.parseLong(healthIdTime));
            healthIdDate = simpleDateFormat.format(tempDate);
            targetDate = simpleDateFormat.format(date);
            if (targetDate.equals(healthIdDate)) {
                healthDataTemp.add(new HealthDataAnalysis(new String(this.getSensorInformation().getSensorId() + healthIdTime), this));
            }
        }
        return healthDataTemp;
    }

    public ArrayList<HealthData> getHealthDataBetween(Date start, Date end) {
        ArrayList<HealthData> healthDataTemp = new ArrayList<>();
        for (String healthIdTime : this.getAvailableTimestamp()) {
            Date healthDataTimestamp = new Date();
            healthDataTimestamp.setTime(Long.parseLong(healthIdTime));
            if (healthDataTimestamp.after(start) && healthDataTimestamp.before(end)) {
                healthDataTemp.add(new HealthData(new String(this.getSensorInformation().getSensorId() + healthIdTime), this));
            }
        }
        return healthDataTemp;
    }

    public void delete() {
        Global.getSensorGateway().deleteSensorObject(this);
        this.getSensorInformation().getDataReference().removeValue();
    }


}
