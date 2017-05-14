package com.mykovol.ProcessTracker;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Created by MykoVol on 3/5/2017.
 */
class Buffer {
    private static final ArrayList<ProcessDetails> PROCESS_DETAILS = new ArrayList<>();
    private static Instant lastSyncTime;
    private static int lastSyncCount;

    public static Duration getLastSyncTimeAgo() {
        if (lastSyncTime == null) {
            return Duration.ZERO;
        }
        return Duration.between(lastSyncTime, Instant.now());
    }

    public static void setLastSyncTime() {
        Buffer.lastSyncTime = Instant.now();
    }

    public static int getLastSyncCount() {
        return lastSyncCount;
    }

    public static void setLastSyncCount(int lastSyncCount) {
        Buffer.lastSyncCount = lastSyncCount;
    }

    private static final Logger LOGGER = Logger.getLogger(Buffer.class);

    public static int getBufferSize() {
        return PROCESS_DETAILS.size();
    }

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
        if (!listToSync.isEmpty()) {
            synchronized (PROCESS_DETAILS) {
                PROCESS_DETAILS.removeAll(listToSync);
            }
            setLastSyncTime();
            setLastSyncCount(listToSync.size());
        }
        LOGGER.debug("Sync size - " + listToSync.size() + ". Buffer size - " + PROCESS_DETAILS.size());
    }

    static void saveToFile() {
        if (!PROCESS_DETAILS.isEmpty()) {
            try (FileOutputStream fout = new FileOutputStream("buffer.dat", false);
                 ObjectOutputStream oos = new ObjectOutputStream(fout)) {
                oos.writeObject(PROCESS_DETAILS);
                LOGGER.info("Saved buffer with size - " + PROCESS_DETAILS.size() + " to file system");
            } catch (Exception e) {
                LOGGER.error(e);
            }
        }
    }

    static void readFromFile() {
        try (FileInputStream streamIn = new FileInputStream("buffer.dat");
             ObjectInputStream objectinputstream = new ObjectInputStream(streamIn)) {
            ArrayList<ProcessDetails> readCase = (ArrayList<ProcessDetails>) objectinputstream.readObject();
            if (!readCase.isEmpty()) PROCESS_DETAILS.addAll(readCase);
            LOGGER.info("Read buffer with size - " + readCase.size() + " from file system");
        } catch (FileNotFoundException e) {
            LOGGER.error("No saved buffer to read", e);
        } catch (Exception e) {
            LOGGER.error(e);
        }

    }
}
