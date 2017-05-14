package com.mykovol.ProcessTracker;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by MykoVol on 3/5/2017.
 */
class ProcessDetails implements Serializable{
    private String procName;
    private String procTitle;
    private Timestamp DateTime;

    ProcessDetails(String proc_name, String proc_title) {
        this.procName = proc_name;
        this.procTitle = proc_title;

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        this.DateTime = new java.sql.Timestamp(now.getTime());
    }

    String getProcName() {
        return procName;
    }

    String getProcTitle() {
        return procTitle;
    }

    Timestamp getDateTime() {
        return DateTime;
    }
}
