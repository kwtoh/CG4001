package com.example.user.myapplication;

/**
 * Created by user on 3/30/2015.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 11;
    private static final String DATABASE_NAME = "entries.db";

    // TABLE
    public static final String TABLE_ENTRIES = "entries";

    // COLUMNS NAME
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ENTRY_NAME = "_entryname";
    public static final String COLUMN_ENTRY_MOODSCORE = "_entrymoodscore";
    public static final String COLUMN_ENTRY_OTHERFEELING = "_entryotherfeeling";
    public static final String COLUMN_ENTRY_THOUGHTS = "_entrythoughts";
    public static final String COLUMN_ENTRY_EVENT = "_entryevent";
    public static final String COLUMN_ENTRY_ACTION = "_entryaction";
    public static final String COLUMN_ENTRY_DATE = "_entrydate";


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    // On the first time creation
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_ENTRIES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ENTRY_NAME + " TEXT, " +
                COLUMN_ENTRY_MOODSCORE + " INTEGER, " +
                COLUMN_ENTRY_OTHERFEELING + " TEXT, " +
                COLUMN_ENTRY_THOUGHTS + " TEXT, " +
                COLUMN_ENTRY_EVENT + " TEXT, " +
                COLUMN_ENTRY_ACTION + " TEXT, " +
                COLUMN_ENTRY_DATE + " TEXT " +
                ")";

        // Executing the Query
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_ENTRIES;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    // Adding a new row to the database
    public void addEntry(Entry entry){

        ContentValues values = new ContentValues();
        values.put(COLUMN_ENTRY_NAME, entry.get_entryName());
        values.put(COLUMN_ENTRY_MOODSCORE, entry.get_entryMoodScore());
        values.put(COLUMN_ENTRY_OTHERFEELING, entry.get_entryOtherFeeling());
        values.put(COLUMN_ENTRY_THOUGHTS, entry.get_entryThoughts());
        values.put(COLUMN_ENTRY_EVENT, entry.get_entryEvent());
        values.put(COLUMN_ENTRY_ACTION, entry.get_entryAction());
        values.put(COLUMN_ENTRY_DATE, entry.get_entryDate());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ENTRIES, null, values);
        db.close();

    }

    // Deleting a new row from the database
    public void deleteEntry(String entryName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ENTRIES + " WHERE " + COLUMN_ENTRY_NAME + "=\"" + entryName + "\";");
    }

    // Print out database as string
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " +  TABLE_ENTRIES + " WHERE 1";

        // Pointer to result
        Cursor c = db.rawQuery(query, null);

        //Move to first row
        c.moveToFirst();

        while(!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("_entryname"))!=null){
                dbString += c.getString(c.getColumnIndex("_entryname"));
                dbString += " ";
                dbString += c.getString(c.getColumnIndex("_entrymoodscore"));
                dbString += " ";
                dbString += c.getString(c.getColumnIndex("_entryotherfeeling"));
                dbString += " ";
                dbString += c.getString(c.getColumnIndex("_entrythoughts"));
                dbString += " ";
                dbString += c.getString(c.getColumnIndex("_entryevent"));
                dbString += " ";
                dbString += c.getString(c.getColumnIndex("_entryaction"));
                dbString += " ";
                dbString += c.getString(c.getColumnIndex("_entrydate"));
                dbString += " ";
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    public Vector<String> entryNameToString(){
        Vector<String> entries = new Vector<String>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " +  TABLE_ENTRIES + " WHERE 1";

        // Pointer to result
        Cursor c = db.rawQuery(query, null);

        //Move to first row
        c.moveToFirst();

        while(!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("_entryname"))!=null){
                entries.add(c.getString(c.getColumnIndex("_entryname")));
            }
            c.moveToNext();
        }
        db.close();
        return entries;
    }

    public Entry entryNameToEntry(String entryName){
        Entry entry = null;

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " +  TABLE_ENTRIES + " WHERE " + COLUMN_ENTRY_NAME + "=\"" + entryName + "\";";

        // Pointer to result
        Cursor c = db.rawQuery(query, null);

        //Move to first row
        c.moveToFirst();

        while(!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("_entryname"))!=null){
                entry = new Entry(
                        c.getString(c.getColumnIndex("_entryname")),
                        Integer.parseInt(c.getString(c.getColumnIndex("_entrymoodscore"))),
                        c.getString(c.getColumnIndex("_entryotherfeeling")),
                        c.getString(c.getColumnIndex("_entrythoughts")),
                        c.getString(c.getColumnIndex("_entryevent")),
                        c.getString(c.getColumnIndex("_entryaction")),
                        c.getString(c.getColumnIndex("_entrydate"))
                );
            }
            c.moveToNext();
        }
        db.close();
        return entry;
    }

    public List<Entry> databaseToArray(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " +  TABLE_ENTRIES + " WHERE 1";

        // Pointer to result
        Cursor c = db.rawQuery(query, null);

        //Move to first row
        c.moveToFirst();

        List<Entry> list = new ArrayList<Entry>();

        while(!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("_entryname"))!=null){

                Entry temp = new Entry(
                        c.getString(c.getColumnIndex("_entryname")),
                        c.getInt(c.getColumnIndex("_entrymoodscore")),
                        c.getString(c.getColumnIndex("_entryotherfeeling")),
                        c.getString(c.getColumnIndex("_entrythoughts")),
                        c.getString(c.getColumnIndex("_entryevent")),
                        c.getString(c.getColumnIndex("_entryaction")),
                        c.getString(c.getColumnIndex("_entrydate"))
                );

                list.add(temp);

            }
            c.moveToNext();
        }
        db.close();
        return list;
    }

    public String[] databaseToCSV(){

        SQLiteDatabase db = getWritableDatabase();

        List<String> list = new ArrayList<String>();

        String query = "SELECT * FROM " +  TABLE_ENTRIES + " WHERE 1";

        // Pointer to result
        Cursor c = db.rawQuery(query, null);

        //Move to first row
        c.moveToFirst();

        while(!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("_entryname"))!=null){

                String temp = "";

                temp += c.getString(c.getColumnIndex("_entryname"));
                temp += "#";
                temp += c.getString(c.getColumnIndex("_entrymoodscore"));
                temp += "#";
                temp += c.getString(c.getColumnIndex("_entryotherfeeling"));
                temp += "#";
                temp += c.getString(c.getColumnIndex("_entrythoughts"));
                temp += "#";
                temp += c.getString(c.getColumnIndex("_entryevent"));
                temp += "#";
                temp += c.getString(c.getColumnIndex("_entryaction"));
                temp += "#";
                temp += c.getString(c.getColumnIndex("_entrydate"));

                list.add(temp);
            }
            c.moveToNext();
        }
        db.close();

        String[] strList = new String[list.size()];

        list.toArray(strList);

        return strList;
    }



}
