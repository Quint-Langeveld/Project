package com.example.qlangeveld.testit;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("by Quint");
        }


    // BRONNNN
//    // create an action bar button
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    // handle button activities
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.mybutton) {
//            TextView text = findViewById(R.id.textView);
//            text.setText("On Add clicked");
//        }
//        return super.onOptionsItemSelected(item);
//    }


    public void onFloatingButtonClicked(View view) {
        Intent intent = new Intent(MainActivity.this, EntryActivity.class);
        startActivity(intent);
    }
}
