package com.example.dhruv.projectkid.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.dhruv.projectkid.data.UserContract.*;

import java.io.File;

/**
 *  Helper class for the database
 *  Created by dhruv on 8/7/15.
 */
public class UserHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "user.db";

    public UserHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_USER_PROFILE_TABLE = "CREATE TABLE " + UserProfileEntry.TABLE_NAME +
                " (" +
                UserProfileEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserProfileEntry.COLUMN_NAME_PARENT_NAME + " TEXT NOT NULL, " +
                UserProfileEntry.COLUMN_NAME_PARENT_PHONE + " INTEGER UNIQUE NOT NULL, " +
                UserProfileEntry.COLUMN_NAME_PARENT_EMAIL + " TEXT UNIQUE NOT NULL, " +
                UserProfileEntry.COLUMN_NAME_PARENT_PASSWORD + " TEXT NOT NULL, " +
                UserProfileEntry.COLUMN_NAME_CHILD_NAME + " TEXT NOT NULL, " +
                UserProfileEntry.COLUMN_NAME_CHILD_DOB + " TEXT NOT NULL, " +
                UserProfileEntry.COLUMN_NAME_CHILD_GENDER + " TEXT NOT NULL, " +
                " UNIQUE ( " + UserProfileEntry.COLUMN_NAME_PARENT_PHONE + ", " +
                UserProfileEntry.COLUMN_NAME_PARENT_EMAIL + " ) ON CONFLICT IGNORE " +
                ");";

        final String SQL_CREATE_ALL_ACTIVITIES_TABLE = "CREATE TABLE " + AllActivitiesEntry.TABLE_NAME +
                " ( " +
                AllActivitiesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AllActivitiesEntry.COLUMN_NAME_ACTIVITY_NAME + " TEXT UNIQUE NOT NULL, " +
                AllActivitiesEntry.COLUMN_NAME_MIN_AGE + " INTEGER NOT NULL );";

        final String SQL_CREATE_USER_ACTIVITIES_TABLE = "CREATE TABLE " +
                UserActivitiesEntry.TABLE_NAME + " (" +
                UserActivitiesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserActivitiesEntry.COLUMN_NAME_USER_ID + " INTEGER NOT NULL, " +
                UserActivitiesEntry.COLUMN_NAME_USER_ACTIVITY_ID + " INTEGER NOT NULL, " +

                //Setting up COLUMN_USER_ID as a foreign key to UserProfileTable
                " FOREIGN KEY ( " + UserActivitiesEntry.COLUMN_NAME_USER_ID + " ) REFERENCES " +
                UserProfileEntry.TABLE_NAME + " ( " + UserProfileEntry._ID + " ), " +

                //Setting up COLUMN_USER_ACTIVITY_ID as a foreign key to AllActivitiesTable
                " FOREIGN KEY ( " + UserActivitiesEntry.COLUMN_NAME_USER_ACTIVITY_ID +
                " ) REFERENCES " + AllActivitiesEntry.TABLE_NAME +
                " ( " + AllActivitiesEntry._ID + " ) );";

        db.execSQL(SQL_CREATE_USER_PROFILE_TABLE);
        db.execSQL(SQL_CREATE_ALL_ACTIVITIES_TABLE);
        db.execSQL(SQL_CREATE_USER_ACTIVITIES_TABLE);
    }

    public static boolean checkDatabaseExists(Context context, String databaseName) {
        File dbFile = context.getDatabasePath(databaseName);
        return dbFile.exists();
    }
}
