/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 1/6/18 11:28 AM
 */

package sud_tanj.com.phr_android.Custom;

import android.support.annotation.NonNull;

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
    private String salt=null;
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

    public EncryptedString(String stringData,Boolean encrypted){
        //textEncryptor = new StrongTextEncryptor();
        //textEncryptor.setPassword(Global.getFireBaseUser().getUid());
        //if(encrypted)
            this.setStringData(stringData);
        //else
            //this.setStringData(textEncryptor.encrypt(stringData));
    }


    public void setStringData(String stringData) {
        this.stringData = stringData;
    }

    public String getStringData() {
        return this.stringData;
    }

    public String getEncryptedText(){
        return this.stringData;
    }

    public String getDecryptedText(){
        //return textEncryptor.decrypt(this.getStringData());
        return this.getStringData();
    }


    @Override
    public int compareTo(@NonNull Object o) {
        return this.stringData.compareTo(o.toString());
    }
}
