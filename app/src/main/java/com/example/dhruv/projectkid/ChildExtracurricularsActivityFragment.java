package com.example.dhruv.projectkid;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.dhruv.projectkid.data.UserContract;
import com.example.dhruv.projectkid.data.UserContract.*;
import com.example.dhruv.projectkid.data.UserHelper;


/**
 * A placeholder fragment containing a simple view.
 */
public class ChildExtracurricularsActivityFragment extends Fragment {


    public ChildExtracurricularsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_extracurriculars, container, false);

        UserHelper mDbHelper = new UserHelper(getActivity());
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        String[] projection = {
                AllActivitiesEntry._ID,
                AllActivitiesEntry.COLUMN_NAME_ACTIVITY_NAME,
                AllActivitiesEntry.COLUMN_NAME_MIN_AGE
        };

        String sortOrder = AllActivitiesEntry.COLUMN_NAME_ACTIVITY_NAME + " ASC";
        Cursor cursor = database.query(
                AllActivitiesEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        int[] to = {
                R.id.textView,
                R.id.textView2,
                R.id.textView3
        };


        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                getActivity().getApplicationContext(),
                R.layout.allactivities_layout,
                cursor,
                projection,
                to);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(cursorAdapter);
        return view;
    }
}
