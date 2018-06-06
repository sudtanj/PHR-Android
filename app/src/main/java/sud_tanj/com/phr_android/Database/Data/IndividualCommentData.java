/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 6/4/18 8:50 PM
 */

package sud_tanj.com.phr_android.Database.Data;

import sud_tanj.com.phr_android.Database.Data.DataListener.CommentDataListener;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 04/06/2018 - 20:50.
 * <p>
 * This class last modified by User
 */
public class IndividualCommentData extends UserData {
    public static final String HEALTH_DATA_COMMENT_CHILD_NAME="Comment_Individual";
    public static final String HEALTH_DATA_COMMENT_DATA_NAME="Data";
    private String comment=null;

    public IndividualCommentData(SensorData parentSensor){
        this("",parentSensor);
    }

    public IndividualCommentData(String dataId, SensorData parentSensor) {
        super(dataId, parentSensor, HEALTH_DATA_COMMENT_CHILD_NAME);
        this.comment=new String();

        this.add(new CommentDataListener(),HEALTH_DATA_COMMENT_DATA_NAME);

    }

    public Boolean isValid(){
        return !this.comment.isEmpty();
    }


    @Override
    protected void syncToFirebase() {
        super.syncToFirebase();
        this.getDataReferenceSynchronizer().changeVariable(this.comment);
    }

    public String getComment() {
        return comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
        this.syncToFirebase();
    }
}
