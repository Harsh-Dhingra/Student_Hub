package com.example.studenthub;

public class ReminderDisplay {
    public String reminderKey, moduleTitle, assignMsg, date, time;

    public ReminderDisplay(){}

    public ReminderDisplay(String rKey, String mTitle, String aMsg, String date, String time){
        this.reminderKey = rKey;
        this.moduleTitle = mTitle;
        this.assignMsg = aMsg;
        this.date = date;
        this.time = time;
    }
}
