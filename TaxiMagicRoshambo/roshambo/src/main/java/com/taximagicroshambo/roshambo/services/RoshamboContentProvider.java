package com.taximagicroshambo.roshambo.services;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

public class RoshamboContentProvider extends ContentProvider {

    private RoshamboDatabaseHelper mOpenHelper;

    protected static final String AUTHORITY = "com.taximagicroshambo";

    private static final int RESULTS = 1;
    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, "results", RESULTS);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        switch (URI_MATCHER.match(uri)) {
            case RESULTS:
                long id = db.insert(RoshamboDatabaseHelper.RESULTS_TABLE,null,values);
                Uri itemUri = ContentUris.withAppendedId(uri, id);
                getContext().getContentResolver().notifyChange(itemUri, null);
                return itemUri;

            default:
                throw new IllegalArgumentException("Unsupported URI");
        }
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = RoshamboDatabaseHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch(URI_MATCHER.match(uri)){
            case RESULTS:
                qb.setTables(RoshamboDatabaseHelper.RESULTS_TABLE);

                Cursor cursor = qb.query(
                        db,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        Columns.DEFAULT_SORT);

                cursor.setNotificationUri(getContext().getContentResolver(),Columns.CONTENT_URI);

                return cursor;
            default:
                throw new IllegalArgumentException("Unsupported URI");
        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    protected static final class RoshamboDatabaseHelper extends SQLiteOpenHelper {
        private static RoshamboDatabaseHelper mInstance = null;

        private static final String DB_NAME = "roshambo";
        private static final int DB_VERSION = 1;

        public static final String RESULTS_TABLE = "history_table";
        static final String CREATE_RESULTS_TABLE =
                "CREATE TABLE " + RESULTS_TABLE + " " +
                        "( " +
                        Columns._ID + " INTEGER PRIMARY KEY, " +
                        Columns.DATE_COLUMN + "  DEFAULT CURRENT_TIMESTAMP, " +
                        Columns.RESULTS_COLUMN + "  TEXT " +
                        "); ";

        private RoshamboDatabaseHelper(Context context){
            super(context, DB_NAME,null, DB_VERSION);
        }

        public static synchronized RoshamboDatabaseHelper getInstance(Context context){
            if(mInstance == null){
                mInstance = new RoshamboDatabaseHelper(context);
            }

            return mInstance;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_RESULTS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // no upgrading in this assignment
        }
    }

    public static final class Columns implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(Uri.parse("content://" + AUTHORITY), "results");

        public static final String RESULTS_COLUMN = "results";

        public static final String DATE_COLUMN = "created_at";

        public static final String DEFAULT_SORT = DATE_COLUMN + " DESC ";

    }
}
