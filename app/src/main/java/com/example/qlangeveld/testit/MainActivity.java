package com.example.qlangeveld.testit;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private EntryDatabase db;
    private EntryAdapter entryAdapter;
    private FinishedAdapter finishedAdapter;

    private static final int SWIPE_TRESHOLD = 100;
    private static final int SWIPE_VELOCITY_TRESHOLD = 100;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = EntryDatabase.getInstance(this);
        Cursor curs = db.selectAll();
        entryAdapter = new EntryAdapter(this, curs);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(entryAdapter);

        listView.setOnItemClickListener(new ListItemClickListener());
        listView.setOnItemLongClickListener(new OnItemLongClickListener());

        gestureDetector = new GestureDetector(this,this);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Toast.makeText(this, "jaa das down", Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }


    // based on BRON: https://www.youtube.com/watch?v=32rSs4tE-mc
    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
        boolean result = false;
        float diffY = moveEvent.getY() - downEvent.getY();
        float diffX = moveEvent.getX() - downEvent.getX();

        // swipe left and right
        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (Math.abs(diffX) > SWIPE_TRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_TRESHOLD) {
                if (diffX > 0) {
                    onSwipeRight();
                } else {
                    onSwipeLeft();
                }
                result = true;
            }
        }
        return result;
    }

    private void onSwipeLeft() {
        Toast.makeText(this, "swipe left", Toast.LENGTH_LONG).show();
        // TODO
    }


    private void onSwipeRight() {
        Toast.makeText(this, "swipe right", Toast.LENGTH_LONG).show();
        // TODO
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }


    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);

            String challenge = cursor.getString(cursor.getColumnIndex("challenge"));

            Intent intent = new Intent(MainActivity.this, InputActivity.class);
//            intent.putExtra("challenge", challenge);
            startActivity(intent);
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

    @Override
    protected void onResume() {
        super.onResume();
        upDateData();
    }


    public void onGoingClicked(View view) {
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(null);

        Cursor curs = db.selectAll();
        entryAdapter = new EntryAdapter(this, curs);

        listView.setAdapter(entryAdapter);
    }

    public void onFinishedClicked(View view) {
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(null);

        Cursor curs = db.selectAll();
        finishedAdapter = new FinishedAdapter(this, curs);

        listView.setAdapter(finishedAdapter);
    }

    public void onFloatingButtonClicked(View view) {
        Intent intent = new Intent(MainActivity.this, EntryActivity.class);
        startActivity(intent);
    }


    private void upDateData() {
        db = EntryDatabase.getInstance(this);
        Cursor curs = db.selectAll();
        entryAdapter.swapCursor(curs);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



}
