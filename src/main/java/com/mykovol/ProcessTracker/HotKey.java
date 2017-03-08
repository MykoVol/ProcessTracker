package com.mykovol.ProcessTracker;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

/**
 * Created by MykoVol on 2/20/2017.
 */
public class HotKey {
    void init() {
        JIntellitype.getInstance();
        JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_CONTROL + JIntellitype.MOD_ALT, (int) 'T');
        JIntellitype.getInstance().addHotKeyListener(arg0 -> {
            // show statistic window on Ctrl+Alt+T
            if (arg0 == 1)
                Process.getProcess();

        });
    }

}