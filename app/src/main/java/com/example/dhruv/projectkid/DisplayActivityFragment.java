package com.example.dhruv.projectkid;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DisplayActivityFragment extends Fragment {

    public DisplayActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_display, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listView_temp);
        ArrayList<String> completedActivities =
                getActivity().getIntent().getStringArrayListExtra("completedActivities");

        ArrayAdapter arrayAdapter =
                new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, completedActivities);
        listView.setAdapter(arrayAdapter);

        return view;
    }
}