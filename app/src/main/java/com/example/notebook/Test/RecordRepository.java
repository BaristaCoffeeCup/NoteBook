package com.example.notebook.Test;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Update;

import java.util.List;

public class RecordRepository {

    private LiveData<List<Record>> AllRecord;
    private RecordDao recordDao;

    public RecordRepository(Context context){
        RecordDatabase recordDatabase = RecordDatabase.getDatabase(context.getApplicationContext());
        this.recordDao = recordDatabase.getRecordDao();
        AllRecord = recordDao.getAllRecord();
    }

    public LiveData<List<Record>> getAllRecord(){
        return AllRecord;
    }

    public void InsertRecord(Record... records){
        new InsertAsyncTask(recordDao).execute(records);
    }
    public void DeleteRecord(Record... records){
        new DeleteAsyncTask(recordDao).execute(records);
    }
    public void ClearRecords(){
        new ClearAsyncTask(recordDao).execute();
    }
    public void UpdateRecords(Record... records){
        new UpdateAsyncTask(recordDao).execute(records);
    }



    static class InsertAsyncTask extends AsyncTask<Record,Void,Void> {
        private RecordDao recordDao;

        InsertAsyncTask(RecordDao testDao) {
            this.recordDao = testDao;
        }

        @Override
        protected Void doInBackground(Record... items) {
            recordDao.InsertRecord(items);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<Record,Void,Void>{
        private RecordDao recordDao;

        DeleteAsyncTask(RecordDao testDao) {
            this.recordDao = testDao;
        }

        @Override
        protected Void doInBackground(Record... items) {
            recordDao.DeleteRecord(items);
            return null;
        }
    }

    static class ClearAsyncTask extends AsyncTask<Record,Void,Void>{
        private RecordDao recordDao;

        ClearAsyncTask(RecordDao testDao) {
            this.recordDao = testDao;
        }

        @Override
        protected Void doInBackground(Record... items) {
            recordDao.ClearRecord();
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Record,Void,Void>{
        private RecordDao recordDao;

        UpdateAsyncTask(RecordDao testDao) {
            this.recordDao = testDao;
        }

        @Override
        protected Void doInBackground(Record... items) {
            recordDao.UpdateRecord(items);
            return null;
        }
    }
}
