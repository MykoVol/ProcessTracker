import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by MykoVol on 3/5/2017.
 */
public class ProcessDetails {
    private String procName;
    private String procTitle;
    private Timestamp DateTime;

    public ProcessDetails(String proc_name, String proc_title) {
        this.procName = proc_name;
        this.procTitle = proc_title;

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        this.DateTime =  new java.sql.Timestamp(now.getTime());
    }

    public String getProcName() {
        return procName;
    }

    public String getProcTitle() {
        return procTitle;
    }

    public Timestamp getDateTime() {
        return DateTime;
    }
}
