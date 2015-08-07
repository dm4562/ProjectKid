package com.example.dhruv.projectkid;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.dhruv.projectkid.data.UserContract;

import java.util.ArrayList;

/**
 * Custom CursorAdapter created to populate the list in the select previous activities page
 * Created by dhruv on 6/8/15.
 */
public class MyCursorAdapter extends ResourceCursorAdapter {

    private ArrayList<Boolean> itemChecked =  new ArrayList<>();
    public MyCursorAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
        for (int i = 0; i < this.getCount(); i++) {
            itemChecked.add(i, false);
        }
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final int pos = cursor.getPosition();
        final TextView content = (TextView) view.findViewById(R.id.textView_content);
        content.setText(cursor.getString(cursor.getColumnIndexOrThrow(
                        UserContract.AllActivitiesEntry.COLUMN_NAME_ACTIVITY_NAME))
        );

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox_content);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    itemChecked.set(pos, true);
                } else {
                    itemChecked.set(pos, false);
                }
            }
        });

        checkBox.setChecked(itemChecked.get(pos));
    }
}
