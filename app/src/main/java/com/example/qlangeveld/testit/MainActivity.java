package com.example.qlangeveld.testit;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
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


// Activity containing two Adapters to show a list of ongoing challenges and finished challenges
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


    // Set new gestureListener, OnItemClickListener, OnItemLongClickListener, a link with the database and the adapters
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


    // Connects the button to onFinishedClicked
    public void onFinishedClicked(View view) {
        onFinishedClicked();
    }


    // Connects the button to onGoingClicked
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


    // does nothing, but is required
    @Override
    public void onClick(View v) {
    }


    // Swipes to the left activates onFinishedClicked
    private void onSwipeLeft() {
        onFinishedClicked();
    }


    // Swipes to the right activates onGoingClicked
    private void onSwipeRight() {
        onGoingClicked();
    }


    // ListClickListener for the listView
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
                // pop up??
                String fillin = cursor.getString(cursor.getColumnIndex("fillin"));
                if (fillin.equals("locked")) {
                    showSimpleLockedPopUp();
                } else {
                    Intent intent = new Intent(MainActivity.this, InputActivity.class);
                    intent.putExtra("challenge", challenge);
                    startActivity(intent);
                }

            }
        }
    }


    // LongCLickListener for the listView
    private class OnItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            showSimplePopUp(parent, position);

            return true;
        }
    }


    // pop up message to delete challenge from list (BRON: http://www.androiddom.com/2011/06/displaying-android-pop-up-dialog.html)
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


    // When user wants to view the ongoing challenges
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


    // When user wants to view the finished challenges
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


    // Add a challenge
    public void onFloatingButtonClicked(View view) {
        Intent intent = new Intent(MainActivity.this, EntryActivity.class);
        startActivity(intent);
    }


    // Is called when returns to the MainActivity from an other activity
    @Override
    protected void onResume() {
        super.onResume();
        upDateData();
    }


    // Updates the curser so the listView is up to date
    private void upDateData() {
        db = EntryDatabase.getInstance(this);
        Cursor curs = db.selectOngoing();
        entryAdapter.swapCursor(curs);
    }


    // pop up message prevents user from putting in data to early (BRON: http://www.androiddom.com/2011/06/displaying-android-pop-up-dialog.html)
    private void showSimpleLockedPopUp() {
        String message = "but you still have to wait a little time..";

        // the message
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Good motivation!");

        helpBuilder.setMessage(message);

        // when really to delete it
        helpBuilder.setPositiveButton("Ok, I'll wait",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // go back
                    }
                });

        // Remember, create doesn't show the dialog=
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }


}
