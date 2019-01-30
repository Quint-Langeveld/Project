package com.example.qlangeveld.testit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EntryDatabase db;
    private EntryAdapter entryAdapter;
    private FinishedAdapter finishedAdapter;


    // based on BRON: https://stackoverflow.com/questions/937313/fling-gesture-detection-on-grid-layout
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button finished = findViewById(R.id.finishedButton);
        finished.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.littleMoreBlue)));


        db = EntryDatabase.getInstance(this);
        Cursor curs = db.selectOngoing();
        entryAdapter = new EntryAdapter(this, curs);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(entryAdapter);

        listView.setOnItemClickListener(new ListItemClickListener());
        listView.setOnItemLongClickListener(new OnItemLongClickListener());

        // based on BRON: https://stackoverflow.com/questions/937313/fling-gesture-detection-on-grid-layout
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        listView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });

    }

    public void onFinishedClicked(View view) {
        onFinishedClicked();
    }

    public void onGoingClicked(View view) {
        onGoingClicked();
    }


    // based on BRON: https://stackoverflow.com/questions/937313/fling-gesture-detection-on-grid-layout
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    onSwipeLeft();

                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    onSwipeRight();

                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
    }


    private void onSwipeLeft() {
        onFinishedClicked();
    }


    private void onSwipeRight() {
        onGoingClicked();
    }


    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);

            String challenge = cursor.getString(cursor.getColumnIndex("challenge"));
            String name = cursor.getString(cursor.getColumnIndex("state"));

            if (name.equals("finished")) {
                Intent intent = new Intent(MainActivity.this, GraphActivity.class);
                intent.putExtra("challenge", challenge);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                intent.putExtra("challenge", challenge);
                startActivity(intent);
            }
        }
    }


    private class OnItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            showSimplePopUp(parent, position);

            return true;
        }
    }


    // based on BRON: http://www.androiddom.com/2011/06/displaying-android-pop-up-dialog.html
    private void showSimplePopUp(AdapterView<?> parent, int position) {
        Cursor cursor = (Cursor) parent.getItemAtPosition(position);
        final long _id = cursor.getInt(cursor.getColumnIndex("_id"));
        String title = cursor.getString(cursor.getColumnIndex("challenge"));

        String message = "Are you sure you want to delete " + title + " ?";

        // the message
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Delete challenge?");

        helpBuilder.setMessage(message);

        // when really to delete it
        helpBuilder.setPositiveButton("Yes, delete",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // to remove it
                        db.removeID(_id);
                        upDateData();
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }


    public void onGoingClicked() {

        Button finished = findViewById(R.id.finishedButton);
        Button ongoing = findViewById(R.id.ongoingButton);
        finished.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.littleMoreBlue)));
        ongoing.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.tintedBlue)));


        Cursor curs = db.selectOngoing();
        entryAdapter = new EntryAdapter(this, curs);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(entryAdapter);
    }


    public void onFinishedClicked() {

        Button ongoing = findViewById(R.id.ongoingButton);
        Button finished = findViewById(R.id.finishedButton);
        ongoing.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.littleMoreBlue)));
        finished.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.tintedBlue)));

        Cursor curs = db.selectFinished();
        finishedAdapter = new FinishedAdapter(this, curs);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(finishedAdapter);
    }


    public void onFloatingButtonClicked(View view) {
        Intent intent = new Intent(MainActivity.this, EntryActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        upDateData();
    }


    private void upDateData() {
        db = EntryDatabase.getInstance(this);
        Cursor curs = db.selectOngoing();
        entryAdapter.swapCursor(curs);
    }


}
