package com.example.shashikant.penorbit.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.shashikant.penorbit.data.MedicineContract.MedicineEntry;
/**
 * Created by Shashikant on 7/5/2017.
 */

public class MedicineDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = MedicineDbHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "medicines.db";
    public static final int DATABASE_VERSION = 1;

    //query for creating table in database
    public static final String SQL_CREATE_MEDICINE_TABLE = "CREATE TABLE "+ MedicineEntry.TABLE_NAME+"( "
            + MedicineEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MedicineEntry.MEDICINE_NAME + " TEXT NOT NULL, "
            + MedicineEntry.MEDICINE_FREQUENCY_TYPE + " INTEGER, "
            + MedicineEntry.MEDICINE_QUANTITY_AT_A_TIME + " INTEGER NOT NULL, "
            + MedicineEntry.MEDICINE_DOSE_PER_DAY + " INTEGER NOT NULL, "
            + MedicineEntry.MEDICINE_REMINDERS + " TEXT NOT NULL, "
            + MedicineEntry.MEDICINE_NO_OF_PURCHASED + " INTEGER NOT NULL" +");";
    public MedicineDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(LOG_TAG,SQL_CREATE_MEDICINE_TABLE);
        db.execSQL(SQL_CREATE_MEDICINE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
