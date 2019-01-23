package com.example.qlangeveld.testit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EntryDatabase extends SQLiteOpenHelper {

    private static EntryDatabase instance;


    public static EntryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new EntryDatabase(context, "entries", null, 1 );
        }
        return instance;
    }

    private EntryDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createEntries = "create table entries (_id INTEGER PRIMARY KEY AUTOINCREMENT, challenge TEXT, amountOfTime TEXT, periodOfTime TEXT, amountOfNotifications, periodOfNotifications, state TEXT, repeat TEXT, Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";
        String createItemValues = "create table itemValues (_id INTEGER PRIMARY KEY AUTOINCREMENT, challenge TEXT, succeeded INT, feeling INT, Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createEntries);
        db.execSQL(createItemValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "entries");
        onCreate(db);
    }

    public Cursor selectAll() {
        String select = "SELECT * FROM entries";
        Cursor cursor = this.getWritableDatabase().rawQuery(select,  null);
        return cursor;
    }


    public Cursor selectFinished() {
        String allFinished = "SELECT * FROM entries WHERE state='finished'";
        Cursor curs = this.getWritableDatabase().rawQuery(allFinished, null);
        return curs;
    }


    public Cursor selectPieChart(String challenge) {
        String selectPie = "SELECT succeeded FROM itemValues WHERE challenge='" + challenge + "'";
        Cursor pieCursor = this.getWritableDatabase().rawQuery(selectPie, null);
        return pieCursor;
    }


    public Cursor selectFeeling(String challenge) {
        String selectFeeling = "SELECT feeling FROM itemValues WHERE challenge='" + challenge + "'";
        Cursor feelCursor = this.getWritableDatabase().rawQuery(selectFeeling, null);
        return feelCursor;
    }


    public void insert(Challenge challenge) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();


        cv.put("challenge",challenge.getChallege());
        cv.put("amountOfTime", challenge.getAmountOfTime());
        cv.put("periodOfTime", challenge.getPeriodOfTime());
        cv.put("amountOfNotifications", challenge.getAmountOfNotifications());
        cv.put("periodOfNotifications", challenge.getPeriodOfNotifications());
        cv.put("state", challenge.getState());
        cv.put("repeat", challenge.getRepeat());

        db.insert("entries", null, cv);
    }

    public void insertValue(Value value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("challenge", value.getChallenge());
        cv.put("succeeded", value.getSucceeded());
        cv.put("feeling", value.getFeeling());

        db.insert("itemValues", null, cv);
    }


    public void removeID(long id) {
        SQLiteDatabase dB = this.getReadableDatabase();
        String ide = String.valueOf(id);

        dB.delete("entries", "_id=?", new String[]{ide});
    }

}