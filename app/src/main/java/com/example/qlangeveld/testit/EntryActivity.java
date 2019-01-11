package com.example.qlangeveld.testit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EntryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String amountOfTime;
    private String periodOfTime;

    private String amountOfNotifications;
    private String periodOfNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        setUpSpinners();
    }

    public void onDoneClicked(View view) {
        TextView name = findViewById(R.id.title);
        String title = name.getText().toString();

        String state = "ongoing";

        Challenge challenge = new Challenge(title, amountOfTime, periodOfTime, amountOfNotifications, periodOfNotifications, state);

        EntryDatabase.getInstance(getApplicationContext()).insert(challenge);

        finish();
    }


    private class SeekBarTimeClickListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            SeekBar mySeekBar = findViewById(R.id.seekBarTime);
            mySeekBar.setProgress(progress);

            TextView nrOfTime = findViewById(R.id.howLong);
            String amountString = Integer.toString(progress);
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
            String amountString = Integer.toString(progress);
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
}
