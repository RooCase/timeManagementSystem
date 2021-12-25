package Backend;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Session {

    private ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
    int i;

    public Session() {
        i = 0;
    }
}
