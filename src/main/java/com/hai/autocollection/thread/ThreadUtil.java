package com.hai.autocollection.thread;

import org.apache.tomcat.jni.Lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author created by hai on 2020/1/20
 */
public class ThreadUtil {

    public static Thread[] findAllThreads() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        /* 遍历线程组树，获取根线程组 */
        while ( group != null ) {
            topGroup = group;
            group = group.getParent();
        }
        /* 激活的线程数加倍 */
        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];

        /* 获取根线程组的所有线程 */
        int actualSize = topGroup.enumerate( slackList );
        /* copy into a list that is the exact size */
        Thread[] list = new Thread[actualSize];
        System.arraycopy( slackList, 0, list, 0, actualSize );
        return (list);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = Thread.currentThread();
        ThreadGroup threadGroup = thread.getThreadGroup();
        Thread t1 =  new Thread(threadGroup,()->{
            try {
                Thread.currentThread().wait();
                synchronized (Thread.currentThread()){
                    System.out.println("线程停止");

                }
                System.out.println("唤醒成功");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"test1");
        Thread t2 = new Thread(threadGroup,()->{
            while (true){}
        },"test1");
        t1.start();
        t2.start();
        Thread.sleep(1000);
        synchronized (t1){
            t1.notify();
        }
        threadGroup.list();
        System.out.println(threadGroup.activeCount());
    }
}
