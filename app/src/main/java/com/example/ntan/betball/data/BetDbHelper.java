package com.example.ntan.betball.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ntan.betball.data.BetContract.BetEntry;

/**
 * Database for application. Manages database creation and version management.
 */
public class BetDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = BetDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "betball.db";

    private static final int DATABASE_VERSION = 1;

    public BetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_BET_TABLE =  "CREATE TABLE " + BetEntry.TABLE_NAME + " ("
                + BetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BetEntry.COLUMN_TEAM_NAME + " TEXT NOT NULL, "
                + BetEntry.COLUMN_TEAM_MATCH + " INTEGER NOT NULL, "
                + BetEntry.COLUMN_TEAM_WIN + " INTEGER NOT NULL, "
                + BetEntry.COLUMN_TEAM_DRAW + " INTEGER NOT NULL, "
                + BetEntry.COLUMN_TEAM_LOSE + " INTEGER NOT NULL, "
                + BetEntry.COLUMN_TEAM_GOALWIN + " INTEGER NOT NULL, "
                + BetEntry.COLUMN_TEAM_GOALLOSE + " INTEGER NOT NULL, "
                + BetEntry.COLUMN_TEAM_POINT + " INTEGER NOT NULL);";


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_BET_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}