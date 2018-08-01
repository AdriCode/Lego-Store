package com.example.android.legostore.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.example.android.legostore.data.LegoContract.LegoEntry;
import android.content.UriMatcher;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class LegoProvider extends ContentProvider {

    private Context mContext = getContext();
    public LegoDBHelper mDbHelper;
    private SQLiteDatabase db;

    // URI matcher code for the content URI for the Lego table
    private static final int LEGO = 100;

    // URI matcher code for the content URI for a single item in the lego table
    private static final int LEGO_ID = 101;

    //UriMatcher object to match a content URI to a corresponding code.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /** Tag for the log messages */
    public static final String LOG_TAG = LegoProvider.class.getSimpleName();

    static {
        // Added 2 content URIs to URI matcher
        sUriMatcher.addURI(LegoEntry.CONTENT_AUTHORITY, LegoEntry.PATH, LEGO);
        sUriMatcher.addURI(LegoEntry.CONTENT_AUTHORITY, LegoEntry.PATH + "/#", LEGO_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new LegoDBHelper(mContext);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
       db = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor = null;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        
        switch (match) {
            case LEGO:
                cursor = db.query(LegoEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case LEGO_ID:
                selection = LegoEntry.COLUMN_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = db.query(LegoEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case LEGO:
                return insertLego(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a product into the database with the given content values. Return the new content URI
     */
    private Uri insertLego(Uri uri, ContentValues values) {
        // Get writeable database
        db = mDbHelper.getWritableDatabase();

        Long id = db.insert(LegoEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (id == -1) {
            Toast.makeText(mContext, "Error with saving lego", Toast.LENGTH_SHORT).show();
        }

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        // Delete a single row given by the ID in the URI
        int dataDeleted = db.delete(LegoEntry.TABLE_NAME, selection, selectionArgs);

        // If 1 or more rows were deleted, then notify all listeners the data at the URI
        if (dataDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return dataDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Get writeable database
        db = mDbHelper.getWritableDatabase();

        // Update the database and get the number of rows affected
        int dataChanged = db.update(LegoEntry.TABLE_NAME, values, selection, selectionArgs);

        // If data changed, then notify all listeners the data at the URI
        if (dataChanged != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return dataChanged;
    }
}
