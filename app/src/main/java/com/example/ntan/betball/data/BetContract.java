package com.example.ntan.betball.data;

import android.net.Uri;
import android.content.ContentResolver;
import android.provider.BaseColumns;

import static android.text.style.TtsSpan.GENDER_FEMALE;
import static android.text.style.TtsSpan.GENDER_MALE;


public final class BetContract {

    private BetContract() {}

    public static final String CONTENT_AUTHORITY = "com.example.ntan.betball";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_BET = "Bet";

    public static final class BetEntry implements BaseColumns {

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BET;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BET;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BET);

        public final static String TABLE_NAME = "Bet";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_TEAM_NAME ="name";

        public final static String COLUMN_TEAM_MATCH = "match";

        public final static String COLUMN_TEAM_WIN = "win";

        public final static String COLUMN_TEAM_DRAW = "draw";

        public final static String COLUMN_TEAM_LOSE = "lose";

        public final static String COLUMN_TEAM_GOALWIN = "goalwin";

        public final static String COLUMN_TEAM_GOALLOSE = "goallose";

        public final static String COLUMN_TEAM_POINT = "point";

        public final static String COLUMN_TEAM_FORM = "form";


    }

}

