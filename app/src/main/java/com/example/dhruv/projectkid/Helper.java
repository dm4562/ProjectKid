package com.example.dhruv.projectkid;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Helper class to created by dhruv on 6/7/15.
 */
public class Helper {
    public static String getFormattedDateString(int year, int month, int dayOfMonth){
        GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        dateFormat.setCalendar(calendar);
        String dateFormatted = dateFormat.format(calendar.getTime());
        return dateFormatted;
    }

}
