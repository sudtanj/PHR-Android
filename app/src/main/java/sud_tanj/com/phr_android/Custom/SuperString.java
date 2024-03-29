/*
 * Create by Sudono Tanjung
 * Copyright (c) 2017. All rights reserved.
 *
 * Last Modified by User on 12/14/17 6:51 PM
 */

package sud_tanj.com.phr_android.Custom;

import android.support.annotation.NonNull;


/**
 * This class is part of PHRAndroid
 * Created by Sudono Tanjung on 12/12/2017.
 */

public class SuperString implements Comparable {
    private String stringData = null;
    private String translatedString = null;

    public SuperString(String stringData) {
        this.setStringData(stringData);
        this.setTranslatedString("");
    }

    @Override
    public String toString() {
        if (this.getTranslatedString().isEmpty())
            return this.getStringData();
        else
            return this.getTranslatedString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SuperString)) return false;

        SuperString that = (SuperString) o;


        return getStringData().equals(that.getStringData());
    }

    @Override
    public int hashCode() {
        return this.getStringData().hashCode();
    }

    public String getStringData() {
        return stringData;
    }

    public void setStringData(String stringData) {
        this.stringData = stringData;
    }

    public String getTranslatedString() {
        return translatedString;
    }

    public void setTranslatedString(String translatedString) {
        this.translatedString = translatedString;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return this.stringData.compareTo(o.toString());
    }
}
