/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/19/18 6:29 PM
 */

package sud_tanj.com.phr_android.Health_Data.Interface;

import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 19/05/2018 - 18:29.
 * <p>
 * This class last modified by User
 */
public class SortByMonthListener extends SortingListener {
    public SortByMonthListener(DatePicker datePicker, GraphView graphView) {
        super(datePicker, graphView);
    }

    @Override
    public void onClick(View view) {
        LinearLayout ll = (LinearLayout)this.getDatePicker().getChildAt(0);
        LinearLayout ll2 = (LinearLayout)ll.getChildAt(0);
        ll2.getChildAt(0).setVisibility(View.INVISIBLE);
        ll2.getChildAt(1).setVisibility(View.VISIBLE);
        ll2.getChildAt(2).setVisibility(View.VISIBLE);
        getGraphView().getViewport().setXAxisBoundsManual(true);
        getGraphView().getViewport().setMinX(1);
        getGraphView().getViewport().setMaxX(31);
        getGraphView().refreshDrawableState();
    }
}
