package com.mykovol.ProcessTracker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.sun.jna.NativeLibrary.getProcess;

/**
 * Created by MykoVol on 2/21/2017.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        AppProperties.getProperties();

//        new HotKey().init();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> Buffer.addEntry(Process.getProcess()), 0, 1, TimeUnit.SECONDS);

        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(1);
        executor2.scheduleWithFixedDelay(Buffer::sync, 0, 3, TimeUnit.SECONDS);
    }
}
