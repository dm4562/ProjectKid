package com.example.dhruv.projectkid.data;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.dhruv.projectkid.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Contract class that defines all the columns and everything for the database schema
 * Created by dhruv on 8/7/15.
 */
public class UserContract {

    public UserContract() {}

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.
    public static final String CONTENT_AUTHORITY = MainActivity.class.getPackage().getName();

    // Using the CONTENT_AUTHORITY to create the base of all Uris that the app will use to access
    // the database.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    public static final String PATH_USER_PROFILE = "user_profile";
    public static final String PATH_USER_ACTIVITIES = "user_activities";
    public static final String PATH_ALL_ACTIVITIES = "all_activities";

    public static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * Converts Date class to a string representation, used for easy comparison and database lookup.
     * @param date The input date
     * @return a DB-friendly representation of the date, using the format defined in DATE_FORMAT.
     */
    public static String getDbDateString(Date date){
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }


    /**
     * Converts a dateText to a long Unix time representation
     * @param dateText the input date string
     * @return the Date object
     */
    public static Date getDateFromDb(String dateText) {
        SimpleDateFormat dbDateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return dbDateFormat.parse(dateText);
        } catch ( ParseException e ) {
            e.printStackTrace();
            return null;
        }
    }

    public static final class UserProfileEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER_PROFILE).build();

        public static final String TABLE_NAME = "user_profile";
        public static final String COLUMN_NAME_PARENT_NAME = "_p_name";
        public static final String COLUMN_NAME_PARENT_PHONE = "_p_phone";
        public static final String COLUMN_NAME_PARENT_EMAIL = "_p_email";
        public static final String COLUMN_NAME_PARENT_PASSWORD = "_p_password";
        public static final String COLUMN_NAME_CHILD_NAME = "_c_name";
        public static final String COLUMN_NAME_CHILD_DOB = "_c_dob";
        public static final String COLUMN_NAME_CHILD_GENDER = "_c_gender";
    }

    public static final class UserActivitiesEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER_ACTIVITIES).build();

        public static final String TABLE_NAME = "user_activities";
        public static final String COLUMN_NAME_USER_ID = "_user_ID";
        public static final String COLUMN_NAME_USER_ACTIVITY_NAME = "_user_activities";
    }

    public static final class AllActivitiesEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ALL_ACTIVITIES).build();

        public static final String TABLE_NAME = "all_activities";
        public static final String COLUMN_NAME_ACTIVITY_NAME = "_activity_name";
        public static final String COLUMN_NAME_MIN_AGE = "_min_age";
    }
}
