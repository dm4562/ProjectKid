package com.example.dhruv.projectkid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhruv.projectkid.data.*;
import com.example.dhruv.projectkid.data.UserContract.*;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class MainActivity extends FragmentActivity {

    public static UserProfileDetails userProfileDetails = new UserProfileDetails();
    UserHelper userHelper;
    public SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Creating the Database if it doesn't exist and inserting the values into
        // AllActivitiesDatabase
        createAllActivitiesDb();

        userHelper = new UserHelper(this);
        database = userHelper.getReadableDatabase();

        TextView signUpButton = (TextView) findViewById(R.id.sign_up_button);
        final EditText usernameEditText = (EditText) findViewById(R.id.username_field);
        final EditText passwordEditText = (EditText) findViewById(R.id.password_field);
        Button signInButton = (Button) findViewById(R.id.sign_in_button);

        signUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ParentSignUpActivity.class);
                startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();

//                String[] PROJECTION = { UserProfileEntry.COLUMN_NAME_PARENT_EMAIL,
//                        UserProfileEntry.COLUMN_NAME_PARENT_PASSWORD };

                Cursor cursor = database.query(UserContract.UserProfileEntry.TABLE_NAME,
                        null,
                        UserProfileEntry.COLUMN_NAME_PARENT_EMAIL + " =?",
                        new String[] { username },
                        null,
                        null,
                        null);

                if (cursor.getCount() <= 0) {
                    Toast.makeText(getApplication(),
                            getResources().getString(R.string.incorrect_email_id),
                            Toast.LENGTH_SHORT).show();
                } else {
                    cursor.moveToFirst();

                    String passwordDb = cursor.getString(cursor.getColumnIndexOrThrow(
                            UserProfileEntry.COLUMN_NAME_PARENT_PASSWORD));
                    if (passwordDb.equals(password)) {
                        createUserProfileDetails(cursor);
                        Intent intent = new Intent(getApplicationContext(), DisplayActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.incorrect_password),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createAllActivitiesDb() {
        if(!UserHelper.checkDatabaseExists(getApplicationContext(), UserHelper.DATABASE_NAME)){

            UserHelper mDbHelper = new UserHelper(this);
            SQLiteDatabase mDatabase = mDbHelper.getWritableDatabase();
            List<String> allActivityNames = Arrays.asList(
                    getResources().getStringArray(R.array.all_activities_for_database)
            );

            int[] allActivityAges = getResources().getIntArray(R.array.all_ages_for_database);

            for (int i = 0; i < allActivityAges.length; i++) {
                ContentValues values = new ContentValues();
                values.put(UserContract.AllActivitiesEntry.COLUMN_NAME_ACTIVITY_NAME,
                        allActivityNames.get(i));
                values.put(UserContract.AllActivitiesEntry.COLUMN_NAME_MIN_AGE,
                        allActivityAges[i]);

                long newRowId = mDatabase.insert(UserContract.AllActivitiesEntry.TABLE_NAME,
                        null, values);

                values.clear();
            }
            mDatabase.close();
            mDbHelper.close();
        }
    }

    public void createUserProfileDetails (Cursor cursor) {
        String email = cursor.getString(
                cursor.getColumnIndexOrThrow(UserProfileEntry.COLUMN_NAME_PARENT_EMAIL));
        String nameParent = cursor.getString(
                cursor.getColumnIndexOrThrow(UserProfileEntry.COLUMN_NAME_PARENT_NAME));
        String nameChild = cursor.getString(
                cursor.getColumnIndexOrThrow(UserProfileEntry.COLUMN_NAME_CHILD_NAME));
        long phone = cursor.getLong(
                cursor.getColumnIndexOrThrow(UserProfileEntry.COLUMN_NAME_PARENT_PHONE));
        String childGender = cursor.getString(
                cursor.getColumnIndexOrThrow(UserProfileEntry.COLUMN_NAME_CHILD_GENDER));
        int userId = cursor.getInt(cursor.getColumnIndexOrThrow(UserProfileEntry._ID));
        String rawBirthDate = cursor.getString(
                cursor.getColumnIndexOrThrow(UserProfileEntry.COLUMN_NAME_CHILD_DOB));
        GregorianCalendar birthDate = Utility.getGregorianCalendarFromString(rawBirthDate);

        Cursor activityCursor = database.query(UserActivitiesEntry.TABLE_NAME,
                new String[]{UserActivitiesEntry.COLUMN_NAME_USER_ACTIVITY_NAME},
                UserActivitiesEntry.COLUMN_NAME_USER_ID + " =? ",
                new String[]{Integer.toString(userId)},
                null,
                null,
                UserActivitiesEntry.COLUMN_NAME_USER_ACTIVITY_NAME + " ASC");

        ArrayList<String> completedActivities = new ArrayList<>();
        activityCursor.moveToFirst();
        int count = 0;

        do {
            String activity = activityCursor.getString(activityCursor.getColumnIndexOrThrow(
                    UserActivitiesEntry.COLUMN_NAME_USER_ACTIVITY_NAME));
            completedActivities.add(count, activity);
            count++;
        }while (activityCursor.moveToNext());

        userProfileDetails.setParentEmail(email);
        userProfileDetails.setParentName(nameParent);
        userProfileDetails.setChildName(nameChild);
        userProfileDetails.setParentPhoneNumber(Long.toString(phone));
        userProfileDetails.setChildGender(childGender);
        userProfileDetails.setChildBirthDate(birthDate);
        userProfileDetails.setCompletedActivities(completedActivities);
    }
}
