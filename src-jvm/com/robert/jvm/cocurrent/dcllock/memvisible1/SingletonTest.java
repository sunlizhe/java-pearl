package com.robert.jvm.cocurrent.dcllock.memvisible1;

public class SingletonTest {
    public static void test(int threadCounter) throws InterruptedException {
        Thread[] ts = new Thread[threadCounter];

        for (int i = 0; i < threadCounter; i++) {
            ts[i] = new Thread() {
                @Override
                public void run() {
                    Singleton t = Singleton.getSingleton();

                    if (t.getData() != 10000)
                        throw new RuntimeException("Expose the singleton whitouting initializing its primitive data field!");
                }
            };
        }

        for (int i = 0; i < threadCounter; i++) {
            ts[i].start();
        }

        for (int i = 0; i < threadCounter; i++) {
            ts[i].join();
        }

        if (Singleton.getCoutner() != 1) throw new RuntimeException("Multiple instances (" + Singleton.getCoutner() + ") are created!");
    }

    public static void main(String[] args) throws Exception {
        final int threadCounter = 10;
        final int testTimes = 100;

        for (int i = 0; i < testTimes; i++) {
            test(threadCounter);
            Singleton.reset();
        }
    }
}
