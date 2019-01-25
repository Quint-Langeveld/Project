package com.example.qlangeveld.testit;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FinishedAdapter extends ResourceCursorAdapter {


    public FinishedAdapter(Context context, Cursor cursor) {
        super(context, R.layout.row_item, cursor);
    }


    @Override
    public void bindView(View convertView, Context context, Cursor cursor) {
        String state = cursor.getString((cursor.getColumnIndex("state")));

        if (state.equals("finished")) {
            String textTitle = cursor.getString(cursor.getColumnIndex("challenge"));
            TextView title = convertView.findViewById(R.id.challengeRowItem);
            title.setText(textTitle);

            String repeatText = cursor.getString(cursor.getColumnIndex("repeat"));
            TextView repeat = convertView.findViewById(R.id.repeatText);
            repeat.setText(repeatText);

            ProgressBar progressBar = convertView.findViewById(R.id.progressBar2);
            progressBar.setProgress(100);
        }

    }
}