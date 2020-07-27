package com.ibm.java.fp.lambda.deep;

@FunctionalInterface
interface Handler {
    void connect();
}

//Function as parameter with input

@FunctionalInterface
interface HTTPGetHandler {
    void get(Object response);
}

class Database {

    //arg is Handler type
    public void connectDb(Handler handler) {
        //call the connect method
        handler.connect();
    }
}

class HttpServer {
    //httpGetHandler = new HttpGetHandlerImpl();
    public void fetch(HTTPGetHandler httpGetHandler) {
        //call get Method of HttpGetHandler
        String fakeResponse[] = {"Hello", "Hai", "Welcome"};
        httpGetHandler.get(fakeResponse);
    }
}

class HandlerImpl implements Handler {
    @Override
    public void connect() {
        System.out.println("Database Handler ");
    }
}

public class FunctionAsparameter {
    public static void main(String[] args) {
        Database database = null;
        database = new Database();
        //pass Handler reference to connectDb
        //old synatx
        database.connectDb(new HandlerImpl());
        //annonous
        database.connectDb(new Handler() {
            @Override
            public void connect() {
                System.out.println("Database Handler : anonmous");
            }
        });
        //passing lambada
        Handler handler = () -> System.out.println("Handler separate variable");
        database.connectDb(handler);
        //inline lambda
        database.connectDb(() -> System.out.println("Handler inline lambda"));

        ////////////////////////////////////////////////////////////////////////////////////////////////
        HttpServer httpServer = new HttpServer();
        httpServer.fetch(result -> {
            System.out.println(result.toString());
        });
        httpServer.fetch(result -> System.out.println(result.toString()));
    }
}
