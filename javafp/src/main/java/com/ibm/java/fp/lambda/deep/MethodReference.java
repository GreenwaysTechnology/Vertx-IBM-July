package com.ibm.java.fp.lambda.deep;


@FunctionalInterface
interface Printer {
    void print(String value);
}


class MicroTaskRunner {
    public static void startMicroTaskStatic() {
        for (int i = 0; i < 5; i++) {
            System.out.println("MicroTaskRunner Static " + Thread.currentThread().getName());
        }
    }

    public void startMicroTask() {
        for (int i = 0; i < 5; i++) {
            System.out.println("MicroTask Runner class " + Thread.currentThread().getName());
        }
    }
}

class Loop {

    //thread logic :runnable logic
    private void startMicroTask() {
        for (int i = 0; i < 5; i++) {
            System.out.println("MicroTask " + Thread.currentThread().getName());
        }
    }

    public void start() {
        //Create thread
        Thread thread = null;
        thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        });
        thread.start();
        //isloate thread runnable logic into a separte method and call that method inside lambda
        thread = new Thread(() -> this.startMicroTask());
        thread.start();
        //isloate thread runnable logic into a separte method and call using method Reference ::
        thread = new Thread(this::startMicroTask);
        thread.start();
        //Runnable target outside thisclass
        MicroTaskRunner taskRunner = new MicroTaskRunner();
        thread = new Thread(() -> taskRunner.startMicroTask());
        thread.start();
        thread = new Thread(() -> new MicroTaskRunner().startMicroTask());
        thread.start();
        //method reference outside class
        thread = new Thread(new MicroTaskRunner()::startMicroTask);
        thread.start();
        thread = new Thread(taskRunner::startMicroTask);
        thread.start();
        //static
        thread = new Thread(MicroTaskRunner::startMicroTaskStatic);
        thread.start();


    }
}

public class MethodReference {
    public static void main(String[] args) {
        Loop loop = new Loop();
        loop.start();
        //Method reference for Printer interface
        Printer myprinter = null;
        myprinter = value -> System.out.println(value);
        myprinter.print("Subramanian");
        //method reference
        myprinter = System.out::println;
        myprinter.print("Subramanian");

        HttpServer httpServer = new HttpServer();
        //3.1 : write lambda separatly and pass the reference
        HTTPGetHandler httpGetHandler = System.out::println;
        httpServer.fetch(httpGetHandler);
        //3.2: inline lambda
        httpServer.fetch(System.out::println);


    }
}
