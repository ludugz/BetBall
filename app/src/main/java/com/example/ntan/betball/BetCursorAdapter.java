package com.example.ntan.betball;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.ntan.betball.data.BetContract.BetEntry;

import com.example.ntan.betball.data.BetContract;

/**
 * Created by NTan on 8/16/2017.
 */

public class BetCursorAdapter extends CursorAdapter {

    public BetCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // TODO: Fill out this method and return the list item view (instead of null)
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // TODO: Fill out this method
        TextView textViewteamName = (TextView) view.findViewById(R.id.team_name);
        TextView textViewteamMatch = (TextView) view.findViewById(R.id.team_match);
        TextView textViewteamWin = (TextView) view.findViewById(R.id.team_win);
        TextView textViewteamDraw = (TextView) view.findViewById(R.id.team_draw);
        TextView textViewteamLose = (TextView) view.findViewById(R.id.team_lose);
        TextView textViewteamPoint = (TextView) view.findViewById(R.id.team_point);


        String cursorStringteamName = cursor.getString(cursor.getColumnIndex(BetEntry.COLUMN_TEAM_NAME));
        String cursorStringteamMatch = cursor.getString(cursor.getColumnIndex(BetEntry.COLUMN_TEAM_MATCH));
        String cursorStringteamWin = cursor.getString(cursor.getColumnIndex(BetEntry.COLUMN_TEAM_WIN));
        String cursorStringteamDraw = cursor.getString(cursor.getColumnIndex(BetEntry.COLUMN_TEAM_DRAW));
        String cursorStringteamLose = cursor.getString(cursor.getColumnIndex(BetEntry.COLUMN_TEAM_LOSE));
        String cursorStringteamPoint = cursor.getString(cursor.getColumnIndex(BetEntry.COLUMN_TEAM_POINT));


        textViewteamName.setText(cursorStringteamName);
        textViewteamMatch.setText(cursorStringteamMatch);
        textViewteamWin.setText(cursorStringteamWin);
        textViewteamDraw.setText(cursorStringteamDraw);
        textViewteamLose.setText(cursorStringteamLose);
        textViewteamPoint.setText(cursorStringteamPoint);


    }
}