package com.example.notebook;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface itemDao{

    @Insert
    void InsertItem(item... items); //插入学习记录

    @Update
    void UpdateItem(item... items); //更新学习记录

    @Delete
    void DeleteItem(item... items); //删除学习记录

    //根据记录类型进行查找
    @Query("SELECT * FROM item  WHERE itemType = :type " + "ORDER BY dateMonth and dateDay DESC")
    LiveData<List<item>>getTypeItem(int type);

    @Query("DELETE FROM ITEM")  //清空删除记录
    void ClearItem();

    //根据标题模糊匹配查找
    @Query("SELECT * FROM item WHERE title LIKE :pattern and itemType = :Type ORDER BY dateMonth and dateDay DESC")
    LiveData<List<item>>getItemWithPattern(String pattern,int Type);

    //执行艾宾浩斯算法
    @Query("SELECT * FROM item WHERE (dateMonth = :Month and dateDay =:Day and ifRemember = 1)" +
            "or (dateMonth = :Month2 and dateDay =:Day2 and ifRemember = 1)" +
            "or (dateMonth = :Month3 and dateDay =:Day3 and ifRemember = 1)" +
            "or (dateMonth = :Month4 and dateDay =:Day4 and ifRemember = 1)" +
            "or (dateMonth = :Month5 and dateDay =:Day5 and ifRemember = 1) ORDER BY dateMonth and dateDay")
    LiveData<List<item>>getReviewItem(int Month,int Day,int Month2,int Day2,
                                      int Month3, int Day3,int Month4,int Day4,int Month5,int Day5);
}
