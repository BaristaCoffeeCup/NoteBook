package com.example.notebook;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class item {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "dateMonth")
    private int dateMonth;
    @ColumnInfo(name = "dateDay")
    private int dateDay;
    @ColumnInfo(name = "ifRemember")
    private boolean ifRemember = false;
    @ColumnInfo(name = "itemType")
    private int itemType;

    public item(String title,String content,int dateMonth,int dateDay,int itemType){
        this.title = title;
        this.content = content;
        this.dateMonth = dateMonth;
        this.dateDay = dateDay;
        this.itemType = itemType;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(int dateMonth) {
        this.dateMonth = dateMonth;
    }

    public int getDateDay() {
        return dateDay;
    }

    public void setDateDay(int dateDay) {
        this.dateDay = dateDay;
    }

    public boolean isIfRemember() {
        return ifRemember;
    }

    public void setIfRemember(boolean ifRemeber) {
        this.ifRemember = ifRemeber;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
