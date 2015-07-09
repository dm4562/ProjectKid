package com.example.dhruv.projectkid;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class SignUpBirthDatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Using current date as the default date
        final Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        TextView dateView = (TextView) getActivity().findViewById(R.id.birth_date_text_view);
        String formattedDate = Utility.getFormattedDateString(year, monthOfYear, dayOfMonth);
        dateView.setText(formattedDate);
        ParentSignUpActivityFragment.newUser.setChildBirthDate(
                new GregorianCalendar(year, monthOfYear, dayOfMonth)
        );
    }
}
