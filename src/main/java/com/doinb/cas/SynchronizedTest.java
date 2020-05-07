package com.doinb.cas;

public class SynchronizedTest {

    public void hello() {

        synchronized (SynchronizedTest.class) {

            System.out.println("hello world...");
        }
    }

    public synchronized void helloLock() {

        System.out.println("helloLock...");

    }
}
