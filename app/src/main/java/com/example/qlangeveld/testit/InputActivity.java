package com.example.qlangeveld.testit;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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


        // pop up??
        Cursor cursor = db.selectFillin(challenge);
        cursor.moveToFirst();
        String fillin = cursor.getString(cursor.getColumnIndex("fillin"));
        cursor.close();

        if (fillin.equals("locked")) {
            showSimplePopUp();
        }
    }


    // based on BRON: http://www.androiddom.com/2011/06/displaying-android-pop-up-dialog.html
    private void showSimplePopUp() {
        String message = "but you still have to wait a little time..";

        // the message
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Good motivation!");

        helpBuilder.setMessage(message);

        // when really to delete it
        helpBuilder.setPositiveButton("Ok, go back",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // to remove it
                        finish();
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
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

        EntryDatabase.getInstance(getApplicationContext()).setFillinTolocked(challenge);

        finish();
    }


    private void setProgress(int totalProgress, int nowProgress) {

        float percent = (nowProgress * 100.0f) / totalProgress;
        int newPercent = Math.round(percent);

        Log.d("progress", "setProgress: " + newPercent);

        EntryDatabase.getInstance(getApplicationContext()).setProgress(newPercent, challenge);
    }


    private void isChallengeFinished() {

        // Check if challenge is completed!
        Cursor selectTimeCursor = db.selectTimeOfChallenge(challenge);
        Cursor selectPeriodCursor = db.selectPeriodOfChallenge(challenge);
        Cursor selectAmountOfNotificationsCursor = db.selectAmountOfNotifications(challenge);


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


        // and check how often the challenge already is filled in
        Cursor selectPieChartCursor = db.selectPieChart(challenge);
        int counter = 0;
        if (selectPieChartCursor.moveToFirst()) {
            do {
                counter += 1;
            } while (selectPieChartCursor.moveToNext());
        }
        selectPieChartCursor.close();

        setProgress(totalProgress, counter);

        if (counter >= totalProgress) {
            EntryDatabase.getInstance(getApplicationContext()).toFinished(challenge);
        }
    }


    public void onProgressClicked(View view) {
        Intent intent = new Intent(InputActivity.this, GraphActivity.class);
        intent.putExtra("challenge", challenge);
        startActivity(intent);
    }

}
