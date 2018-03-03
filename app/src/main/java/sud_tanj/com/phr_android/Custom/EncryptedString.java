/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 1/6/18 11:28 AM
 */

package sud_tanj.com.phr_android.Custom;

import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sud_tanj.com.aes_library.CryptLib;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 06/01/2018 - 11:28.
 * <p>
 * This class last modified by User
 */

public class EncryptedString implements Comparable {
    private String stringData = null;
    private CryptLib cryptLib = null;

    public EncryptedString(String stringData, Boolean encrypted) {

            try {
                if (!encrypted) {
                    this.setStringData(this.getCryptLib().encryptSimple(stringData, Global.getFireBaseUser().getUid(), "1234"));
                }
                else {
                    this.setStringData(this.getCryptLib().decryptSimple(stringData, Global.getFireBaseUser().getUid(), "1234"));
                }
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

    }

    public CryptLib getCryptLib() {
        if(this.cryptLib==null){
            try {
                this.cryptLib=new CryptLib();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }
        }
        return cryptLib;
    }

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

    private String getStringData() {
        return this.stringData;
    }

    public void setStringData(String stringData) {
        this.stringData = stringData;
    }

    public String getEncryptedText() {
        return this.getStringData();
    }

    public String getDecryptedText() {
        try {
            return this.getCryptLib().decryptSimple(stringData, Global.getFireBaseUser().getUid(), "1234");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    public int compareTo(@NonNull Object o) {
        return this.stringData.compareTo(o.toString());
    }
}
