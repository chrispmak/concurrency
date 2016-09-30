package wait.and.notify;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by ponyboy on 9/29/2016.
 */
public class App {

    public static void main(String[] args) throws InterruptedException {
        final Producer producer = new Producer();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producer.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producer.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();


    }
}
