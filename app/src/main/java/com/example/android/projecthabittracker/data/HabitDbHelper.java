package com.example.android.projecthabittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.projecthabittracker.data.HabitContract.HabitEntry;
/**
 * Created by arturoahernandez on 3/6/18.
 */

public class HabitDbHelper extends SQLiteOpenHelper {

    //Name of the database on file
    private static final String DATABASE_NAME = "habit.db";

    //Database version.  If you update the database schema, increment the database version
    private static final int DATABASE_VERSION = 1;


    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //CREATE TABLE habits
        //Create a string statement that has the info to make SQL
        String SQL_CREATE_HABITS_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + "("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_GAMES + " INTEGER NOT NULL DEFAULT 0, "
                + HabitEntry.COLUMN_RUNNING + " INTEGER NOT NULL DEFAULT 0, "
                + HabitEntry.COLUMN_MEALS_COOKED + " INTEGER NOT NULL DEFAULT 0, "
                + HabitEntry.COLUMN_DESCRIBE_WEEK + " TEXT, "
                + HabitEntry.COLUMN_CHEAT_DAY + " INTEGER NOT NULL);";

        db.execSQL(SQL_CREATE_HABITS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
