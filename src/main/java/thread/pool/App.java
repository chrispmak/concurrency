package thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by ponyboy on 9/29/2016.
 */
public class App {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for(int i = 0; i < 5; i++) {
            executorService.submit(new Processor(i));
        }

        executorService.shutdown();

        try {
            boolean timedOut = executorService.awaitTermination(1, TimeUnit.DAYS);
            System.out.println("wait: " + !timedOut);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All tasks submitted.");
    }
}
