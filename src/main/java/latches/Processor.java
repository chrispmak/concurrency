package latches;

import java.util.concurrent.CountDownLatch;

/**
 * Created by ponyboy on 9/29/2016.
 */
public class Processor implements Runnable {

    private CountDownLatch latch;

    public Processor(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("Started");

        try {
            Thread.sleep(3000);
            System.out.println("done thread");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        latch.countDown();
    }
}
