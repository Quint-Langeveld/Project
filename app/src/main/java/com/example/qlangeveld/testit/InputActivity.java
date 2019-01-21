package com.example.qlangeveld.testit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity implements View.OnClickListener{

    private String challenge;


    // based on BRON: https://stackoverflow.com/questions/937313/fling-gesture-detection-on-grid-layout
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Intent intent = getIntent();
        challenge = (String) intent.getSerializableExtra("challenge");

        TextView textChallenge = findViewById(R.id.challengeText);
        textChallenge.setText(challenge);


        // based on BRON: https://stackoverflow.com/questions/937313/fling-gesture-detection-on-grid-layout
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        };

    }

    public void onGOClicked(View view) {
        int succeeded;

        Switch succesSwitch = findViewById(R.id.succesSwitch);
        if (succesSwitch.isChecked()) {
            succeeded = 1;
        } else {
            succeeded = 0;
        }

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        float rating = ratingBar.getRating();

        Value newValue = new Value(challenge, succeeded, rating);

        EntryDatabase.getInstance(getApplicationContext()).insertValue(newValue);

        finish();
    }

    public void onProcessClicked() {
        Intent intent = new Intent(InputActivity.this, GraphActivity.class);
        startActivity(intent);
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
                    Toast.makeText(getApplicationContext(),"swipe left", Toast.LENGTH_LONG).show();

                    onProcessClicked();

                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(getApplicationContext(),"swipe right", Toast.LENGTH_LONG).show();

//                    onSwipeRight();

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
        Toast.makeText(this, "HOOI", Toast.LENGTH_LONG).show();
    }
}
