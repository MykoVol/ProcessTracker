package com.mykovol.ProcessTracker;

import com.melloware.jintellitype.JIntellitype;


/**
 * Created by MykoVol on 2/20/2017.
 */
public class HotKey {
    void init() {

        if (System.getProperty("sun.arch.data.model").equals("32")) JIntellitype.setLibraryLocation("lib/JIntellitype.dll");
        else JIntellitype.setLibraryLocation("lib/JIntellitype64.dll");

//        show sync with db summary screen on Ctrl+Alt+T
        JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_CONTROL + JIntellitype.MOD_ALT, (int) 'T');
        JIntellitype.getInstance().addHotKeyListener(arg0 -> {
            if (arg0 == 1)
                PTBoard.getInstance().showForm();
        });
    }

}