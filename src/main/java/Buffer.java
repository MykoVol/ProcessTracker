import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;f

/**
 * Created by MykoVol on 3/5/2017.
 */
public class Buffer {
    private static Logger log = Logger.getLogger(Buffer.class.getName());
    private static final List<ProcessDetails> mylist = new ArrayList<>();
    private static Buffer ourInstance = new Buffer();

    public static Buffer getInstance() {
        return ourInstance;
    }

    public synchronized static void addEntry(ProcessDetails procDet) {
        if (procDet != null) {
            mylist.add(procDet);
            log.log(Level.FINE, "item " + procDet.getDateTime() + " added to buffer. Size - " + mylist.size());
        }
    }

    public synchronized static void sync() {
        for (Iterator<ProcessDetails> i = mylist.iterator(); i.hasNext(); ) {
            ProcessDetails item = i.next();
//            remove from buffer when sync with DB is successful
            if (WorkWithDB.getInstance().addTrack(item)) {
                i.remove();
                log.log(Level.FINE, "item " + item.getDateTime() + " synced with DB. Size - " + mylist.size());
            }
        }
    }
}
