package com.example.ntan.betball;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Matrix;
import com.example.ntan.betball.data.BetContract.BetEntry;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private boolean petSavedInsert;
    private static final String TAG = "MainActivity";
    BetCursorAdapter todoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View mainActivity = findViewById(R.id.main_activiy);
        Drawable backGroundDrawable = getResources().getDrawable(R.drawable.football);
        mainActivity.setBackground(backGroundDrawable);
        todoAdapter = new BetCursorAdapter(this, null);
        getLoaderManager().initLoader(0,null,this);

        // Setup FAB to open MainActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Da vao OnClickButton");
                Intent intent = new Intent(MainActivity.this, EditData.class);
                startActivity(intent);
            }
        });
    }
    // Set up the LoaderCursor
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
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
        return new CursorLoader(this,BetEntry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        todoAdapter.swapCursor(cursor);
        Log.d(TAG, "Da vao onLoadFinished");
        ListView lvItems = (ListView) findViewById(R.id.list_view);
        View emptyView = findViewById(R.id.empty_view);
        View tieuDe =  findViewById(R.id.tieude);
        View BXH =  findViewById(R.id.BXH);

        //Set onItemClickListener on item
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditData.class);
                Uri currentBetUri = ContentUris.withAppendedId(BetEntry.CONTENT_URI,id);
                intent.setData(currentBetUri);
                startActivity(intent);
            }
        });
        if (cursor.getCount()==0){
            tieuDe.setVisibility(View.GONE);
            BXH.setVisibility(View.GONE);
        }
        else {
            tieuDe.setVisibility(View.VISIBLE);
            BXH.setVisibility(View.VISIBLE);
        }
        lvItems.setEmptyView(emptyView);
        lvItems.setAdapter(todoAdapter);
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        todoAdapter.swapCursor(null);
    }
    private void insertBet() {
        String nameString = "chelsea";
        String matchString = "3";
        String winString = "3";
        String drawString = "2";
        String loseString = "1";
        String goalString = "1";
        String pointString = "1";
        ContentValues values = new ContentValues();
        values.put(BetEntry.COLUMN_TEAM_NAME, nameString);
        values.put(BetEntry.COLUMN_TEAM_MATCH, matchString);
        values.put(BetEntry.COLUMN_TEAM_WIN, winString);
        values.put(BetEntry.COLUMN_TEAM_DRAW, drawString);
        values.put(BetEntry.COLUMN_TEAM_LOSE, loseString);
        values.put(BetEntry.COLUMN_TEAM_GOALWIN, goalString);
        values.put(BetEntry.COLUMN_TEAM_GOALLOSE, goalString);
        values.put(BetEntry.COLUMN_TEAM_POINT, pointString);

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
    }
    private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    private Drawable resizeDrawable(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 1920, 1080, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }
}
