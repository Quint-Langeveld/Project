package com.example.qlangeveld.testit;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private EntryDatabase db;
    private EntryAdapter entryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = EntryDatabase.getInstance(this);
        Cursor curs = db.selectAll();
        entryAdapter = new EntryAdapter(this, curs);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(entryAdapter);

        listView.setOnItemClickListener(new ListItemClickListener());
        listView.setOnItemLongClickListener(new OnItemLongClickListener());
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);

            String challenge = cursor.getString(cursor.getColumnIndex("challenge"));

            Intent intent = new Intent(MainActivity.this, InputActivity.class);
//            intent.putExtra("challenge", challenge);
            startActivity(intent);
        }
    }


    private class OnItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            showSimplePopUp(parent, position);

            return true;
        }
    }

    // based on BRON: http://www.androiddom.com/2011/06/displaying-android-pop-up-dialog.html
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

    @Override
    protected void onResume() {
        super.onResume();
        upDateData();
    }


    public void onFloatingButtonClicked(View view) {
        Intent intent = new Intent(MainActivity.this, EntryActivity.class);
        startActivity(intent);
    }


    private void upDateData() {
        db = EntryDatabase.getInstance(this);
        Cursor curs = db.selectAll();
        entryAdapter.swapCursor(curs);
    }
}
