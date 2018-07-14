/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 7/14/18 10:39 AM
 */

package sud_tanj.com.phr_android.Custom;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 14/07/2018 - 10:39.
 * <p>
 * This class last modified by User
 */
public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParser() {

    }

    public JSONObject getJSONFromUrl(String urlText) {

        // Making HTTP request
        jObj=null;
        URL url;
        String responseJSON="";
        StringBuffer response = new StringBuffer();
        try {
            url = new URL(urlText);
        } catch (MalformedURLException e) {
            return jObj;
            //throw new IllegalArgumentException("invalid url");
        }

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(false);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
                return jObj;
                //throw new IOException("Post failed with error code " + status);
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
        } catch (Exception e) {
            return jObj;
            //e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

            //Here is your json in string format
            responseJSON = response.toString();
        }

        try {
            jObj=new JSONObject(responseJSON);
        } catch (JSONException e) {
            //e.printStackTrace();
        }

        // return JSON String
        return jObj;

    }

}