package com.ibm.java.fp.lambda.deep;

@FunctionalInterface
interface MyFunction {
    void sayHello();
}

//args and parameters
@FunctionalInterface
interface Details {
    //value1 and value2 are args
    void setDetails(String value1, String value2);
}

@FunctionalInterface
interface Single {
    void setName(String name);
}

//return values
@FunctionalInterface
interface Stock {
    int getStock();
}

//more than one parameter
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}

@FunctionalInterface
interface MyFunction2 {
    String getValue(String value);
}

public class DeepLambdaRules {
    public static void main(String[] args) {
        //
        MyFunction function = null;
        //Version  1:
        function = () -> {
            //function body
            System.out.println("Hello ");
        };
        function.sayHello();
        //if lambda has only one line of body : remove {}
        function = () -> System.out.println("Hello ");
        function.sayHello();
        ////////////////////////////////////////////////////////////////////////////////////
        Details details = null;
        //args
        details = (String value1, String value2) -> {
            System.out.println(value1 + " " + value2);
        };
        //parameters
        details.setDetails("Hello", "Vertx");
        //if lambda has only one line of body : remove {}
        details = (String value1, String value2) -> System.out.println(value1 + " " + value2);
        details.setDetails("Hello", "Vertx");
        //You can remove args data type as well
        //Type is verified automatically : Type Inference
        details = (value1, value2) -> System.out.println(value1 + " " + value2);
        details.setDetails("Hello", "Vertx");
        // details.setDetails(12, "Vertx");
        /////////////////////////////////////////////////////////////////////////////////
        //Single parameter
        Single single = null;
        single = (String name) -> System.out.println(name);
        single.setName("Subramanian");
        //type inference
        single = (name) -> System.out.println(name);
        single.setName("Subramanian");
        //if function has single arg, remove ()
        single = name -> System.out.println(name);
        single.setName("Subramanian");
        ///////////////////////////////////////////////////////////////////////////
        //return value
        Stock stock = null;

        //with return statement
        stock = () -> {
            return 100;
        };
        System.out.println(stock.getStock());
        //if function body having only return statement. we can remove {} and return statement
        stock = () -> 100;
        System.out.println(stock.getStock());
        ////////////////////////////////////////////////////////////////////////////////////
        Calculator calculator = null;
        calculator = (a, b) -> {
            int result = a + b;
            return result;
        };
        System.out.println(calculator.calculate(10, 10));
        // args and return
        calculator = (a, b) -> a + b;
        System.out.println(calculator.calculate(10, 10));
        //////////////////////////////////////////////////////////////////////////////
        MyFunction2 myFunction2 = null;
        myFunction2 = (value) -> {
            return value;
        };
        System.out.println(myFunction2.getValue("SOmething"));
        myFunction2 = value -> value;
        System.out.println(myFunction2.getValue("SOmething"));


    }
}
