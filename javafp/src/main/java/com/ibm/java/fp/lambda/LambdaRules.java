package com.ibm.java.fp.lambda;

//SAM
interface Welcome {
    //static methods
    public static void doSomething() {
        System.out.println("Do Something");
    }

    public static void doSomething1() {
        System.out.println("Do Something");
    }

    //only one abstract method
    void sayHello();

    //void sayHai(); //IT wil throw error
    //default methods
    public default void saySomething() {
        System.out.println("i am saying something");
    }

    public default void saySomething1() {
        System.out.println("i am saying something");
    }

}
//throw compile time error during interface design :
//there is an annotation which marks /tells that interface functional interface

@FunctionalInterface
interface Something {
    void doSomething();
    //void test();
    // void saySomething();
    public default void saySomething() {
        System.out.println("i am saying something");
    }

    public default void saySomething1() {
        System.out.println("i am saying something");
    }
}

public class LambdaRules {
    public static void main(String[] args) {

        //lambda
        Welcome welcome = null;
        welcome = () -> {
            System.out.println("Hello");
        };
        welcome.sayHello();
        welcome.saySomething();
        welcome.saySomething1();
        Welcome.doSomething();
        Welcome.doSomething1();
        ///////////////////////////////////

        Something something = null;
        something = () -> {
            System.out.println();
        };
        something.doSomething();

    }
}
