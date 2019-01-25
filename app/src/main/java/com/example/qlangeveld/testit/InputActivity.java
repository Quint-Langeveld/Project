package com.example.qlangeveld.testit;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

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

        isChallengeFinished();

        finish();
    }


    private void isChallengeFinished() {

        // Check if challenge is completed!
        Cursor selectTimeCursor = db.selectTimeOfChallenge(challenge);
        Cursor selectPeriodCursor = db.selectPeriodOfChallenge(challenge);
        Cursor amountOfNotificationsCursor = db.selectAmountOfNotifications(challenge);
        Cursor PeriodOfNotificationsCursor = db.selectPeriodOfNotifications(challenge);

        Log.d(challenge, "isChallengeFinished: ");

        selectPeriodCursor.moveToFirst();
        String PeriodOfChallenge = selectPeriodCursor.getString(selectPeriodCursor.getColumnIndex("periodOfTime"));
        selectPeriodCursor.close();
        Log.d("1", "isChallengeFinished: ");

        selectTimeCursor.moveToFirst();
        int TimeOfChallenge = selectTimeCursor.getInt(selectTimeCursor.getColumnIndex("amountOfTime"));
        selectTimeCursor.close();
        Log.d("2", "isChallengeFinished: ");


        amountOfNotificationsCursor.moveToFirst();
        int amountOfNotifications = amountOfNotificationsCursor.getInt(amountOfNotificationsCursor.getColumnIndex("amountOfNotifications"));
        amountOfNotificationsCursor.close();
        Log.d("3", "isChallengeFinished: ");

        PeriodOfNotificationsCursor.moveToFirst();
        String PeriodOfNotifications = PeriodOfNotificationsCursor.getString(PeriodOfNotificationsCursor.getColumnIndex("periodOfNotifications"));
        PeriodOfNotificationsCursor.close();
        Log.d("4", "isChallengeFinished: ");


        // check how often the challenged had to be filled in
        int finalAmount;

        if (PeriodOfChallenge.equals("Days")) {
            if (PeriodOfNotifications.equals("a week")) {
                finalAmount = 1;
            } else if (PeriodOfNotifications.equals("a month")) {
                finalAmount = 1;
            } else {
                finalAmount = TimeOfChallenge;
            }

        } else if (PeriodOfChallenge.equals("Weeks")) {
            if (PeriodOfNotifications.equals("a day")) {
                finalAmount = TimeOfChallenge * 7;
            } else if (PeriodOfNotifications.equals("a month")) {
                finalAmount = TimeOfChallenge / 4;
            } else {
                finalAmount = TimeOfChallenge;
            }

        } else {
            if (PeriodOfNotifications.equals("a day")) {
                finalAmount = TimeOfChallenge * 30;
            } else if (PeriodOfNotifications.equals("a week")) {
                finalAmount = TimeOfChallenge * 4;
            } else {
                finalAmount = TimeOfChallenge;
            }
        }



        // and check how often the challenge already is filled in
        Cursor selectPieChartCursor = db.selectPieChart(challenge);
        int counter = 0;
        if (selectPieChartCursor.moveToFirst()) {
            do {
                counter += 1;
            } while (selectPieChartCursor.moveToNext());
        }
        selectPieChartCursor.close();


        if (counter >= finalAmount) {
            Log.d("5", "isChallengeFinished: ");
            EntryDatabase.getInstance(getApplicationContext()).toFinished(challenge);
        }


    }


    public void onProgressClicked(View view) {
        Intent intent = new Intent(InputActivity.this, GraphActivity.class);
        intent.putExtra("challenge", challenge);
        startActivity(intent);
    }

}
