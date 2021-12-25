package Backend;

import Frontend.MainScreen;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Timer {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final ScheduledExecutorService stopper = Executors.newScheduledThreadPool(1);
    private static double minutes = 0;

    public Timer() {
        minutes = 0;
        Runnable increment = () -> minutes++;
        ScheduledFuture<?> scheduleHandle = scheduler.scheduleAtFixedRate(increment,
                1, 1, MINUTES);
        scheduler.schedule(new Runnable() {
            public void run() {
                if(!MainScreen.getActiveJobSession()){
                    scheduleHandle.cancel(true);
                }
            }
        }, 1,  SECONDS);
    }

    public static double getMinutes() {
        return minutes;
    }
}
