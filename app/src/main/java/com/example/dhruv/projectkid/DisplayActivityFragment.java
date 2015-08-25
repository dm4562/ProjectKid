package com.example.dhruv.projectkid;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.dhruv.projectkid.MainActivity.*;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DisplayActivityFragment extends Fragment {

    public class ViewHolder {

        TextView parentNameTextView;
        TextView childNameTextView;
        TextView parentEmailTextView;
        TextView parentPhoneNumberTextView;
        Button displayActivitiesButton;

        public ViewHolder(View parentView) {
            parentNameTextView = (TextView) parentView.findViewById(R.id.display_parent_name);
            childNameTextView = (TextView) parentView.findViewById(R.id.display_child_name);
            parentEmailTextView =
                    (TextView) parentView.findViewById(R.id.display_parent_email_textView);
            parentPhoneNumberTextView =
                    (TextView) parentView.findViewById(R.id.display_parent_phone_number_textView);
            displayActivitiesButton =
                    (Button) parentView.findViewById(R.id.display_show_activities_button);
        }
    }
    public DisplayActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_display, container, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.parentNameTextView.setText(MainActivity.userProfileDetails.getParentName());
        viewHolder.childNameTextView.setText(MainActivity.userProfileDetails.getChildName());
        viewHolder.parentEmailTextView.setText(MainActivity.userProfileDetails.getParentEmail());
        viewHolder.parentPhoneNumberTextView.setText(
                MainActivity.userProfileDetails.getParentPhoneNumber());
        return view;
    }
}
