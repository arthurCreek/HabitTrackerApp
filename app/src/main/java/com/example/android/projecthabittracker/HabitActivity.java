package com.example.android.projecthabittracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.android.projecthabittracker.data.HabitContract.HabitEntry;
import com.example.android.projecthabittracker.data.HabitDbHelper;

public class HabitActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new HabitDbHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {HabitEntry._ID,
                HabitEntry.COLUMN_GAMES,
                HabitEntry.COLUMN_RUNNING,
                HabitEntry.COLUMN_MEALS_COOKED,
                HabitEntry.COLUMN_DESCRIBE_WEEK,
                HabitEntry.COLUMN_CHEAT_DAY};

        Cursor cursor = db.query(HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);



        TextView displayView = (TextView) findViewById(R.id.text_view_pet);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The habit table contains " + cursor.getCount() + " habit rows.\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_GAMES + " - " +
                    HabitEntry.COLUMN_RUNNING + " - " +
                    HabitEntry.COLUMN_MEALS_COOKED + " - " +
                    HabitEntry.COLUMN_DESCRIBE_WEEK + " - " +
                    HabitEntry.COLUMN_CHEAT_DAY + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int gamesColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_GAMES);
            int runningColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_RUNNING);
            int mealsColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_MEALS_COOKED);
            int describeWeekColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DESCRIBE_WEEK);
            int cheatDayColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_CHEAT_DAY);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                int currentGames = cursor.getInt(gamesColumnIndex);
                int currentRunning = cursor.getInt(runningColumnIndex);
                int currentMeals = cursor.getInt(mealsColumnIndex);
                String currentDescribeWeek = cursor.getString(describeWeekColumnIndex);
                int currentCheatDay = cursor.getInt(cheatDayColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentGames + " - " +
                        currentRunning + " - " +
                        currentMeals + " - " +
                        currentDescribeWeek + " - " +
                        currentCheatDay));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    private void insertPet() {
        // TODO: Insert a single pet into the database
        SQLiteDatabase dbFake = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_GAMES, 3);
        values.put(HabitEntry.COLUMN_RUNNING, 10);
        values.put(HabitEntry.COLUMN_MEALS_COOKED, 12);
        values.put(HabitEntry.COLUMN_DESCRIBE_WEEK, "Great");
        values.put(HabitEntry.COLUMN_CHEAT_DAY, HabitEntry.CHEAT_DAY_SATURDAY);

        long newRowId = dbFake.insert(HabitEntry.TABLE_NAME, null, values);

        Log.v("HabitActivity", "New row ID " + newRowId);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_habit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
