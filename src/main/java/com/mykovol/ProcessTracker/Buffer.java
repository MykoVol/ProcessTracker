package com.mykovol.ProcessTracker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Created by MykoVol on 3/5/2017.
 */
final class Buffer {
    private static Logger logger = Logger.getLogger(Buffer.class);
    private static final List<ProcessDetails> mylist = new ArrayList<>();
    private static Buffer ourInstance = new Buffer();

    public static Buffer getInstance() {
        return ourInstance;
    }

    synchronized static void addEntry(ProcessDetails procDet) {
        if (procDet != null) {
            mylist.add(procDet);
            logger.debug("added to buffer. Size - " + mylist.size());
        }
    }

    synchronized static void sync() {
        for (Iterator<ProcessDetails> i = mylist.iterator(); i.hasNext(); ) {
            ProcessDetails item = i.next();
//            remove from buffer when sync with DB is successful
            if (WorkWithDB.getInstance().addTrack(item)) {
                i.remove();
                logger.debug("synced with DB. Left - " + mylist.size());
            }
        }
    }
}
