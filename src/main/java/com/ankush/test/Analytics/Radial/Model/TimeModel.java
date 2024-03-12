package com.ankush.test.Analytics.Radial.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class TimeModel {
    @Getter
    long hours;
    @Getter
    long pending;
    @Getter
    long complete;
    Date date;
//    @Getter
//    String date;

    public TimeModel(long hours,long timestamp) {
        this.hours = hours;
        date=new Date(timestamp);
    }

    public void set(String s, long val){
        if(s.equalsIgnoreCase("COMPLETE")){
            complete=val;
        }else {
            pending=val;
            complete+=pending;
        }
    }
}
