package com.example.notebook.Test;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Record.class},version = 1,exportSchema = false)
public abstract  class RecordDatabase extends RoomDatabase {

    private static RecordDatabase INSTANCE;
    static synchronized RecordDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),RecordDatabase.class,"record_item_database")
                    //.fallbackToDestructiveMigration()
                    //.addMigrations(MIGRATION_4_5)
                    .build();
        }
        return INSTANCE;
    }

    public abstract RecordDao getRecordDao();

}
