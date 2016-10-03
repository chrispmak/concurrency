package reentrant.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ponyboy on 10/3/2016.
 */
public class ReentrantLockEx {
    private static Logger log = LoggerFactory.getLogger(ReentrantLockEx.class);
    private Lock lock = new ReentrantLock();
    private int count = 0;
    private Condition cond = lock.newCondition();

    private void increment() {
        for(int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public void firstThread() throws InterruptedException{
        log.info("locking first thread");
        lock.lock();

        log.info("waiting...");
        cond.await();
        log.info("woke up");

        try {
            log.info("incremeting first thread");
            increment();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("unlocking first thread");
            lock.unlock();
        }

    }

    public void secondThread() throws InterruptedException {
        log.info("locking second thread");
        Thread.sleep(1000);
        lock.lock();

        log.info("press return key.");
        new Scanner(System.in).nextLine();
        log.info("got the return key");
        cond.signal();

        try {
            log.info("incremeting second thread");
            increment();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("unlocking second thread");
            lock.unlock();
        }
    }

    public void finished() {
        log.info("Count is: {}", count);
    }

    public static void main(String[] args) {
        final ReentrantLockEx reentrantLockEx = new ReentrantLockEx();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    reentrantLockEx.firstThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    reentrantLockEx.secondThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();


        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        reentrantLockEx.finished();


    }

}
