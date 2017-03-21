package com.mykovol.ProcessTracker;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Created by MykoVol on 3/5/2017.
 */
class Buffer {
    private static final ArrayList<ProcessDetails> PROCESS_DETAILS = new ArrayList<>();
    private static final Logger LOGGER = Logger.getLogger(Buffer.class);

    static void addEntry(ProcessDetails procDet) {
        if (procDet != null) {
            synchronized (PROCESS_DETAILS) {
                PROCESS_DETAILS.add(procDet);
            }
            LOGGER.trace("Item added to buffer");
        }
    }

    static void sync() {
        List<ProcessDetails> listToSync = null;
//        copy all elements to syncList to release main list
        synchronized (PROCESS_DETAILS) {
            listToSync = new ArrayList<>(PROCESS_DETAILS);
        }

        WorkWithDB.getInstance().insertTrackList(listToSync);

//        delete all synced items from main list
        synchronized (PROCESS_DETAILS) {
            PROCESS_DETAILS.removeAll(listToSync);
            LOGGER.debug("Sync size - " + listToSync.size() + ". Buffer size - " + PROCESS_DETAILS.size());

        }
    }
}
