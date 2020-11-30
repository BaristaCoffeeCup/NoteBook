package com.example.notebook.Test;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TestDao {

    @Insert
    void InsertTestItem(TestItem... testItems);

    @Delete
    void DeleteTestItem(TestItem... testItems);

    @Query("DELETE FROM TESTITEM")
    void ClearTestItem();

    @Update
    void UpdateTestItem(TestItem... testItems);

    @Query("SELECT * FROM testitem")
    LiveData<List<TestItem>> getAllTestItems();

    @Query("SELECT * FROM testitem WHERE testTitle = :TestTitle")
    TestItem getTestItemsWithTitle(String TestTitle);
}
