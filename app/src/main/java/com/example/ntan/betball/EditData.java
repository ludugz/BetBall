package com.example.ntan.betball;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ntan.betball.data.BetContract;
import com.example.ntan.betball.data.BetContract.BetEntry;


/**
 * Created by NTan on 10/3/2017.
 */

public class EditData extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private AutoCompleteTextView mTeamNameACTX;
    private EditText mTeamWinEditText;
    private EditText mTeamDrawEditText;
    private EditText mTeamLoseEditText;
    private EditText mTeamGoalWinEditText;
    private EditText mTeamGoalLoseEditText;
    private TextView mTeamPointTextView;
    private Uri currentBetUri;
    private boolean petSavedInsert;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data);
        View editDataActivity = findViewById(R.id.edit_data_activity);
        Drawable backGroundDrawable = getResources().getDrawable(R.drawable.football);
        editDataActivity.setBackground(backGroundDrawable);

        Intent intent = getIntent();
        currentBetUri = intent.getData();
        if (currentBetUri == null) {
            setTitle("team info");
            invalidateOptionsMenu();
        } else {
            setTitle("team update");
            getLoaderManager().initLoader(0, null, this);
        }

        // Set Auto complete textview for teamname
        mTeamNameACTX = (AutoCompleteTextView) findViewById(R.id.autocomplete_teamname);
        String[] teamNameString = getResources().getStringArray(R.array.team_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teamNameString);
        mTeamNameACTX.setAdapter(adapter);

        mTeamWinEditText = (EditText) findViewById(R.id.ed_team_win);
        mTeamDrawEditText = (EditText) findViewById(R.id.ed_team_draw);
        mTeamLoseEditText = (EditText) findViewById(R.id.ed_team_lose);
        mTeamGoalWinEditText = (EditText) findViewById(R.id.ed_team_goalwin);
        mTeamGoalLoseEditText = (EditText) findViewById(R.id.ed_team_goallose);
        mTeamPointTextView = (TextView) findViewById(R.id.team_point);
    }

    // Prepare and create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editdata, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (currentBetUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (currentBetUri==null){
            switch (item.getItemId()) {
                // Respond to a click on the "Save" menu option
                case R.id.action_save:
                    insertData();
                    return true;
                case R.id.action_detail:
                    insertDetailedData();
            }
        }
        else{
            switch (item.getItemId()) {
                // Respond to a click on the "Save" menu option
                case R.id.action_save:
                    updateData();
                    finish();
                    return true;
                    // Respond to a click on the "Delete" menu option
                case R.id.action_delete:
                    deleteData();
                    finish();
                    return true;
                case R.id.action_detail:
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                BetEntry._ID,
                BetEntry.COLUMN_TEAM_NAME,
                BetEntry.COLUMN_TEAM_MATCH,
                BetEntry.COLUMN_TEAM_WIN,
                BetEntry.COLUMN_TEAM_DRAW,
                BetEntry.COLUMN_TEAM_LOSE,
                BetEntry.COLUMN_TEAM_GOALWIN,
                BetEntry.COLUMN_TEAM_GOALLOSE,
                BetEntry.COLUMN_TEAM_POINT};
        return new CursorLoader(this,currentBetUri,projection,null,null,null);
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            if (cursor.moveToFirst()){

                mTeamNameACTX.setText(cursor.getString(cursor.getColumnIndex(BetEntry.COLUMN_TEAM_NAME)));
                mTeamWinEditText.setText(cursor.getString(cursor.getColumnIndex(BetEntry.COLUMN_TEAM_WIN)));
                mTeamDrawEditText.setText(cursor.getString(cursor.getColumnIndex(BetEntry.COLUMN_TEAM_DRAW)));
                mTeamLoseEditText.setText(cursor.getString(cursor.getColumnIndex(BetEntry.COLUMN_TEAM_LOSE)));
                mTeamGoalWinEditText.setText(cursor.getString(cursor.getColumnIndex(BetEntry.COLUMN_TEAM_GOALWIN)));
                mTeamGoalLoseEditText.setText(cursor.getString(cursor.getColumnIndex(BetEntry.COLUMN_TEAM_GOALLOSE)));
            }
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    private void insertData() {
        String teamNameString = mTeamNameACTX.getText().toString().trim();
        String teamWinString = mTeamWinEditText.getText().toString().trim();
        String teamDrawString = mTeamDrawEditText.getText().toString().trim();
        String teamLoseString = mTeamLoseEditText.getText().toString().trim();
        String teamGoalWinString = mTeamGoalWinEditText.getText().toString().trim();
        String teamGoalLoseString = mTeamGoalLoseEditText.getText().toString().trim();

        if ( teamNameString.equals("") ||  teamWinString.equals("") ||
                teamDrawString.equals("") || teamLoseString.equals("") ||
                teamGoalWinString.equals("") ||teamGoalLoseString.equals("")
                ){
            Toast.makeText(EditData.this,"Please enter a correct data",Toast.LENGTH_LONG).show();
        } else {
            int match;
            int win = Integer.parseInt(teamWinString);
            int draw = Integer.parseInt(teamDrawString);
            int lose = Integer.parseInt(teamLoseString);
            int goalWin = Integer.parseInt(teamGoalWinString);
            int goalLose = Integer.parseInt(teamGoalLoseString);
            int winPoint = 3*win;
            int drawPoint = draw;
            int teamPoint = winPoint + drawPoint;
            match = win + draw+ lose;
            // Create a ContentValues object where column names are the keys,
            // and pet attributes from the editor are the values.
            ContentValues values = new ContentValues();
            values.put(BetEntry.COLUMN_TEAM_NAME, teamNameString);
            values.put(BetEntry.COLUMN_TEAM_MATCH,match);
            values.put(BetEntry.COLUMN_TEAM_WIN,win);
            values.put(BetEntry.COLUMN_TEAM_DRAW,draw);
            values.put(BetEntry.COLUMN_TEAM_LOSE,lose);
            values.put(BetEntry.COLUMN_TEAM_GOALWIN,goalWin);
            values.put(BetEntry.COLUMN_TEAM_GOALLOSE,goalLose);
            values.put(BetEntry.COLUMN_TEAM_POINT, teamPoint);

            // Insert a new pet into the provider, returning the content URI for the new pet.
            Uri newUri = getContentResolver().insert(BetEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_pet_successful),
                        Toast.LENGTH_SHORT).show();
                petSavedInsert = true;
            }
            finish();
        }
    }
    private void deleteData() {
        int rowDeleted =0;
        rowDeleted = getContentResolver().delete(currentBetUri,null,null);

    }
    private void updateData() {
        int rowUpdate = 0;

        String teamNameString = mTeamNameACTX.getText().toString().trim();
        String teamWinString = mTeamWinEditText.getText().toString().trim();
        String teamDrawString = mTeamDrawEditText.getText().toString().trim();
        String teamLoseString = mTeamLoseEditText.getText().toString().trim();
        String teamGoalWinString = mTeamGoalWinEditText.getText().toString().trim();
        String teamGoalLoseString = mTeamGoalLoseEditText.getText().toString().trim();

        if (teamNameString.equals("") || teamWinString.equals("")) {
            Toast.makeText(EditData.this, "Please enter a correct data", Toast.LENGTH_LONG).show();
        } else {

            int match;
            int win = Integer.parseInt(teamWinString);
            int draw = Integer.parseInt(teamDrawString);
            int lose = Integer.parseInt(teamLoseString);
            int goalWin = Integer.parseInt(teamGoalWinString);
            int goalLose = Integer.parseInt(teamGoalLoseString);
            int winPoint = 3*win;
            int drawPoint = draw;
            int teamPoint = winPoint + drawPoint;
            match = win + draw + lose;

            // Create a ContentValues object where column names are the keys,
            // and pet attributes from the editor are the values.
            ContentValues values = new ContentValues();
            values.put(BetEntry.COLUMN_TEAM_NAME, teamNameString);
            values.put(BetEntry.COLUMN_TEAM_MATCH, match);
            values.put(BetEntry.COLUMN_TEAM_WIN, win);
            values.put(BetEntry.COLUMN_TEAM_DRAW, draw);
            values.put(BetEntry.COLUMN_TEAM_LOSE, lose);
            values.put(BetEntry.COLUMN_TEAM_GOALWIN, goalWin);
            values.put(BetEntry.COLUMN_TEAM_GOALLOSE, goalLose);
            values.put(BetEntry.COLUMN_TEAM_POINT, teamPoint);
            rowUpdate = getContentResolver().update(currentBetUri, values, null, null);
        }
    }
    private void insertDetailedData() {
        Intent intent = new Intent(EditData.this, EditDetailedData.class);
        startActivity(intent);
    }
}
