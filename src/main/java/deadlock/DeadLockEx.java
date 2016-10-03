package deadlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ponyboy on 10/3/2016.
 */
public class DeadLockEx {
    private static Logger log = LoggerFactory.getLogger(DeadLockEx.class);

    private Account acc1 = new Account();
    private Account acc2 = new Account();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    private void acquireLock(Lock firstLock, Lock secondLock) throws InterruptedException {
        while(true) {
            boolean gotFirstLock = false;
            boolean gotSecondLock = false;

            try {
                gotFirstLock = firstLock.tryLock();
                gotSecondLock = secondLock.tryLock();
            }finally {
                if(gotFirstLock && gotSecondLock) {
                    return;
                }

                if(gotFirstLock) {
                    firstLock.unlock();
                }

                if(gotSecondLock) {
                    secondLock.unlock();
                }
            }

            Thread.sleep(1);
        }
    }

    private Random random = new Random();

    public void firstThread() throws InterruptedException{
        for(int i = 0; i < 10000; i++) {
            acquireLock(lock1, lock2);
            try {
//                log.info("transferring from 1 to 2");
                Account.transfer(acc1, acc2, random.nextInt(100));
            } finally {
                log.info("unlocking 1 and 2 from first thread");
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void secondThread() throws InterruptedException {
        for(int i = 0; i < 10000; i++) {
            acquireLock(lock2, lock1);
            try {
//                log.info("transferring from 2 to 1");
                Account.transfer(acc2, acc1, random.nextInt(100));
            } finally {
                log.info("unlocking 1 and 2 from second thread");
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finished() {
        log.info("Account 1 balance: {}", acc1.getBalance());
        log.info("Account 2 balance: {}", acc2.getBalance());
        log.info("Total balance: {}", acc1.getBalance() + acc2.getBalance());
    }

    public static void main(String[] args) {
        final DeadLockEx deadLockEx = new DeadLockEx();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    deadLockEx.firstThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    deadLockEx.secondThread();
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


        deadLockEx.finished();


    }

}
