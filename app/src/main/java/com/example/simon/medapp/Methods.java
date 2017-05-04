package com.example.simon.medapp;

import android.text.format.DateUtils;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by simon on 2017-04-18.
 */

public class Methods {

    /**
     *
     * @param d: date-object
     * @return String for day of the week fully written out, ex. Friday
     */
    public static String getDay(Calendar d) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        if (DateUtils.isToday(d.getTimeInMillis())) {
            return "Today";
        } else {
            return dateFormat.format(d.getTime());
        }
    }

    /**
     * Changes date when arrows clicked in layout, NOT FINISHED
     * @param view id of arrow-button
     */
    public static void dateChange(View view) {

    }

    /**
     *
     * @param d: date-object
     * @return String for month and day, ex. September, 25
     */
    public static String getDate(Calendar d) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM, d");
        return dateFormat.format(d.getTime());
    }

}
