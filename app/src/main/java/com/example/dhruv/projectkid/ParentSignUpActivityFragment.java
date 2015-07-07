package com.example.dhruv.projectkid;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

class SignUpViewHolder {

    public EditText parentFullNameEditText;
    public EditText parentPhoneNumberEditText;
    public EditText parentEmailEditText;
    public EditText parentPasswordEdiText;
    public EditText childFullNameEditText;
    public TextView childBirthDateText;
    public Spinner childGenderSpinner;
    public Button nextButton;

    public SignUpViewHolder(View view){
        parentFullNameEditText = (EditText) view.findViewById(R.id.parent_full_name);
        parentPhoneNumberEditText = (EditText) view.findViewById(R.id.parent_phone_number);
        parentEmailEditText = (EditText) view.findViewById(R.id.parent_email);
        parentPasswordEdiText = (EditText) view.findViewById(R.id.parent_password);
        childFullNameEditText = (EditText) view.findViewById(R.id.child_name);
        childBirthDateText = (TextView) view.findViewById(R.id.birth_date_text_view);
        childGenderSpinner = (Spinner) view.findViewById(R.id.child_spinner_gender);
        nextButton = (Button) view.findViewById(R.id.sign_up_page_next_button);
    }
}

/**
 * A placeholder fragment containing a simple view.
 */
public class ParentSignUpActivityFragment extends Fragment {

    public static SignUpViewHolder signUpViewHolder;
    public static UserProfileDetails newUser = new UserProfileDetails();

    public static String parentName;
    public static String parentPhoneNumber;
    public static String parentEmail;
    public static String parentPassword;
    public static String childName;
    public static String childBirthDate;
    public static String childGender;

    public ParentSignUpActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_signup, container, false);

        signUpViewHolder = new SignUpViewHolder(view);
        signUpViewHolder.childBirthDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        signUpViewHolder.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewUserData();
                Intent intent = new Intent(getActivity(),
                        ChildExtracurricularsActivity.class);
                startActivity(intent);
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

    @Override
    public void onResume() {
        super.onResume();
    }

    public void showDatePickerDialog(){
        DialogFragment dialogFragment = new SignUpBirthDatePickerFragment();
        dialogFragment.show(getActivity().getFragmentManager(), "datePicker");
    }

    public void setNewUserData(){

        parentName = signUpViewHolder.parentFullNameEditText.getText().toString();
        parentEmail = signUpViewHolder.parentEmailEditText.getText().toString();
        parentPhoneNumber = signUpViewHolder.parentPhoneNumberEditText.getText().toString();
        parentPassword = signUpViewHolder.parentPasswordEdiText.getText().toString();
        childName = signUpViewHolder.childFullNameEditText.getText().toString();
        childGender = signUpViewHolder.childGenderSpinner.getSelectedItem().toString();
        childBirthDate = signUpViewHolder.childBirthDateText.getText().toString();

        newUser.setParentName(parentName);
        newUser.setParentEmail(parentEmail);
        newUser.setParentPhoneNumber(parentPhoneNumber);
        newUser.setParentPassword(parentPassword);
        newUser.setChildName(childName);
        newUser.setChildGender(childGender);
    }
}
