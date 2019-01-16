package com.example.qlangeveld.testit;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.qlangeveld.testit.App.CHANNEL_1_ID;


public class EntryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String title;

    private String amountOfTime = "1";
    private String periodOfTime;

    private String amountOfNotifications = "1";
    private String periodOfNotifications;

    private String repeating;

    private NotificationManagerCompat notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        setUpSpinners();

        notificationManager = NotificationManagerCompat.from(this);

    }

    public void onDoneClicked(View view) {
        TextView name = findViewById(R.id.title);
        title = name.getText().toString();

        String state = "ongoing";

        makeRepeatString();

        Challenge challenge = new Challenge(title, amountOfTime, periodOfTime, amountOfNotifications, periodOfNotifications, state, repeating);

        EntryDatabase.getInstance(getApplicationContext()).insert(challenge);

        sendNotificationChannel1();

        finish();
    }


    private void sendNotificationChannel1() {
        Intent activityIntent = new Intent(this, InputActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle("Test it!")
                .setContentText(title)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setVibrate(new long[] { 1000, 1000})
                .setContentIntent(contentIntent)
                .build();

        notificationManager.notify(1, notification);
    }

    private class SeekBarTimeClickListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            SeekBar mySeekBar = findViewById(R.id.seekBarTime);
            mySeekBar.setProgress(progress);

            TextView nrOfTime = findViewById(R.id.howLong);
            int progressToShow = progress + 1;
            String amountString = Integer.toString(progressToShow);
            nrOfTime.setText(amountString);

            amountOfTime = amountString;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }


    private class SeekBarAmountClickListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            SeekBar mySeekBar = findViewById(R.id.seekBarAmount);
            mySeekBar.setProgress(progress);

            TextView nrOfAmount = findViewById(R.id.howOften);
            int amountToShow = progress + 1;
            String amountString = Integer.toString(amountToShow);
            nrOfAmount.setText(amountString);

            amountOfNotifications = amountString;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }


    private void setUpSpinners() {
        // for the time seekbar
        SeekBar seekBarTime = findViewById(R.id.seekBarTime);
        seekBarTime.setOnSeekBarChangeListener(new SeekBarTimeClickListener());

        // for the amount seekbar
        SeekBar seekBarAmount = findViewById(R.id.seekBarAmount);
        seekBarAmount.setOnSeekBarChangeListener(new SeekBarAmountClickListener());

        // spinner 1
        Spinner spinner1 = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.periods, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        //spinner 2
        Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.aPeriod, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        switch (text) {
            case "a day": {
                periodOfNotifications = text;
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            } break;

            case "a week": {
                periodOfNotifications = text;
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            } break;

            case "a month": {
                periodOfNotifications = text;
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            } break;

            case "Days": {
                periodOfTime = text;
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            } break;

            case "Weeks": {
                periodOfTime = text;
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            } break;

            case "Months": {
                periodOfTime = text;
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            } break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void makeRepeatString() {
        if (periodOfNotifications.equals("a day")) {
            if (amountOfNotifications.equals("1")) {
                repeating = "every day";
            } else {
                repeating = amountOfNotifications + " times a day";
            }
        } else if (periodOfNotifications.equals("a week")) {
            if (amountOfNotifications.equals("1")) {
                repeating = "every week";
            } else {
                repeating = amountOfNotifications + " times a week";
            }
        } else if (periodOfNotifications.equals("a month")) {
            if (amountOfNotifications.equals("1")) {
                repeating = "every month";
            } else {
                repeating = amountOfNotifications + " times a month";
            }
        }

        // and add the time span to the string
        if (periodOfTime.equals("Days")) {
            if (amountOfTime.equals("1")) {
                repeating = repeating + ", for 1 day";
            } else {
                repeating = repeating + ", for " + amountOfTime + " days";
            }
        } else if (periodOfTime.equals("Weeks")) {
            if (amountOfTime.equals("1")) {
                repeating = repeating + ", for 1 week";
            } else {
                repeating = repeating + ", for " + amountOfTime + " weeks";
            }
        } else if (periodOfTime.equals("Months")) {
            if (amountOfTime.equals("1")) {
                repeating = repeating + ", for 1 month";
            } else {
                repeating = repeating + ", for " + amountOfTime + " months";
            }
        }
    }
}
