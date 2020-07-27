package com.ibm.java.fp.innercls;

class Runner implements Runnable {
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }

}

public class ThreadInnercls {
    public static void main(String[] args) {

        Thread thread = null;
        thread = new Thread(new Runner());
        //start
        thread.start();
        thread = new Thread(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName());

            }
        });
        thread.start();
    }
}
