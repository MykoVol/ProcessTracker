import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.PointerByReference;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by MykoVol on 2/22/2017.
 */
public class Process {
    private static final int MAX_TITLE_LENGTH = 1024;
    private static Logger log = Logger.getLogger(Buffer.class.getName());

    @Nullable
    public static ProcessDetails getProcess() {
        String procTitle;
        String procName;
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];

        PointerByReference pointer = new PointerByReference();
        User32DLL.GetWindowThreadProcessId(User32DLL.GetForegroundWindow(), pointer);
        Pointer process = Kernel32.OpenProcess(Kernel32.PROCESS_QUERY_INFORMATION | Kernel32.PROCESS_VM_READ, false, pointer.getValue());
        Psapi.GetModuleBaseNameW(process, null, buffer, MAX_TITLE_LENGTH);
        procName = Native.toString(buffer);
        if (procName == null) {
            log.log(Level.WARNING, "procName is empty");
            return null;
        } else {
            log.log(Level.FINER, "procName " + procName);
        }

        User32DLL.GetWindowTextW(User32DLL.GetForegroundWindow(), buffer, MAX_TITLE_LENGTH);
        procTitle = Native.toString(buffer);
//        procName = procName.substring(procName.lastIndexOf('-')+2);
        log.log(Level.FINER, "procTitle " + procTitle);

        return new ProcessDetails(procName, procTitle);
    }

    static class Psapi {
        static {
            Native.register("psapi");
        }

        public static native int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size);
    }

    static class Kernel32 {
        static {
            Native.register("kernel32");
        }

        public static int PROCESS_QUERY_INFORMATION = 0x0400;
        public static int PROCESS_VM_READ = 0x0010;

        public static native int GetLastError();

        public static native Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
    }

    static class User32DLL {
        static {
            Native.register("user32");
        }

        public static native int GetWindowThreadProcessId(WinDef.HWND hWnd, PointerByReference pref);

        public static native WinDef.HWND GetForegroundWindow();

        public static native int GetWindowTextW(WinDef.HWND hWnd, char[] lpString, int nMaxCount);
    }
}
