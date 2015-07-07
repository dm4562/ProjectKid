package com.example.dhruv.projectkid;

import android.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

class SignUpViewHolder {

    public static EditText parentFullNameEditText;
    public static EditText parentPhoneNumberEditText;
    public static EditText parentEmailEditText;
    public static EditText parentPasswordEdiText;
    public static EditText childFullNameEditText;
    public static TextView childBirthDateText;
    public static Spinner childGenderSpinner;

    public SignUpViewHolder(View view){
        parentFullNameEditText = (EditText) view.findViewById(R.id.parent_full_name);
        parentPhoneNumberEditText = (EditText) view.findViewById(R.id.parent_phone_number);
        parentEmailEditText = (EditText) view.findViewById(R.id.parent_email);
        parentPasswordEdiText = (EditText) view.findViewById(R.id.parent_password);
        childFullNameEditText = (EditText) view.findViewById(R.id.child_name);
    }

}

/**
 * A placeholder fragment containing a simple view.
 */
public class ParentSignUpActivityFragment extends Fragment {



    public ParentSignUpActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_signup, container, false);


        signUpViewHolder.childBirthDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void showDatePickerDialog(){
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getActivity().getFragmentManager(), "datePicker");
    }
}
