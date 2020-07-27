package com.ibm.java.fp.lambda.deep;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

//interface MyInterface{
//    String get();
//}
//    MyInterface myInterface  =()->"Hello";
//        System.out.println(myInterface.get());

public class BuiltinFunctionalInterfaces {
    public static void main(String[] args) {
        //Supplier
        Supplier<String> supplier = null;
        //old sytle
        supplier = new Supplier<String>() {
            @Override
            public String get() {
                return "HEllo";
            }
        };
        System.out.println(supplier.get());
        supplier = () -> "hello";
        System.out.println(supplier.get());

        //Consumer<String> consumer = value -> System.out.println(value);
        Consumer<String> consumer = System.out::println;
        consumer.accept("Hello,Consumer");

        //Existing java collection apis and functional interfaces
        //consumer inside java apis : collections : iterators : forEach
        List<String> names = Arrays.asList("Subramanian", "Geetha", "Divya Sree");
        names.forEach(consumer);
        names.forEach(name -> System.out.println(name));
        names.forEach(System.out::println);


    }
}
