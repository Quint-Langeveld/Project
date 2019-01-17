package com.example.qlangeveld.testit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

public class InputActivity extends AppCompatActivity {

    private String challenge;

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

        finish();
    }

}
