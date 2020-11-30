package com.example.notebook.Test;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Record {

    @PrimaryKey(autoGenerate = true)
    private int Id;
    @ColumnInfo(name = "Title")
    private String Title;
    @ColumnInfo(name = "TimeStart")
    private String TimeStart;
    @ColumnInfo(name = "TimeMax")
    private String TimeMax;
    @ColumnInfo(name = "TimeCount")
    private String TimeCount;
    @ColumnInfo(name = "timeMax1")
    private int timeMax1;
    @ColumnInfo(name = "timeUse1")
    private int timeUse1;
    @ColumnInfo(name = "isFinished")
    private boolean isFinished;

    public Record(String Title,String TimeMax){
        this.Title = Title;
        this.TimeMax = TimeMax;
        this.TimeStart = "未开始";
        this.TimeCount = "00:00";
        this.timeMax1 = 0;
        this.timeUse1 = 0;
        this.isFinished = false;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTimeStart() {
        return TimeStart;
    }

    public void setTimeStart(String timeStart) {
        TimeStart = timeStart;
    }

    public void setTimeMax(String timeMax) {
        TimeMax = timeMax;
    }

    public String getTimeMax() {
        return TimeMax;
    }


    public String getTimeCount() {
        return TimeCount;
    }

    public void setTimeCount(String timeCount) {
        TimeCount = timeCount;
    }

    public int getTimeMax1() {
        return timeMax1;
    }

    public void setTimeMax1(int timeMax1) {
        this.timeMax1 = timeMax1;
    }

    public int getTimeUse1() {
        return timeUse1;
    }

    public void setTimeUse1(int timeUse1) {
        this.timeUse1 = timeUse1;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
