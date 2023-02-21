package requests;

import java.util.concurrent.TimeUnit;

public abstract class AbstractRequest {

    public static void timeout(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
