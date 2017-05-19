package com.mykovol.ProcessTracker;

import org.apache.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.sun.jna.NativeLibrary.getProcess;

/**
 * Created by MykoVol on 2/21/2017.
 */
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

//        save buffer to file before exist
        Runtime.getRuntime().addShutdownHook(new Thread((() -> Buffer.saveToFile())));
//        download saved buffer on start up
        Buffer.readFromFile();

        AppProperties.getProperties();
//        second part of a DB passwords is provided by parameter (DB pass = configPass + paramPass)
        AppProperties.setJdbcPassword(args);

        new HotKey().init();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> Buffer.addEntry(Process.getProcess()), 0, 1, TimeUnit.SECONDS);

        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(1);
        executor2.scheduleWithFixedDelay(Buffer::sync, 0, 3, TimeUnit.SECONDS);




    }
}
