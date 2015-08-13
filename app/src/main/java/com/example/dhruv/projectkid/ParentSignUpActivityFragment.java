package com.example.dhruv.projectkid;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhruv.projectkid.data.UserContract;
import com.example.dhruv.projectkid.data.UserHelper;

class SignUpViewHolder {

    public EditText parentFullNameEditText;
    public EditText parentPhoneNumberEditText;
    public EditText parentEmailEditText;
    public EditText parentPasswordEdiText;
    public EditText parentPasswordRepeatEditText;
    public EditText childFullNameEditText;
    public TextView childBirthDateText;
    public Spinner childGenderSpinner;
    public Button nextButton;

    public SignUpViewHolder(View view){
        parentFullNameEditText = (EditText) view.findViewById(R.id.parent_full_name);
        parentPhoneNumberEditText = (EditText) view.findViewById(R.id.parent_phone_number);
        parentEmailEditText = (EditText) view.findViewById(R.id.parent_email);
        parentPasswordEdiText = (EditText) view.findViewById(R.id.parent_password);
        parentPasswordRepeatEditText = (EditText) view.findViewById(R.id.parent_password_confirm);
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

    public SignUpViewHolder signUpViewHolder;
    public static UserProfileDetails newUser = new UserProfileDetails();

    public String parentName;
    public String parentPhoneNumber;
    public String parentEmail;
    public String parentPassword;
    public String childName;
    public String childBirthDate;
    public String childGender;

    boolean parentNameEntered = false;
    boolean parentPhoneNumberEntered = false;
    boolean parentEmailEntered = false;
    boolean parentPasswordEntered = false;
    boolean parentPasswordRepeatEntered = false;
    boolean childNameEntered = false;
    boolean childBirthDateSelected = false;

    public UserHelper dbHelper;
    public SQLiteDatabase database;

    public TextWatcher parentNameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            checkCorrectDataEntered();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            parentNameEntered = true;
            checkCorrectDataEntered();
        }
    };
    public TextWatcher parentPhoneNumberWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            checkCorrectDataEntered();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();
            if(text.length() == 10){
                Cursor cursor = database.rawQuery("SELECT * FROM " +
                        UserContract.UserProfileEntry.TABLE_NAME + " WHERE " +
                        UserContract.UserProfileEntry.COLUMN_NAME_PARENT_PHONE + " = " + text, null);

                if (cursor.getCount() <= 0) {
                    parentPhoneNumberEntered = true;
                    signUpViewHolder.parentPhoneNumberEditText.setError(null);
                }
                else {
                    signUpViewHolder.parentPhoneNumberEditText.setError(
                            getResources().getString(R.string.phone_number_already_exists));
                    parentPasswordEntered = false;
                }
            }
            else{
                signUpViewHolder.parentPhoneNumberEditText.setError(
                        getResources().getString(R.string.wrong_phone_number));
                parentPhoneNumberEntered = false;
            }
            checkCorrectDataEntered();
        }
    };
    public TextWatcher parentEmailWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            checkCorrectDataEntered();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.toString().contains("@")){
                Cursor cursor = database.query(UserContract.UserProfileEntry.TABLE_NAME,
                        new String[] {UserContract.UserProfileEntry.COLUMN_NAME_PARENT_EMAIL},
                        UserContract.UserProfileEntry.COLUMN_NAME_PARENT_EMAIL + " =? ",
                        new String[] { s.toString() },
                        null,
                        null,
                        null
                );

                if (cursor.getCount() <= 0) {
                    parentEmailEntered = true;
                    signUpViewHolder.parentEmailEditText.setError(null);
                } else {
                    parentEmailEntered = false;
                    signUpViewHolder.parentEmailEditText.setError(
                            getResources().getString(R.string.email_already_exist));
                }

            }
            else {
                signUpViewHolder.parentEmailEditText.setError(
                        getResources().getString(R.string.wrong_email)
                );
                parentEmailEntered = false;
            }
            checkCorrectDataEntered();
        }
    };
    public TextWatcher parentPasswordWatcher =  new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            checkCorrectDataEntered();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length() >= 5){
                parentPasswordEntered = true;
            }
            else {
                signUpViewHolder.parentPasswordEdiText.setError(
                        getResources().getString(R.string.password_error)
                );
                parentPasswordEntered = false;
            }
            checkCorrectDataEntered();
        }
    };
    public TextWatcher getParentPasswordRepeatWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            checkCorrectDataEntered();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String passwordAbove = signUpViewHolder.parentPasswordEdiText.getText().toString();
            if (passwordAbove.equals(s.toString())) {
                parentPasswordRepeatEntered = true;
                signUpViewHolder.parentPasswordRepeatEditText.setError(null);
            } else {
                parentPasswordRepeatEntered = false;
                signUpViewHolder.parentPasswordRepeatEditText.setError(
                        getResources().getString(R.string.password_match_error));
            }
            checkCorrectDataEntered();
        }
    };
    public TextWatcher childNameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
                childNameEntered = true;
        }
    };
    public TextWatcher childBirthDateWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            checkCorrectDataEntered();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!s.toString().matches(getResources().getString(R.string.select_birth_date_button))){
                childBirthDateSelected = true;
            }
            checkCorrectDataEntered();
        }
    };

    public ParentSignUpActivityFragment() {
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dbHelper.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_signup, container, false);
        signUpViewHolder = new SignUpViewHolder(view);

        dbHelper = new UserHelper(getActivity());
        database = dbHelper.getReadableDatabase();

        signUpViewHolder.parentFullNameEditText.addTextChangedListener(parentNameWatcher);
        signUpViewHolder.parentPhoneNumberEditText.addTextChangedListener(parentPhoneNumberWatcher);
        signUpViewHolder.parentEmailEditText.addTextChangedListener(parentEmailWatcher);
        signUpViewHolder.parentPasswordEdiText.addTextChangedListener(parentPasswordWatcher);
        signUpViewHolder.parentPasswordRepeatEditText.addTextChangedListener(getParentPasswordRepeatWatcher);
        signUpViewHolder.childFullNameEditText.addTextChangedListener(childNameWatcher);
        signUpViewHolder.childBirthDateText.addTextChangedListener(childBirthDateWatcher);

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
                getActivity().finish();
            }
        });
        return view;
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

    public void checkCorrectDataEntered(){
        if(parentNameEntered && parentPhoneNumberEntered && parentEmailEntered &&
                parentPasswordEntered && parentPasswordRepeatEntered &&
                childNameEntered && childBirthDateSelected){
            signUpViewHolder.nextButton.setTextColor(getResources().getColor(R.color.colorAccent));
            signUpViewHolder.nextButton.setEnabled(true);
        }
        else {
            signUpViewHolder.nextButton.setTextColor(getResources().getColor(R.color.textColorPrimary));
            signUpViewHolder.nextButton.setEnabled(false);
        }
    }
}
