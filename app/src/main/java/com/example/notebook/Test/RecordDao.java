package com.example.notebook.Test;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecordDao {

    @Insert
    void InsertRecord(Record... records);

    @Delete
    void DeleteRecord(Record... records);

    @Query("DELETE FROM RECORD")
    void ClearRecord();

    @Query("SELECT * FROM record")
    LiveData<List<Record>> getAllRecord();

    @Update
    void UpdateRecord(Record... records);
}
