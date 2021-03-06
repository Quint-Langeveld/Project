package com.example.qlangeveld.testit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;


// This Activity handles the user to create a new Challenge
public class EntryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String title;

    private int amountOfTime = 1;
    private String amountOfTimeString = "1";
    private String periodOfTime;

    private int amountOfNotifications = 1;
    private String amountOfNotificationsString = "1";
    private String periodOfNotifications = "a day";

    private String repeating;

    private NotificationManagerCompat notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);


        // Handles the text in the spinners and the seekbars
        setUpSpinners();

        notificationManager = NotificationManagerCompat.from(this);
    }


    // User ends the Actifity by submitting his/her input
    public void onDoneClicked(View view) {
        TextView name = findViewById(R.id.title);
        title = name.getText().toString();

        String state = "ongoing";

        // makes the string that shall be showed per Challenge in the listView of the MainActivity
        makeRepeatString();

        int progress = 0;
        String fillin = "free";

        Challenge challenge = new Challenge(title, amountOfTime, periodOfTime, amountOfNotifications, progress, state, repeating, fillin);

        // inserts the new challenge in the database
        EntryDatabase.getInstance(getApplicationContext()).insert(challenge);

        // creates the notification per challenge
        setNotifications();

        finish();
    }

    // This creates the notification Manger object
    private void setNotifications() {
        //from BRON: https://stackoverflow.com/questions/23440251/how-to-repeat-notification-daily-on-specific-time-in-android-through-background
        if (amountOfNotifications == 1) {
            sendOneNote();
        } else if (amountOfNotifications == 2){
            sendTwoNotes();
        } else {
            sendThreeNotes();
        }
    }

    // Method setNotifications redirects these three methods
    private void sendOneNote() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(EntryActivity.this, AlarmReciever.class);
        intent1.putExtra("title", title);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(EntryActivity.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) EntryActivity.this.getSystemService(EntryActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
    private void sendTwoNotes() {

        // first notification
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(EntryActivity.this, AlarmReciever.class);
        intent1.putExtra("title", title);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(EntryActivity.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) EntryActivity.this.getSystemService(EntryActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        // second notifiction
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 18);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        Intent intent2 = new Intent(EntryActivity.this, AlarmReciever.class);
        intent1.putExtra("title", title);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(EntryActivity.this, 0,intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am2 = (AlarmManager) EntryActivity.this.getSystemService(EntryActivity.this.ALARM_SERVICE);
        am2.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent2);

    }
    private void sendThreeNotes() {

        // first notification
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(EntryActivity.this, AlarmReciever.class);
        intent1.putExtra("title", title);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(EntryActivity.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) EntryActivity.this.getSystemService(EntryActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        // second notifiction
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 15);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        Intent intent2 = new Intent(EntryActivity.this, AlarmReciever.class);
        intent2.putExtra("title", title);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(EntryActivity.this, 0,intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am2 = (AlarmManager) EntryActivity.this.getSystemService(EntryActivity.this.ALARM_SERVICE);
        am2.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent2);

        // third notification
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(Calendar.HOUR_OF_DAY, 20);
        calendar3.set(Calendar.MINUTE, 0);
        calendar3.set(Calendar.SECOND, 0);
        Intent intent3 = new Intent(EntryActivity.this, AlarmReciever.class);
        intent3.putExtra("title", title);
        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(EntryActivity.this, 0,intent3, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am3 = (AlarmManager) EntryActivity.this.getSystemService(EntryActivity.this.ALARM_SERVICE);
        am3.setRepeating(AlarmManager.RTC_WAKEUP, calendar3.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent3);

    }


    // Detects the user input for the amount of days/weeks/months to try the challenge
    private class SeekBarTimeClickListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            SeekBar mySeekBar = findViewById(R.id.seekBarTime);
            mySeekBar.setProgress(progress);

            TextView nrOfTime = findViewById(R.id.howLong);
            int progressToShow = progress + 1;
            String amountString = Integer.toString(progressToShow);
            nrOfTime.setText(amountString);

            amountOfTime = progressToShow;
            amountOfTimeString = amountString;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }


    // Detects the user input of the amount of notifications to receive
    private class SeekBarAmountClickListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            SeekBar mySeekBar = findViewById(R.id.seekBarAmount);
            mySeekBar.setProgress(progress);

            TextView nrOfAmount = findViewById(R.id.howOften);
            int amountToShow = progress + 1;
            String amountString = Integer.toString(amountToShow);
            nrOfAmount.setText(amountString);

            amountOfNotifications = amountToShow;
            amountOfNotificationsString = amountString;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }


    // Sets up the text in the spinners and the seekbars
    private void setUpSpinners() {
        // For the time seekbar
        SeekBar seekBarTime = findViewById(R.id.seekBarTime);
        seekBarTime.setOnSeekBarChangeListener(new SeekBarTimeClickListener());

        // For the amount seekbar
        SeekBar seekBarAmount = findViewById(R.id.seekBarAmount);
        seekBarAmount.setOnSeekBarChangeListener(new SeekBarAmountClickListener());

        // For the spinner
        Spinner spinner1 = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.periods, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);
    }


    // Detects the user's choice of challenges' period
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        switch (text) {
            case "a day": {
                periodOfNotifications = text;
            } break;

            case "a week": {
                periodOfNotifications = text;
            } break;

            case "a month": {
                periodOfNotifications = text;
            } break;

            case "Days": {
                periodOfTime = text;
            } break;

            case "Weeks": {
                periodOfTime = text;
            } break;

            case "Months": {
                periodOfTime = text;
            } break;
        }
    }


    // When no choice is made
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    // Creates a string that is shown in the listView of the MainActivity
    private void makeRepeatString() {
        if (periodOfNotifications.equals("a day")) {
            if (amountOfNotificationsString.equals("1")) {
                repeating = "every day";
            } else {
                repeating = amountOfNotifications + " times a day";
            }
        }

        // and add the time span to the string
        if (periodOfTime.equals("Days")) {
            if (amountOfTimeString.equals("1")) {
                repeating = repeating + ", for 1 day";
            } else {
                repeating = repeating + ", for " + amountOfTime + " days";
            }
        } else if (periodOfTime.equals("Weeks")) {
            if (amountOfTimeString.equals("1")) {
                repeating = repeating + ", for 1 week";
            } else {
                repeating = repeating + ", for " + amountOfTime + " weeks";
            }
        } else if (periodOfTime.equals("Months")) {
            if (amountOfTimeString.equals("1")) {
                repeating = repeating + ", for 1 month";
            } else {
                repeating = repeating + ", for " + amountOfTime + " months";
            }
        }
    }
}
