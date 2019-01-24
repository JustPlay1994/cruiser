package com.justplay1994.github.cruiser.core.route.impl;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Package: com.justplay1994.github.cruiser.core.route.impl
 * @Project: cruiser
 * @Creator: huangzezhou
 * @Create_Date: 2019/1/22 17:18
 * @Updater: huangzezhou
 * @Update_Date: 2019/1/22 17:18
 * @Update_Description: huangzezhou 补充
 * @Description:
 **/
public class ConcurrentRoutingTableTest {

    //TODO： 实现分段读写锁，每一个项进行加锁。
    //TODO： 线程生命周期状态获取，以及控制。例如interrupt、interrupted、runnable、block、await()\、signalAll()等
    //TODO:  防止死锁

    @Test
    public void readWriteLockTest() {

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        



//        System.out.println("writeHoldCount = "+lock.getWriteHoldCount());
//        System.out.println("readHoldCount="+lock.getReadHoldCount());
//        System.out.println("queueLength"+lock.getQueueLength());
//        System.out.println("readLockCount="+lock.getReadLockCount());
//        System.out.println(lock.getWaitQueueLength());

        class ReadThread extends Thread{

            @Override
            public void run() {

                lock.readLock().lock();

                System.out.println("Read start!");
                System.out.println("readHoldCount="+lock.getReadHoldCount());//当前线程拥有的线程数量
                System.out.println("readLockCount="+lock.getReadLockCount());
                for (int i = 0; i < 10; ++i){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Read finished!");
                lock.readLock().unlock();

            }
        }

        class WriteThread implements Runnable{

            @Override
            public void run() {
                lock.writeLock().lock();

                System.out.println("Write start");
                for (int i = 0; i < 10; ++i){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Write finished!");
                lock.writeLock().unlock();
            }

        }



        ThreadPoolExecutor executor = new ThreadPoolExecutor(8,8,100, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());

        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            executor.execute(new ReadThread());
            executor.execute(new WriteThread());
        }

        while (!(executor.getActiveCount() == 0 && executor.getQueue().size() == 0)){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    @Test
    public void reentrantLockTest(){

    }

    /**
     * 与 synchronized 相比，重入锁有着显示的操作过程，何时加锁，何时释放，都在程序员的控制中。
     */
    static class ReenterLock implements Runnable{
        public static ReentrantLock lock = new ReentrantLock();
        public static int i = 0;
        public void run() {
            for (int j = 0;j<100000;j++) {
                lock.lock();
//            lock.lock();
                try {
                    i++;
                }finally {
//                    lock.unlock();
                lock.unlock();
                }
            }
        }
        public static void main(String[] args) throws InterruptedException {
            ReenterLock reenterLock = new ReenterLock();
            Thread t1 = new Thread(reenterLock);
            Thread t2 = new Thread(reenterLock);
            t1.start();t2.start();
            t1.join();t2.join();
            System.out.println(i);
        }
    }

    public void mutexTest(){

    }

    @Test
    public void ReentrantReadWriteLockMaxTest(){
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    }

}