package com.example.dhruv.projectkid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhruv.projectkid.data.UserContract;
import com.example.dhruv.projectkid.data.UserContract.*;
import com.example.dhruv.projectkid.data.UserHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class ChildExtracurricularsActivityFragment extends Fragment {

    public ListView activityListView;
    private ArrayList<String> completedActivities = new ArrayList<>();

    public ChildExtracurricularsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_extracurriculars, container, false);
        activityListView = (ListView) view.findViewById(R.id.listView);
        final LinearLayout activitiesListingContainer = (LinearLayout)
                view.findViewById(R.id.activities_listing_container);
        final Button finishButton = (Button) view.findViewById(R.id.activities_page_next_button);

        LoadActivities loadActivities = new LoadActivities();
        loadActivities.execute("");

        activityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox_content);
                TextView activityText = (TextView) view.findViewById(R.id.textView_content);
                String activityName = activityText.getText().toString();

                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
                    if (completedActivities.contains(activityName)) {
                        completedActivities.remove(activityName);
                    }
                } else {
                    checkBox.setChecked(true);
                    if (!completedActivities.contains(activityName)) {
                        completedActivities.add(activityName);
                    }
                }
            }
        });

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.activities_radio_container);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.activities_radio_yes) {
                    activitiesListingContainer.setVisibility(View.VISIBLE);
                } else {
                    activitiesListingContainer.setVisibility(View.GONE);
                }
                finishButton.setTextColor(getResources().getColor(R.color.colorAccent));
                finishButton.setEnabled(true);
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentSignUpActivityFragment.newUser.setCompletedActivities(completedActivities);
                boolean inserted = insertUserProfileDetails(ParentSignUpActivityFragment.newUser);
                if (inserted) {
                    Toast.makeText(getActivity(), "User created", Toast.LENGTH_SHORT).show();
                    insertUserActivities(ParentSignUpActivityFragment.newUser);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "User already exists", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }


    class LoadActivities extends AsyncTask<String, Void, MyCursorAdapter> {

        @Override
        protected MyCursorAdapter doInBackground(String... params) {

            final String[] PROJECTION = {
                    AllActivitiesEntry._ID,
                    AllActivitiesEntry.COLUMN_NAME_ACTIVITY_NAME,
                    AllActivitiesEntry.COLUMN_NAME_MIN_AGE
            };

            final String SELECTION = null;
            String sortOrder = AllActivitiesEntry.COLUMN_NAME_ACTIVITY_NAME + " ASC";

            UserHelper mUserHelper = new UserHelper(getActivity());
            SQLiteDatabase database = mUserHelper.getReadableDatabase();

            Cursor cursor = database.query(AllActivitiesEntry.TABLE_NAME,
                    PROJECTION,
                    SELECTION,
                    null,
                    null,
                    null,
                    sortOrder
            );

            MyCursorAdapter myCursorAdapter = new MyCursorAdapter(
                    getActivity(),
                    R.layout.select_activity_simple_list_item,
                    cursor,
                    0
            );

            database.close();
            mUserHelper.close();
            return myCursorAdapter;
        }

        @Override
        protected void onPostExecute(MyCursorAdapter myCursorAdapter) {
            super.onPostExecute(myCursorAdapter);
            activityListView.setAdapter(myCursorAdapter);
            activityListView.setItemsCanFocus(false);
        }
    }

    public boolean insertUserProfileDetails (UserProfileDetails details) {
        UserHelper helper = new UserHelper(getActivity());
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query(UserProfileEntry.TABLE_NAME,
                null,
                UserProfileEntry.COLUMN_NAME_PARENT_EMAIL + "=?",
                new String[] {details.getParentEmail()},
                null,
                null,
                null);
        if (cursor.getCount() > 0) {
            //The same email already exists and can't be inserted
            database.close();
            helper.close();
            return false;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(UserProfileEntry.COLUMN_NAME_PARENT_NAME, details.getParentName());
            contentValues.put(UserProfileEntry.COLUMN_NAME_PARENT_PHONE, details.getParentPhoneNumber());
            contentValues.put(UserProfileEntry.COLUMN_NAME_PARENT_EMAIL, details.getParentEmail());
            contentValues.put(UserProfileEntry.COLUMN_NAME_PARENT_PASSWORD, details.getParentPassword());
            contentValues.put(UserProfileEntry.COLUMN_NAME_CHILD_NAME, details.getChildName());
            contentValues.put(UserProfileEntry.COLUMN_NAME_CHILD_GENDER, details.getChildGender());

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
            dateFormat.setCalendar(details.getChildBirthDate());
            String birthDate = dateFormat.format(details.getChildBirthDate().getTime());

            contentValues.put(UserProfileEntry.COLUMN_NAME_CHILD_DOB, birthDate);
            long userId = database.insertOrThrow(UserProfileEntry.TABLE_NAME, null, contentValues);
            database.close();
            helper.close();

            return (userId >= 0);
        }
    }

    public boolean insertUserActivities (UserProfileDetails details) {

        if (details.getCompletedActivities().size() > 0) {

            UserHelper helper = new UserHelper(getActivity());
            SQLiteDatabase database = helper.getWritableDatabase();

            Cursor tempCursor = database.query(UserProfileEntry.TABLE_NAME,
                    new String[] { UserProfileEntry._ID },
                    UserProfileEntry.COLUMN_NAME_PARENT_EMAIL + " =?",
                    new String[] { details.getParentEmail() },
                    null,
                    null,
                    null);

            if (tempCursor.moveToFirst()) {

                int parentId =
                        tempCursor.getInt(tempCursor.getColumnIndexOrThrow(UserProfileEntry._ID));

                Cursor cursor = database.query(UserActivitiesEntry.TABLE_NAME,
                        new String[]{UserActivitiesEntry.COLUMN_NAME_USER_ACTIVITY_NAME},
                        UserActivitiesEntry.COLUMN_NAME_USER_ID + " = " + Integer.toString(parentId),
                        null,
                        null,
                        null,
                        null);

                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    int count = 0;
                    ContentValues contentValues = new ContentValues();
                    do {
                        String activityName = cursor.getString(cursor.getColumnIndexOrThrow(
                                UserActivitiesEntry.COLUMN_NAME_USER_ACTIVITY_NAME));
                        if (!activityName.equals(completedActivities.get(count))) {
                            contentValues.put(UserActivitiesEntry.COLUMN_NAME_USER_ACTIVITY_NAME,
                                    completedActivities.get(count));
                            contentValues.put(UserActivitiesEntry.COLUMN_NAME_USER_ID, parentId);
                            database.insertOrThrow(UserActivitiesEntry.TABLE_NAME, null, contentValues);
                        }
                    } while (cursor.moveToNext());

                    database.close();
                    helper.close();
                    return true;
                } else {
                    ContentValues contentValues = new ContentValues();
                    for (int i = 0; i < completedActivities.size(); i++) {
                        contentValues.put(UserActivitiesEntry.COLUMN_NAME_USER_ID, parentId);
                        contentValues.put(UserActivitiesEntry.COLUMN_NAME_USER_ACTIVITY_NAME,
                                completedActivities.get(i));
                        database.insertOrThrow(UserActivitiesEntry.TABLE_NAME, null, contentValues);
                        contentValues.clear();
                    }

                    database.close();
                    helper.close();
                    return true;
                }
            } else {
                database.close();
                helper.close();
                return false;
            }
        } else {
            return false;
        }
    }
}