package com.ibm.java.fp.lambda.deep;

interface Resolve {
    void resolve(Object response);
}

interface Reject {
    void reject(Object error);
}

class Login {
    public void auth(Resolve res, Reject rej) {
        String name = "admin";
        String pass = "admin";
        String success = "Login Success";
        String failure = "Login Failed";
        //biz
        if (name.equals("admin") && pass.equals("admin")) {
            res.resolve(success);
        } else {
            rej.reject(failure);
        }
    }
}

public class LoginDemo {
    public static void main(String[] args) {
        Login login = new Login();

        login.auth(response -> {
            System.out.println(response);
        }, error -> {
            System.out.println(error);
        });
        //Function as parameter to constructor
        Runnable target;
        Thread thread = new Thread(()-> System.out.println(Thread.currentThread().getName()));
        thread.start();
    }
}
