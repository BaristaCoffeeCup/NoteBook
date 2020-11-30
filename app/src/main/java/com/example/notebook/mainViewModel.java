package com.example.notebook;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notebook.Test.Record;
import com.example.notebook.Test.RecordRepository;
import com.example.notebook.Test.TestItem;
import com.example.notebook.Test.TestRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class mainViewModel extends AndroidViewModel {

    private itemRepository itemRepository;
    private TestRepository testRepository;
    private RecordRepository recordRepository;

    public mainViewModel(@NonNull Application application) {
        super(application);
        itemRepository = new itemRepository(application);
        testRepository = new TestRepository(application);
        recordRepository = new RecordRepository(application);
    }

    public LiveData<List<item>> getAllItemTypeLive(int Type) {
        return itemRepository.getallItemTypeLive(Type);
    }

    public LiveData<List<item>> getItemWithPattern(String Pattern,int Type){
        return itemRepository.getItemWithPattern(Pattern,Type);
    }

    public LiveData<List<item>> getReviewItem(int Month,int Day,int Month2,int Day2,int Month3, int Day3,int Month4,int Day4,int Month5,int Day5){
        return itemRepository.getReviewItem(Month, Day,Month2,Day2,Month3,Day3,Month4,Day4,Month5,Day5);
    }

    public void inserItems(item... items) {
        itemRepository.InsertItem(items);
    }
    public void updateItems(item... items) {
        itemRepository.UpdateItem(items);
    }
    public void deleteItems(item... items) {
        itemRepository.DeleteItem(items);
    }
    public void clearItems(){
        itemRepository.ClearItem();
    }

    //**************************************************************************************************************************//

    public LiveData<List<TestItem>> getAllTestItems(){
        return testRepository.getAllTestItems();
    }

    public void InsertTestItems(TestItem... testItems){
        testRepository.InsertTestItem(testItems);
    }
    public void DeleteTestItems(TestItem... testItems){
        testRepository.DeleteTestItem(testItems);
    }
    public void UpdateTestItems(TestItem... testItems){
        testRepository.UpdateTestItem(testItems);
    }
    public void ClearTestItems(){
        testRepository.ClearTestItem();
    }
    public TestItem getTestItemWithTitle(String TestTitle) throws ExecutionException, InterruptedException {
        return testRepository.getTestItemWithTitle(TestTitle);
    }

    //**************************************************************************************************************************//

    public LiveData<List<Record>> getAllRecord(){
        return recordRepository.getAllRecord();
    }
    public void InsertRecord(Record... records){
        recordRepository.InsertRecord(records);
    }
    public void DeleteRecord(Record... records){
        recordRepository.DeleteRecord(records);
    }
    public void ClearRecord(){
        recordRepository.ClearRecords();
    }
    public void UpdateRecord(Record... records){
        recordRepository.UpdateRecords(records);
    }


}
