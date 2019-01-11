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

        int _Id = cursor.getInt(cursor.getColumnIndex("_id"));
        String textTitle = cursor.getString(cursor.getColumnIndex("title"));
        String textContext = cursor.getString(cursor.getColumnIndex("content"));
        String moodString =  cursor.getString(cursor.getColumnIndex("mood"));
        String intDate = cursor.getString(cursor.getColumnIndex("Timestamp"));

//        if (moodString.equals("happy")) {
//            ((ImageView) convertView.findViewById(R.id.plaatje)).setImageResource(R.drawable.happy);
//        } else if (moodString.equals("sad")){
//            ((ImageView) convertView.findViewById(R.id.plaatje)).setImageResource(R.drawable.sad);
//        } else if (moodString.equals("neutral")) {
//            ((ImageView) convertView.findViewById(R.id.plaatje)).setImageResource(R.drawable.surprised);
//        }
//
//        TextView Title = convertView.findViewById(R.id.Title);
//        Title.setText(textTitle);
//
//        TextView Context = convertView.findViewById(R.id.context);
//        Context.setText(textContext);
//
//        TextView Date = convertView.findViewById(R.id.daate);
//        Date.setText(intDate);
    }
}


