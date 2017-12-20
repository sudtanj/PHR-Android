/*
 * Create by Sudono Tanjung
 * Copyright (c) 2017. All rights reserved.
 *
 * Last Modified by User on 12/20/17 4:49 PM
 */

package sud_tanj.com.phr_android.CardLayout;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 20/12/2017 - 16:49.
 * <p>
 * This class last modified by User
 */

public class DataObject {
    private String mText1;
    private String mText2;

    DataObject (String text1, String text2){
        mText1 = text1;
        mText2 = text2;
    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }
}