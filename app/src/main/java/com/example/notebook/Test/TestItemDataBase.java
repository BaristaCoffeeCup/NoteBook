package com.example.notebook.Test;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {TestItem.class},version = 1,exportSchema = false)
public abstract class TestItemDataBase extends RoomDatabase {

    private static TestItemDataBase INSTANCE;
    static synchronized TestItemDataBase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),TestItemDataBase.class,"test_item_database")
                    //.fallbackToDestructiveMigration()
                    //.addMigrations(MIGRATION_4_5)
                    .build();
        }
        return INSTANCE;
    }

    public abstract TestDao getTestItemDao();


}
