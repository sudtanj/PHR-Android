/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 6/4/18 9:38 PM
 */

package sud_tanj.com.phr_android.Database.Data;

import sud_tanj.com.phr_android.Database.Data.DataListener.CommentDataListener;
import sud_tanj.com.phr_android.Database.Data.DataListener.DoctorCommentDataListener;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 04/06/2018 - 21:38.
 * <p>
 * This class last modified by User
 */
public class DoctorCommentData extends UserData {
    public static final String HEALTH_DATA_COMMENT_CHILD_NAME="Comment_Doctor";
    public static final String HEALTH_DATA_COMMENT_DATA_NAME="Data";
    private String comment=null;

    public DoctorCommentData(SensorData parentSensor){
        this("",parentSensor);
    }

    public DoctorCommentData(String dataId, SensorData parentSensor) {
        super(dataId, parentSensor, HEALTH_DATA_COMMENT_CHILD_NAME);
        this.comment=new String();

        this.add(new DoctorCommentDataListener(),HEALTH_DATA_COMMENT_DATA_NAME);

    }


    @Override
    protected Boolean isValid() {
        return !this.comment.isEmpty();
    }

    @Override
    protected void addNewDataToParentSensor(String healthId) {
        this.getParentSensor().addhealthDataDoctorComment(healthId);
    }

    @Override
    protected void syncToFirebase() {
        super.syncToFirebase();
        this.getDataReferenceSynchronizer().changeVariable(this.comment);
    }

    public String getComment() {
        return comment;
    }

    public void setCommentWithoutSync(String comment){
        this.comment = comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
        this.syncToFirebase();
    }
}
