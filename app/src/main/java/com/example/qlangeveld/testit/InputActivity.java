package com.example.qlangeveld.testit;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;


// This Activity handles the input of the user when he/she enters how they feel and if they succeeded
public class InputActivity extends AppCompatActivity{

    private String challenge;
    private EntryDatabase db = EntryDatabase.getInstance(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Intent intent = getIntent();
        challenge = (String) intent.getSerializableExtra("challenge");

        TextView textChallenge = findViewById(R.id.challengeText);
        textChallenge.setText(challenge);

    }


    // User commits input
    public void onGOClicked(View view) {

        // Detects if user succeeded
        int succeeded;
        Switch succesSwitch = findViewById(R.id.succesSwitch);
        if (succesSwitch.isChecked()) {
            succeeded = 1;
        } else {
            succeeded = 0;
        }


        // Detection of the feeling of the user
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        float rating = ratingBar.getRating();

        Value newValue = new Value(challenge, succeeded, rating);

        // Inserts a new item in 'itemValues' table of the local database
        EntryDatabase.getInstance(getApplicationContext()).insertValue(newValue);

        // Checks if challenge has to be changed to 'finished'
        isChallengeFinished();

        // Locks the input page of this challenge until it is switched back to 'free' by the TimeManager class
        EntryDatabase.getInstance(getApplicationContext()).setFillinTolocked(challenge);

        finish();
    }


    // Inserts the progress in the database
    private void setProgress(int totalProgress, int nowProgress) {

        float percent = (nowProgress * 100.0f) / totalProgress;
        int newPercent = Math.round(percent);

        EntryDatabase.getInstance(getApplicationContext()).setProgress(newPercent, challenge);
    }


    // Hnadles the progress of a challenge
    private void isChallengeFinished() {

        // Check if challenge is completed!
        int totalProgress = isCompleted();

        // Calculates the progress in percentage and inserts is in the database
        checkProgress(totalProgress);
    }


    // Calculates and returns the total amount of input of a challenge
    private int isCompleted() {

        // Select cursor
        Cursor selectTimeCursor = db.selectTimeOfChallenge(challenge);
        Cursor selectPeriodCursor = db.selectPeriodOfChallenge(challenge);
        Cursor selectAmountOfNotificationsCursor = db.selectAmountOfNotifications(challenge);

        // Retrieve data
        selectPeriodCursor.moveToFirst();
        String PeriodOfChallenge = selectPeriodCursor.getString(selectPeriodCursor.getColumnIndex("periodOfTime"));
        selectPeriodCursor.close();

        selectTimeCursor.moveToFirst();
        int TimeOfChallenge = selectTimeCursor.getInt(selectTimeCursor.getColumnIndex("amountOfTime"));
        selectTimeCursor.close();

        selectAmountOfNotificationsCursor.moveToFirst();
        int amountOfNOtifications = selectAmountOfNotificationsCursor.getInt(selectAmountOfNotificationsCursor.getColumnIndex("amountOfNotifications"));
        selectAmountOfNotificationsCursor.close();


        // check how often the challenged had to be filled in
        int totalProgress;

        if (PeriodOfChallenge.equals("Days")) {

            if (amountOfNOtifications == 2) {
                totalProgress = TimeOfChallenge * 2;
            } else if (amountOfNOtifications == 3) {
                totalProgress = TimeOfChallenge * 3;
            } else {
                totalProgress = TimeOfChallenge;
            }

        } else if (PeriodOfChallenge.equals("Weeks")) {

            totalProgress = TimeOfChallenge * 7;

            if (amountOfNOtifications == 2) {
                totalProgress = totalProgress * 2;
            } else if (amountOfNOtifications == 3) {
                totalProgress = TimeOfChallenge * 3;
            }

        } else {
            totalProgress = TimeOfChallenge * 30;

            if (amountOfNOtifications == 2) {
                totalProgress = totalProgress * 2;
            } else if (amountOfNOtifications == 3) {
                totalProgress = TimeOfChallenge * 3;
            }
        }

        return totalProgress;
    }


    // Inserts the progress of a challenge in percentage in the database
    private void checkProgress(int totalProgress) {

        Cursor selectPieChartCursor = db.selectPieChart(challenge);
        int counter = 0;
        if (selectPieChartCursor.moveToFirst()) {
            do {
                counter += 1;
            } while (selectPieChartCursor.moveToNext());
        }
        selectPieChartCursor.close();

        setProgress(totalProgress, counter);

        // Inserts into the database
        if (counter >= totalProgress) {
            EntryDatabase.getInstance(getApplicationContext()).toFinished(challenge);
        }
    }


    // Redirects user to the GraphActivity to show an update of the progress
    public void onProgressClicked(View view) {
        Intent intent = new Intent(InputActivity.this, GraphActivity.class);
        intent.putExtra("challenge", challenge);
        startActivity(intent);
    }

}
