package com.example.dhruv.projectkid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhruv.projectkid.data.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);

        TextView signUpButton = (TextView) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener((View v) -> {
                Intent intent = new Intent(MainActivity.this, ParentSignUpActivity.class);
                startActivity(intent);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creating the Database if it doesn't exist and inserting the values into
        // AllActivitiesDatabase
        if(UserHelper.checkDatabaseExists(getApplicationContext(), UserHelper.DATABASE_NAME)){
            Toast.makeText(this, "Database exists!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Database doesn't exist!", Toast.LENGTH_LONG).show();
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
            mDbHelper.close();
        }

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
}
