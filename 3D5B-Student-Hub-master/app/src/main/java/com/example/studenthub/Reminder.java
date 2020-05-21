package com.example.studenthub;

public class Reminder {
    public String moduleTitle, assignMsg, date, time;

    public Reminder(){}

    public Reminder( String mTitle, String aMsg, String date, String time){
        this.moduleTitle = mTitle;
        this.assignMsg = aMsg;
        this.date = date;
        this.time = time;
    }
}
