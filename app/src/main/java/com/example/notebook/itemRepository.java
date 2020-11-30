package com.example.notebook;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class itemRepository {

    private LiveData<List<item>> allItemTypeLive;
    private itemDao itemDao;

    public itemRepository(Context context) {
        ItemDataBase itemDataBase = ItemDataBase.getDatabase(context.getApplicationContext());
        itemDao = itemDataBase.getItemDao();
    }

    public LiveData<List<item>> getallItemTypeLive(int Type) {
        allItemTypeLive = itemDao.getTypeItem(Type);
        return itemDao.getTypeItem(Type);
    }

    public LiveData<List<item>>getItemWithPattern(String Pattern,int Type){
        return itemDao.getItemWithPattern("%"+Pattern+"%",Type);
    }

    public LiveData<List<item>>getReviewItem(int Month,int Day,int Month2,int Day2,int Month3, int Day3,int Month4,int Day4,int Month5,int Day5){
        return itemDao.getReviewItem(Month,Day,Month2,Day2,Month3,Day3,Month4,Day4,Month5,Day5);
    }

    void InsertItem(item... items){
        new InsertAsyncTask(itemDao).execute(items);
    }
    void DeleteItem(item... items){
        new DeleteAsyncTask(itemDao).execute(items);
    }
    void UpdateItem(item... items){
        new UpdateAsyncTask(itemDao).execute(items);
    }
    void ClearItem(){
        new ClearAsyncTask(itemDao).execute();
    }



    static class InsertAsyncTask extends AsyncTask<item,Void,Void> {
        private itemDao itemDao;

        InsertAsyncTask(itemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(item... items) {
            itemDao.InsertItem(items);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<item,Void,Void>{
        private itemDao itemDao;

        UpdateAsyncTask(itemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(item... items) {
            itemDao.UpdateItem(items);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<item,Void,Void>{
        private itemDao itemDao;

        DeleteAsyncTask(itemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(item... items) {
            itemDao.DeleteItem(items);
            return null;
        }
    }

    static class ClearAsyncTask extends AsyncTask<item,Void,Void>{
        private itemDao itemDao;

        ClearAsyncTask(itemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Void doInBackground(item... items) {
            itemDao.ClearItem();
            return null;
        }
    }


}
