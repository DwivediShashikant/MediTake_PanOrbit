package com.example.shashikant.penorbit;

import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.CalendarContract;
import android.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shashikant.penorbit.data.MedicineContract;
import com.example.shashikant.penorbit.data.MedicineContract.MedicineEntry;
import com.example.shashikant.penorbit.data.MedicineDbHelper;

import static android.provider.CalendarContract.CalendarCache.URI;
import static android.text.style.TtsSpan.GENDER_FEMALE;

public class MedicineEditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String LOG_TAG = MedicineEditorActivity.class.getSimpleName();
    Uri currentMedicineUri = null;
    private EditText mMedicineName;
    private EditText mQuantity;
    private EditText mDosePerDay;
    private EditText mReminders;
    private EditText mPurchased;
    private Spinner mFrequnecyTypeSpinner;
    public MedicineDbHelper mDbHelper;

    private int mfrequnecyType;

    String []projection = {
            MedicineEntry._ID,
            MedicineEntry.MEDICINE_NAME,
            MedicineEntry.MEDICINE_FREQUENCY_TYPE,
            MedicineEntry.MEDICINE_QUANTITY_AT_A_TIME,
            MedicineEntry.MEDICINE_DOSE_PER_DAY,
            MedicineEntry.MEDICINE_REMINDERS,
            MedicineEntry.MEDICINE_NO_OF_PURCHASED
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_editor);

        Intent intent = getIntent();
        currentMedicineUri = intent.getData();
        mMedicineName = (EditText)findViewById(R.id.edit_med_name);
        mQuantity = (EditText)findViewById(R.id.edit_quantity);
        mDosePerDay = (EditText)findViewById(R.id.edit_dose_day);
        mReminders = (EditText)findViewById(R.id.edit_reminders);
        mPurchased = (EditText)findViewById(R.id.edit_purchased);
        mFrequnecyTypeSpinner = (Spinner)findViewById(R.id.spinner_dose_type);


        setUpSpinner();

        if(currentMedicineUri!=null){
            setTitle("Edit Medicine");
            Log.v(LOG_TAG,"The Uri for the current medicine is: "+currentMedicineUri);
            //kicking off loader
            getLoaderManager().initLoader(0,null,MedicineEditorActivity.this);
        }

    }
    public void setUpSpinner(){
        ArrayAdapter frequencySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_frequnecy_type_option, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        frequencySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mFrequnecyTypeSpinner.setAdapter(frequencySpinnerAdapter);

        mFrequnecyTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.frequnecy_daily))) {
                        mfrequnecyType = MedicineContract.MedicineEntry.FREQUENCY_DAILY; // Daily
                    } else if (selection.equals(getString(R.string.frequnecy_weekly))) {
                        mfrequnecyType = MedicineContract.MedicineEntry.FREQUENCY_WEEKLY; // Weekly

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }
    public void insertMedicine(){
        ContentValues values = new ContentValues();
        values.put(MedicineEntry.MEDICINE_NAME,mMedicineName.getText().toString().trim());
        values.put(MedicineEntry.MEDICINE_FREQUENCY_TYPE,mfrequnecyType);
        values.put(MedicineEntry.MEDICINE_QUANTITY_AT_A_TIME,mQuantity.getText().toString().trim());
        values.put(MedicineEntry.MEDICINE_DOSE_PER_DAY,mDosePerDay.getText().toString().trim());
        values.put(MedicineEntry.MEDICINE_REMINDERS,mReminders.getText().toString().trim());
        values.put(MedicineEntry.MEDICINE_NO_OF_PURCHASED,mPurchased.getText().toString().trim());

        Uri uri = getContentResolver().insert(MedicineEntry.CONTENT_URI,values);
        if(uri!=null){
            Toast.makeText(this,"Medicine Inserted",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"Error with saving medicine",Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_save:
                insertMedicine();
                finish();
                return true;
            case R.id.action_delete:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
        }
        return true;
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG,"the id for this medicine is: "+id);
        //Uri currentMedicineUri = Uri.parse(MedicineEntry.CONTENT_URI+"/"+id);
        return new CursorLoader(getBaseContext(),currentMedicineUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
        if(data == null){
            Log.v(LOG_TAG,"returned cursor is empty");
        }
        mMedicineName.setText(data.getString(data.getColumnIndex(MedicineEntry.MEDICINE_NAME)));
        mQuantity.setText(data.getString(data.getColumnIndex(MedicineEntry.MEDICINE_QUANTITY_AT_A_TIME)));
        mDosePerDay.setText(data.getString(data.getColumnIndex(MedicineEntry.MEDICINE_DOSE_PER_DAY)));
        mReminders.setText(data.getString(data.getColumnIndex(MedicineEntry.MEDICINE_REMINDERS)));
        mPurchased.setText(data.getString(data.getColumnIndex(MedicineEntry.MEDICINE_NO_OF_PURCHASED)));

        int frequencyType = data.getInt(data.getColumnIndex(MedicineEntry.MEDICINE_FREQUENCY_TYPE));
        mFrequnecyTypeSpinner.setSelection(frequencyType);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
    }
}
