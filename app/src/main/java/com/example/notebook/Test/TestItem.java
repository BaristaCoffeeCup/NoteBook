package com.example.notebook.Test;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TestItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "testTitle")
    private String testTitle;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "finishtime")
    private String finishtime;

    @ColumnInfo(name = "maxtime")
    private String maxtime;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTestTitle() {
        return testTitle;
    }
    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getFinishtime() {
        return finishtime;
    }
    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }

    public String getMaxtime() {
        return maxtime;
    }

    public void setMaxtime(String maxtime) {
        this.maxtime = maxtime;
    }

    public TestItem(String date, String testTitle, String title, String finishtime,String maxtime){
        this.testTitle = testTitle;
        this.date = date;
        this.title = title;
        this.finishtime = finishtime;
        this.maxtime = maxtime;
    }

}
