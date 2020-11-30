package com.example.notebook.Test;

import android.content.Context;

import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TestRepository {

    private LiveData<List<TestItem>> allTestItem;
    private TestDao testDao;

    public TestRepository(Context context) {
        TestItemDataBase testItemDataBase = TestItemDataBase.getDatabase(context.getApplicationContext());
        this.testDao = testItemDataBase.getTestItemDao();
        allTestItem = testDao.getAllTestItems();
    }

    public LiveData<List<TestItem>> getAllTestItems(){
        return allTestItem;
    }

    public void InsertTestItem(TestItem... items){
        new InsertAsyncTask(testDao).execute(items);
    }
    public void DeleteTestItem(TestItem... items){
        new DeleteAsyncTask(testDao).execute(items);
    }
    public void ClearTestItem(){
        new ClearAsyncTask(testDao).execute();
    }
    public void UpdateTestItem(TestItem... items){
        new UpdateAsyncTask(testDao).execute(items);
    }
    public TestItem getTestItemWithTitle(String TestTitle) throws ExecutionException, InterruptedException {
        return new GetWithTitleAsyncTask(testDao).execute(TestTitle).get();
    }

    static class InsertAsyncTask extends AsyncTask<TestItem,Void,Void> {
        private TestDao testDao;

        InsertAsyncTask(TestDao testDao) {
            this.testDao = testDao;
        }

        @Override
        protected Void doInBackground(TestItem... items) {
            testDao.InsertTestItem(items);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<TestItem,Void,Void> {
        private TestDao testDao;

        UpdateAsyncTask(TestDao testDao) {
            this.testDao = testDao;
        }

        @Override
        protected Void doInBackground(TestItem... items) {
            testDao.UpdateTestItem(items);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<TestItem,Void,Void>{
        private TestDao testDao;

        DeleteAsyncTask(TestDao testDao) {
            this.testDao = testDao;
        }

        @Override
        protected Void doInBackground(TestItem... items) {
            testDao.DeleteTestItem(items);
            return null;
        }
    }

    static class ClearAsyncTask extends AsyncTask<TestItem,Void,Void>{
        private TestDao testDao;

        ClearAsyncTask(TestDao testDao) {
            this.testDao = testDao;
        }

        @Override
        protected Void doInBackground(TestItem... items) {
            testDao.ClearTestItem();
            return null;
        }
    }

    static class GetWithTitleAsyncTask extends AsyncTask<String, Void, TestItem> {

        private TestDao testDao;
        GetWithTitleAsyncTask(TestDao testDao){
            this.testDao = testDao;
        }

        @Override
        protected TestItem doInBackground(String... strings) {
            return testDao.getTestItemsWithTitle(strings[0]);
        }
    }


}
