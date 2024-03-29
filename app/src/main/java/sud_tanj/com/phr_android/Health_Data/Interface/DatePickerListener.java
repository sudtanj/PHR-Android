/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/11/18 9:16 PM
 */

package sud_tanj.com.phr_android.Health_Data.Interface;

import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.util.Calendar;

import sud_tanj.com.phr_android.Health_Data.HealthDataList;
import sud_tanj.com.phr_android.R;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 11/05/2018 - 21:16.
 * <p>
 * This class last modified by User
 */
public class DatePickerListener implements View.OnClickListener {
    private Context healthDataContext;
    private DatePickerDataChangeListener dateSetListener;
    private SpinnerDatePickerDialogBuilder spinnerDatePickerDialogBuilder;
    private DatePickerDialog datePickerDialog;
    private Boolean firstTime;
    private DatePicker datePicker;

    public DatePickerListener(Context healthDataContext, DatePickerDataChangeListener dateSetListener) {
        this.healthDataContext = healthDataContext;
        this.dateSetListener=dateSetListener;
        this.spinnerDatePickerDialogBuilder=new SpinnerDatePickerDialogBuilder();
        this.firstTime=Boolean.TRUE;
        this.datePicker = (DatePicker) ((HealthDataList)this.healthDataContext).findViewById(R.id.date_picker);

    }

    public DatePickerDataChangeListener getDateSetListener() {
        return dateSetListener;
    }

    @Override
    public void onClick(View view) {
        Calendar calendar= Calendar.getInstance();
        Integer year=calendar.get(Calendar.YEAR);
        Integer monthOfYear=calendar.get(Calendar.MONTH);
        Integer dayOfMonth=calendar.get(Calendar.DATE);
        if(this.firstTime) {
            datePicker.init(year, monthOfYear, dayOfMonth, this.dateSetListener);
            //LinearLayout ll = (LinearLayout)datePicker.getChildAt(0);
            //LinearLayout ll2 = (LinearLayout)ll.getChildAt(0);
            //ll2.getChildAt(0).setVisibility(View.INVISIBLE);
            //showDate(year, monthOfYear, dayOfMonth, R.style.NumberPickerStyle);
            this.firstTime=Boolean.FALSE;
        } else {
           // this.datePickerDialog.show();
        }
    }

    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
       /**
        this.datePickerDialog=this.spinnerDatePickerDialogBuilder
                .context(this.healthDataContext).callback(this.dateSetListener)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build();
        this.datePickerDialog.show();
    */
    }
}
