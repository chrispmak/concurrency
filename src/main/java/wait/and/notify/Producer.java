package wait.and.notify;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by ponyboy on 9/30/2016.
 */
public class Producer {



    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer thread running");
            wait();
            System.out.println("Produer resumed.");
        }
    }

    public void consume() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        Thread.sleep(2000);
        synchronized (this) {
            System.out.println("waiting for return key.");
            scanner.nextLine();
            System.out.println("Return key pressed.");
            notify();
        }
    }
}
