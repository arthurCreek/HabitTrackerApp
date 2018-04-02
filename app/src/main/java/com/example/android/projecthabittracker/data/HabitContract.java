package com.example.android.projecthabittracker.data;

import android.provider.BaseColumns;

/**
 * Created by arturoahernandez on 3/6/18.
 */

public final class HabitContract {

    public HabitContract() {
    }

    public static final class HabitEntry implements BaseColumns{

        //Table name
        public static final String TABLE_NAME = "habits";

        //Columns including the _id
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_GAMES = "game";
        public static final String COLUMN_RUNNING = "running";
        public static final String COLUMN_MEALS_COOKED = "meals";
        public static final String COLUMN_DESCRIBE_WEEK = "week";
        public static final String COLUMN_CHEAT_DAY = "cheat";

        //Possible gender constants
        public static final int CHEAT_DAY_NONE = 0;
        public static final int CHEAT_DAY_MONDAY= 1;
        public static final int CHEAT_DAY_TUESDAY = 2;
        public static final int CHEAT_DAY_WEDNESDAY = 3;
        public static final int CHEAT_DAY_THURSDAY= 4;
        public static final int CHEAT_DAY_FRIDAY = 5;
        public static final int CHEAT_DAY_SATURDAY = 6;
        public static final int CHEAT_DAY_SUNDAY= 7;
    }
}
