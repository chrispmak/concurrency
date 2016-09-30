package lowlevel.sync;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by ponyboy on 9/30/2016.
 */
public class Producer {

    private LinkedList<Integer> list = new LinkedList<>();
    private final int LIMIT = 10;
    private Object lock = new Object();

    public void produce() throws InterruptedException {
        int value = 0;
        while(true) {
            synchronized (lock) {
                while(list.size() == LIMIT) {
                    System.out.println("Producer wait...");
                    lock.wait();
                }
                list.add(value++);
                lock.notify();
            }
        }
    }

    public void consume() throws InterruptedException {
        Random random = new Random();
        while(true) {
            synchronized (lock) {
                while (list.size() == 0) {
                    System.out.println("Consumer waiting...");
                    lock.wait();
                }
                System.out.print("List size is: " + list.size());
                int value = list.removeFirst();
                System.out.println("; value is: " + value);
                lock.notify();
            }

            Thread.sleep(random.nextInt(500));
        }
    }
}
