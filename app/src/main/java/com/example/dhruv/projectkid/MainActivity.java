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
import java.util.Arrays;
import java.util.List;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating the Database if it doesn't exist and inserting the values into
        // AllActivitiesDatabase
        createAllActivitiesDb();

        TextView signUpButton = (TextView) findViewById(R.id.sign_up_button);
        final EditText usernameEditText = (EditText) findViewById(R.id.username_field);
        final EditText passwordEditText = (EditText) findViewById(R.id.password_field);
        Button signInButton = (Button) findViewById(R.id.sign_in_button);

        UserHelper userHelper = new UserHelper(this);
        final SQLiteDatabase database = userHelper.getReadableDatabase();

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

                String[] PROJECTION = { UserProfileEntry.COLUMN_NAME_PARENT_EMAIL,
                        UserProfileEntry.COLUMN_NAME_PARENT_PASSWORD };

                Cursor cursor = database.query(UserContract.UserProfileEntry.TABLE_NAME,
                        PROJECTION,
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
                    String emailDb = cursor.getString(cursor.getColumnIndexOrThrow(
                            UserProfileEntry.COLUMN_NAME_PARENT_EMAIL));
                    String passwordDb = cursor.getString(cursor.getColumnIndexOrThrow(
                            UserProfileEntry.COLUMN_NAME_PARENT_PASSWORD));
                    if (passwordDb.equals(password)) {
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
}
