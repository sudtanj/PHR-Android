/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/19/18 6:23 PM
 */

package sud_tanj.com.phr_android.Health_Data.Interface;

import android.view.View;
import android.widget.DatePicker;

import com.jjoe64.graphview.GraphView;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 19/05/2018 - 18:23.
 * <p>
 * This class last modified by User
 */
public abstract class SortingListener implements View.OnClickListener {
    public GraphView getGraphView() {
        return graphView;
    }

    private DatePicker datePicker;
    private GraphView graphView;
    public SortingListener(DatePicker datePicker, GraphView graphView) {
        this.datePicker=datePicker;
        this.graphView=graphView;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }
}
