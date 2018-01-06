/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 1/6/18 11:28 AM
 */

package sud_tanj.com.phr_android.Custom;

import android.support.annotation.NonNull;

import org.encryptor4j.util.TextEncryptor;

import java.security.GeneralSecurityException;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 06/01/2018 - 11:28.
 * <p>
 * This class last modified by User
 */

public class EncryptedString implements Comparable{
    private String stringData=null;
    private TextEncryptor te = null;
    @Override
    public String toString() {
        return this.getStringData();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EncryptedString)) return false;

        EncryptedString that = (EncryptedString) o;


        return getStringData().equals(that.getStringData());
    }

    @Override
    public int hashCode() {
        return this.getStringData().hashCode();
    }

    public EncryptedString(String stringData){
       // te = new TextEncryptor(Global.getFireBaseUser().getUid());
       // try {
          //  this.setStringData(te.encrypt(stringData));
        this.setStringData(stringData);
        //} catch (GeneralSecurityException e) {
         //   e.printStackTrace();
        //}
    }

    public void setStringData(String stringData) {
        this.stringData = stringData;
    }

    public String getStringData() {
        //try {
            return stringData;
       // } catch (GeneralSecurityException e) {
         //   return e.toString();
        //}
    }


    @Override
    public int compareTo(@NonNull Object o) {
        return this.stringData.compareTo(o.toString());
    }
}
