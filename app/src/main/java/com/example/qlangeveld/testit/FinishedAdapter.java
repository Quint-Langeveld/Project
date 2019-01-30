package com.example.qlangeveld.testit;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


// adapter for the listView of the finished challenges at the MainActivity
public class FinishedAdapter extends ResourceCursorAdapter {


    public FinishedAdapter(Context context, Cursor cursor) {
        super(context, R.layout.row_item, cursor);
    }


    // Binds together the name, frequency and the progress of the challenge
    @Override
    public void bindView(View convertView, Context context, Cursor cursor) {
        String state = cursor.getString((cursor.getColumnIndex("state")));

        // Makes sure just to show the ongoing challenges
        if (state.equals("finished")) {

            // For the name
            String textTitle = cursor.getString(cursor.getColumnIndex("challenge"));
            TextView title = convertView.findViewById(R.id.challengeRowItem);
            title.setText(textTitle);

            // For the frequency
            String repeatText = cursor.getString(cursor.getColumnIndex("repeat"));
            TextView repeat = convertView.findViewById(R.id.repeatText);
            repeat.setText(repeatText);

            // For the progress
            ProgressBar progressBar = convertView.findViewById(R.id.progressBar2);
            progressBar.setProgress(100);
        }

    }
}