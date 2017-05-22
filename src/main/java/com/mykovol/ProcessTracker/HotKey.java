package com.mykovol.ProcessTracker;

import com.melloware.jintellitype.JIntellitype;


/**
 * Created by MykoVol on 2/20/2017.
 */
public class HotKey {
    void init() {
        if (System.getProperty("sun.arch.data.model").equals("32")) JIntellitype.setLibraryLocation(getClass().getClassLoader().getResource("JIntellitype.dll").getPath());
        else JIntellitype.setLibraryLocation( getClass().getClassLoader().getResource("JIntellitype64.dll").getPath());

//        show sync with db summary screen on Ctrl+Alt+T
        JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_CONTROL + JIntellitype.MOD_ALT, (int) 'T');
        JIntellitype.getInstance().addHotKeyListener(arg0 -> {
            if (arg0 == 1)
                ProcessTrackerSummary.getInstance().showForm();
        });
    }

}