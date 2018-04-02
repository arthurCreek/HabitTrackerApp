package com.example.android.projecthabittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
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

import com.example.android.projecthabittracker.data.HabitContract.HabitEntry;
import com.example.android.projecthabittracker.data.HabitDbHelper;

/**
 * Created by arturoahernandez on 3/6/18.
 */

public class EditorActivity extends AppCompatActivity {
    /** EditText field to enter hours played*/
    private EditText mGameEditText;

    /** EditText field to enter miles ran */
    private EditText mRunningEditText;

    /** EditText field to enter the meals cooked */
    private EditText mMealsEditText;

    /** EditText field to enter week description*/
    private EditText mDescribeWeek;

    /** EditText field to enter the cheat day */
    private Spinner mCheatSpinner;

    /**
     * Cheat day. The possible values are:
     * 0 for None, 1 for Monday, 2 for Tuesday, 3 for Wednesday, 4 for Thursday, 5 for Friday
     * 6 for Saturday, 7 for Sunday
     */
    private int mCheatDay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mGameEditText = (EditText) findViewById(R.id.edit_game_hours);
        mRunningEditText = (EditText) findViewById(R.id.edit_miles_ran);
        mMealsEditText = (EditText) findViewById(R.id.edit_meals_cooked);
        mDescribeWeek = (EditText) findViewById(R.id.edit_describe_week);
        mCheatSpinner = (Spinner) findViewById(R.id.spinner_cheat_day);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the cheat day.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_cheat_day_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mCheatSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mCheatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.cheat_monday))) {
                        mCheatDay = HabitEntry.CHEAT_DAY_MONDAY; // Monday
                    } else if (selection.equals(getString(R.string.cheat_tuesday))) {
                        mCheatDay = HabitEntry.CHEAT_DAY_TUESDAY; // Tueday
                    }else if (selection.equals(getString(R.string.cheat_wednesday))) {
                        mCheatDay = HabitEntry.CHEAT_DAY_WEDNESDAY; //Wednesday
                    } else if (selection.equals(getString(R.string.cheat_thursday))) {
                        mCheatDay = HabitEntry.CHEAT_DAY_THURSDAY; //Thursday
                    } else if (selection.equals(getString(R.string.cheat_friday))) {
                        mCheatDay = HabitEntry.CHEAT_DAY_FRIDAY; //Friday
                    } else if (selection.equals(getString(R.string.cheat_saturday))) {
                        mCheatDay = HabitEntry.CHEAT_DAY_SATURDAY; //Saturday
                    } else if (selection.equals(getString(R.string.cheat_sunday))) {
                        mCheatDay = HabitEntry.CHEAT_DAY_SUNDAY; //Sunday
                    } else{
                        mCheatDay = HabitEntry.CHEAT_DAY_NONE;
                    } // Unknown
                }
            }
            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCheatDay = 0; // Unknown
            }
        });

    }

    //Get new habits info and save it into the database
    private void insertHabit(){

        int gamesInt;
        int runningInt;
        int mealsInt;

        String gamesString = mGameEditText.getText().toString().trim();
        if (gamesString.matches("")) {
            Toast.makeText(this, "You did not enter hours played", Toast.LENGTH_SHORT).show();
            return;
        } else {
            gamesInt =  Integer.parseInt(gamesString);
        }

        String runningString = mRunningEditText.getText().toString().trim();
        if (runningString.matches("")) {
            Toast.makeText(this, "You did not enter miles ran", Toast.LENGTH_SHORT).show();
            return;
        } else {
            runningInt =  Integer.parseInt(runningString);
        }

        String mealsString = mMealsEditText.getText().toString().trim();
        if (mealsString.matches("")) {
            Toast.makeText(this, "You did not enter meals cooked", Toast.LENGTH_SHORT).show();
            return;
        } else {
            mealsInt =  Integer.parseInt(mealsString);
        }

        String describeWeekString = mDescribeWeek.getText().toString().trim();

        String cheatDayInt = mCheatSpinner.getSelectedItem().toString();

        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        SQLiteDatabase dbFake = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_GAMES, gamesInt);
        values.put(HabitEntry.COLUMN_RUNNING, runningInt);
        values.put(HabitEntry.COLUMN_MEALS_COOKED, mealsInt);
        values.put(HabitEntry.COLUMN_DESCRIBE_WEEK, describeWeekString);
        values.put(HabitEntry.COLUMN_CHEAT_DAY, cheatDayInt);

        long newRowId = dbFake.insert(HabitEntry.TABLE_NAME, null, values);

        if (newRowId == -1){
            Toast.makeText(this, "Error making habit", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Habits saved with row ID: " + newRowId, Toast.LENGTH_SHORT).show();
        }

        Log.v("EditorActivity", "New row ID " + newRowId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to the database
                insertHabit();
                //Exit activity
                finish();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
