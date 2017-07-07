package com.example.shashikant.penorbit.data;

import android.net.Uri;
import android.provider.BaseColumns;

import static android.R.attr.y;

/**
 * Created by Shashikant on 7/5/2017, this class will have constants for database.
 */

public final class MedicineContract {
    // private constructor so it can not be instantiated
    private MedicineContract(){}

    public static final String CONTENT_AUTHORITY = "com.example.shashikant.penorbit";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MEDICINE = "medicine";
    public static abstract class MedicineEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEDICINE);
        public static final String TABLE_NAME = "medicine";
        public static final String _ID = BaseColumns._ID;
        public static final String MEDICINE_NAME = "name";
        public static final String MEDICINE_FREQUENCY_TYPE = "frequency_type";
        public static final String MEDICINE_QUANTITY_AT_A_TIME = "quantity_at_a_time";
        public static final String MEDICINE_DOSE_PER_DAY = "dose_per_day";
        public static final String MEDICINE_REMINDERS = "reminders";
        public static final String MEDICINE_NO_OF_PURCHASED = "medicine_purchased";

        /**
         * Possible values for frequency type
         */
        public static final int FREQUENCY_DAILY  = 0;
        public static final int FREQUENCY_WEEKLY = 1;
    }
}
