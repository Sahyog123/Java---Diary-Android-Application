package com.example.diarymobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class will manage everything regarding the database
 */
public class DatabaseAdapter extends SQLiteOpenHelper {


    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DiaryEntryDB";
    private static final String DB_TABLE = "Notes";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_NOTE = "note";

    public DatabaseAdapter(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL query to create a table
        String CREATE_TABLE = "CREATE TABLE "+DB_TABLE+ "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DATE + " TEXT,"
                +KEY_NOTE + " TEXT" + ")";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Method to insert notes into the sql table
     * @param date - the date for the diary entry
     * @param note - the note for the date
     * @return
     */
    public Boolean insertNotes(String date, String note)
    {

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date",date);
        contentValues.put("note",note);

        long result = DB.insert(DB_TABLE,null, contentValues);
        //Check if insertion was successful
        if(result == -1)
        {
            return false;
        }
        else{
            return true;
        }

    }

    /**
     * This method allows to retrieve all the notes in the database
     * @return Cursor containing all the notes
     */
    public Cursor getAllNotes()
    {
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM Notes",null);
        //System.out.println(cursor);
        return cursor;
    }

    /**
     * This method allows to delete all the notes in the database
     * @return Boolean to inform about the operations status
     */
    public Boolean deleteAllNotes()
    {
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("DELETE FROM "+DB_TABLE, null);

        if(cursor.getCount()>0)
        {
            long result = DB.delete(DB_TABLE,null,null );

            if(result == -1)
            {
                return false;
            }
            else{
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Method to delete a specific note
     * @param date - The date of the note to delete
     * @return Boolean to inform the status of the operation
     */
    public Boolean deleteNote(String date)
    {
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM Notes WHERE date=?", new String[]{date});

        if(cursor.getCount()>0)
        {
            long result = DB.delete(DB_TABLE,"date=?", new String[]{date});

            if(result == -1)
            {
                return false;
            }
            else{
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Method to get filtered notes
     * @param date - The date of note to be found
     * @return Cursor containing the notes details
     */
    public Cursor getFilteredNote(String date){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM Notes where date=?", new String[]{date});
        System.out.println(cursor);
        return cursor;
    }



}
