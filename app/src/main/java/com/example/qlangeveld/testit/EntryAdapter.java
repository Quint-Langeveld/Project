package com.example.qlangeveld.testit;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EntryAdapter extends ResourceCursorAdapter {

    public EntryAdapter(Context context, Cursor cursor) {
        super(context, R.layout.row_item, cursor);
    }


    @Override
    public void bindView(View convertView, Context context, Cursor cursor)  {
        String state = cursor.getString((cursor.getColumnIndex("state")));

        if (state.equals("ongoing")) {
            String textTitle = cursor.getString(cursor.getColumnIndex("challenge"));
            TextView title = convertView.findViewById(R.id.challengeRowItem);
            title.setText(textTitle);

            String repeatText = cursor.getString(cursor.getColumnIndex("repeat"));
            TextView repeat = convertView.findViewById(R.id.repeatText);
            repeat.setText(repeatText);
        }
    }
}


