package com.ibm.java.fp.lambda;

//functional interface

/**
 * 1.interface must have single abstract method : SAM
 */
interface Greeter {
    //abstract method
    void sayGreet();
}

public class BasicLambda {
    public static void main(String[] args) {
        //way 1 : using legacy innerclass
        Greeter greet = null;
        greet = new Greeter() {
            public void sayGreet() {
                System.out.println("Hello annonous inner class");
            }
        };
        greet.sayGreet();
        //way 2: using java 8' Lambda syntax
        greet = () -> {
            System.out.println("Hello Using lambda");
        };
        greet.sayGreet();

        //Thread
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };
        Thread thread = null;
        //using runnable interface annonus
        thread = new Thread(runnable);
        thread.start();
        //inline
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
        thread.start();

        //lambda version
        Runnable newRunnable = () -> {
            System.out.println(Thread.currentThread().getName());
        };
        thread = new Thread(newRunnable);
        thread.start();

        //inline lambda version
        thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        });
        thread.start();


    }
}
