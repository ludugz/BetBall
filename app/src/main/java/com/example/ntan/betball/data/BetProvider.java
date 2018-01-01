package com.example.ntan.betball.data;

import android.content.ContentProvider;
import android.content.Context;
import android.content.UriMatcher;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.ntan.betball.data.BetContract.BetEntry;

public class BetProvider extends ContentProvider {

    public static final String LOG_TAG = BetProvider.class.getSimpleName();

    private static final int BET = 100;

    private static final int BET_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(BetContract.CONTENT_AUTHORITY, BetContract.PATH_BET, BET);

        sUriMatcher.addURI(BetContract.CONTENT_AUTHORITY, BetContract.PATH_BET + "/#", BET_ID);
    }

    private BetDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new BetDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case BET:

                cursor = database.query(BetEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case BET_ID:

                selection = BetEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(BetEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BET:
                return BetEntry.CONTENT_LIST_TYPE;
            case BET_ID:
                return BetEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BET:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertPet(Uri uri, ContentValues values) {
        String name = values.getAsString(BetEntry.COLUMN_TEAM_NAME);
        if (name == null) {
            throw new IllegalArgumentException("A man needs a name");
        }


        Integer win = values.getAsInteger(BetEntry.COLUMN_TEAM_WIN);
        if (win == null || win < 0) {
            throw new IllegalArgumentException("A man needs a win");
        }
        Integer draw = values.getAsInteger(BetEntry.COLUMN_TEAM_DRAW);
        if (draw == null || draw < 0) {
            throw new IllegalArgumentException("A man needs a draw");
        }
        Integer lose = values.getAsInteger(BetEntry.COLUMN_TEAM_LOSE);
        if (lose == null || lose < 0) {
            throw new IllegalArgumentException("A man needs a lose");
        }
        Integer goalWin = values.getAsInteger(BetEntry.COLUMN_TEAM_GOALWIN);
        if (goalWin == null || goalWin < 0) {
            throw new IllegalArgumentException("A man needs a goalwin");
        }
        Integer goalLose = values.getAsInteger(BetEntry.COLUMN_TEAM_GOALLOSE);
        if (goalLose == null || goalLose < 0) {
            throw new IllegalArgumentException("A man needs a goallose");
        }

        Integer point = values.getAsInteger(BetEntry.COLUMN_TEAM_POINT);
        if (point == null || point < 0) {
            throw new IllegalArgumentException("A man needs a point");
        }


        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new pet with the given values
        long id = database.insert(BetEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case BET:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(BetEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted!=0){
                    getContext().getContentResolver().notifyChange(uri,null);

                }
                return rowsDeleted;
            case BET_ID:
                // Delete a single row given by the ID in the URI
                selection = BetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(BetEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted!=0){
                    getContext().getContentResolver().notifyChange(uri,null);

                }
                return rowsDeleted ;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {

            case BET:
                return updatePet(uri, contentValues, selection, selectionArgs);
            case BET_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = BetEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updatePet(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }


    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // Check that the name is not null
        String name = values.getAsString(BetEntry.COLUMN_TEAM_NAME);
        if (name == null) {
            throw new IllegalArgumentException("A man needs a name");
        }

        Integer win = values.getAsInteger(BetEntry.COLUMN_TEAM_WIN);
        if (win == null || win < 0) {
            throw new IllegalArgumentException("A man needs a win");
        }
        Integer draw = values.getAsInteger(BetEntry.COLUMN_TEAM_DRAW);
        if (draw == null || draw < 0) {
            throw new IllegalArgumentException("A man needs a draw");
        }
        Integer lose = values.getAsInteger(BetEntry.COLUMN_TEAM_LOSE);
        if (lose == null || lose < 0) {
            throw new IllegalArgumentException("A man needs a lose");
        }
        Integer goalWin = values.getAsInteger(BetEntry.COLUMN_TEAM_GOALWIN);
        if (goalWin == null || goalWin < 0) {
            throw new IllegalArgumentException("A man needs a goalWin");
        }
        Integer goalLose = values.getAsInteger(BetEntry.COLUMN_TEAM_GOALLOSE);
        if (goalLose == null || goalLose < 0) {
            throw new IllegalArgumentException("A man needs a goalLose");
        }
        Integer point = values.getAsInteger(BetEntry.COLUMN_TEAM_POINT);
        if (point == null || point < 0) {
            throw new IllegalArgumentException("A man needs a point");
        }


        // If there are no values to update, then no need to update
        if (values.size() == 0) {
            return 0;
        }

        // TODO: Update the selected pets in the pets database table with the given ContentValues
        SQLiteDatabase database = mDbHelper.getWritableDatabase();


        int updateRow = database.update(BetEntry.TABLE_NAME,values,selection,selectionArgs);
        // TODO: Return the number of rows that were affected
        if (updateRow != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updateRow;
    }
}