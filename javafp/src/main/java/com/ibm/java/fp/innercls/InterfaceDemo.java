package com.ibm.java.fp.innercls;

//declare interface
interface Greeter {
    void sayGreet();
}
//implementation for the above interface

/**
 * ways
 * 1. via Explicit class
 * 2. without writing explicit class : inner class : anonymous inner class
 */

class GreeterImpl implements Greeter {
    public void sayGreet() {
        System.out.println("Hello Vertx How are you?");
    }
}


public class InterfaceDemo {
    public static void main(String[] args) {

        //declare Greeter
        Greeter greeterImpl = null;
        greeterImpl = new GreeterImpl();
        greeterImpl.sayGreet();
        //inner class : anonymous inner class
        greeterImpl = new Greeter() {
            public void sayGreet() {
                System.out.println("Hello , anonymous ");
            }
        };
        greeterImpl.sayGreet();


    }
}
