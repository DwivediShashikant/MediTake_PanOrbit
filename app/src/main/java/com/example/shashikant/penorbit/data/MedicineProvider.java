package com.example.shashikant.penorbit.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.example.shashikant.penorbit.data.MedicineContract.MedicineEntry;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Shashikant on 7/5/2017.
 */

public class MedicineProvider extends ContentProvider {

    private final String LOG_TAG = MedicineProvider.class.getSimpleName();
    public MedicineDbHelper mDbHelper;
    public static final int MEDICINES = 100;
    public static final int MEDICINE_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI( MedicineContract.CONTENT_AUTHORITY, MedicineContract.PATH_MEDICINE, MEDICINES);
        sUriMatcher.addURI( MedicineContract.CONTENT_AUTHORITY, MedicineContract.PATH_MEDICINE+"/#",MEDICINE_ID);
    }
    @Override
    public boolean onCreate() {
        mDbHelper = new MedicineDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor cursor = null;
        switch (match){
            case MEDICINES:
                cursor = db.query(MedicineEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case MEDICINE_ID:
                Log.v(LOG_TAG,"Uri is of specifice type++++++++++++++++++++++++++++++");
                selection =  MedicineEntry._ID+"=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))} ;
                cursor = db.query(MedicineEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                cursor.moveToNext();
                Log.v(LOG_TAG,cursor.getString(cursor.getColumnIndex(MedicineEntry.MEDICINE_NAME))+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                break;
            default:
                Log.v("","error executing ");
        }
        // set notification URI on cursor
        // so that we know if data at this particular URI changes
        // we need to update the cursor
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = sUriMatcher.match(uri);
        switch(match){
            case MEDICINES:
                return inserMedicine(uri, values);
            default:
                try {
                    throw new IllegalAccessException(" insertion is not supported for this " +uri);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }
    public Uri inserMedicine( Uri uri, ContentValues values){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long medId = db.insert( MedicineEntry.TABLE_NAME, null, values);

        // notify all listners that the data has changed for this uri
        getContext().getContentResolver().notifyChange(uri,null);

        return ContentUris.withAppendedId(uri,medId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowDeleted= db.delete(MedicineEntry.TABLE_NAME,selection,selectionArgs);
        if(rowDeleted!=0)
        getContext().getContentResolver().notifyChange(uri,null);
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match =  sUriMatcher.match(uri);
        switch(match){
            case MEDICINES:
                updateMedcine(uri,values,selection,selectionArgs);
                break;
            case MEDICINE_ID:
                selection = MedicineEntry._ID + "=?";
                selectionArgs = new String[]{ String.valueOf( ContentUris.parseId(uri))};
                return updateMedcine( uri, values, selection, selectionArgs);
            default:
                try {
                    throw new IllegalAccessException("update is not supported for the"+uri);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        }
        return 0;
    }

    public int updateMedcine(Uri uri, ContentValues values, String selection, String[]selectionArgs){
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Returns the number of database rows affected by the update statement
        int rowUpdated = database.update(MedicineEntry.TABLE_NAME, values, selection, selectionArgs);
        if(rowUpdated!=0){
            // notify all listners if data has changed
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowUpdated;
    }
}
