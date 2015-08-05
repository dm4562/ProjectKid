package com.example.dhruv.projectkid;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhruv.projectkid.data.UserContract;
import com.example.dhruv.projectkid.data.UserContract.*;
import com.example.dhruv.projectkid.data.UserHelper;


/**
 * A placeholder fragment containing a simple view.
 */
public class ChildExtracurricularsActivityFragment extends Fragment {

    public static ListView activityListView;
    private  SimpleCursorAdapter cursorAdapter;

    public ChildExtracurricularsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_extracurriculars, container, false);
        activityListView = (ListView) view.findViewById(R.id.listView);
        final LinearLayout activitiesListingContainer = (LinearLayout)
                view.findViewById(R.id.activities_listing_container);

        LoadActivities loadActivities = new LoadActivities();
        loadActivities.execute("");

        activityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView =
                        (CheckedTextView) view.findViewById(R.id.checked_text_view);
                if (checkedTextView.isChecked())
                    checkedTextView.setChecked(false);
                else
                    checkedTextView.setChecked(true);
            }
        });

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.activities_radio_container);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.activities_radio_yes) {
                    activitiesListingContainer.setVisibility(View.VISIBLE);
                } else {
                    activitiesListingContainer.setVisibility(View.INVISIBLE);
                }
            }
        });

        return view;
    }


    class LoadActivities extends AsyncTask<String, Void, SimpleCursorAdapter> {

        @Override
        protected SimpleCursorAdapter doInBackground(String... params) {

           // SimpleCursorAdapter cursorAdapter;
            final String[] PROJECTION = {
                    AllActivitiesEntry._ID,
                    AllActivitiesEntry.COLUMN_NAME_ACTIVITY_NAME,
                    AllActivitiesEntry.COLUMN_NAME_MIN_AGE
            };

            final String SELECTION = null;
            String sortOrder = AllActivitiesEntry.COLUMN_NAME_ACTIVITY_NAME + " ASC";
            String[] from_cols = {AllActivitiesEntry.COLUMN_NAME_ACTIVITY_NAME};

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

            int[] to_cols = {R.id.checked_text_view};
            cursorAdapter = new SimpleCursorAdapter(getActivity(),
                    R.layout.select_activity_simple_list_item,
                    cursor,
                    from_cols,
                    to_cols,
                    0);

            return cursorAdapter;
        }

        @Override
        protected void onPostExecute(SimpleCursorAdapter simpleCursorAdapter) {
            super.onPostExecute(simpleCursorAdapter);
            activityListView.setAdapter(simpleCursorAdapter);
            activityListView.setItemsCanFocus(false);
        }
    }
}