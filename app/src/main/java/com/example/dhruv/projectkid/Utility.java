package com.example.dhruv.projectkid;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Utility class to created by dhruv on 6/7/15.
 */
public class Utility {
    public static String getFormattedDateString(int year, int month, int dayOfMonth){
        GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        dateFormat.setCalendar(calendar);
        return dateFormat.format(calendar.getTime());
    }

}
