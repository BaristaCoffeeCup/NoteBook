package com.example.notebook;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {item.class},version = 1,exportSchema = false)
public abstract class ItemDataBase extends RoomDatabase {

    private static ItemDataBase INSTANCE;
    static synchronized ItemDataBase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),ItemDataBase.class,"item_database")
                    //.fallbackToDestructiveMigration()
                    //.addMigrations(MIGRATION_4_5)
                    .build();
        }
        return INSTANCE;
    }
    public abstract itemDao getItemDao();
}
