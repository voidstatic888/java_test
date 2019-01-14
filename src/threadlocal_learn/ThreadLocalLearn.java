package threadlocal_learn;

import java.util.Properties;

public class ThreadLocalLearn {

    public static ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        Properties properties = new Properties();
        System.out.println(properties);
        stringThreadLocal.set("h");
        System.out.println(stringThreadLocal.get());
        new Thread(() -> {
            stringThreadLocal.set("y");
            System.out.println(Thread.currentThread().getName() + ": " + stringThreadLocal.get());
        }).start();
        new Thread(() -> {
            stringThreadLocal.set("a");
            System.out.println(Thread.currentThread().getName() + ": " + stringThreadLocal.get());
        }).start();
        new Thread(() -> {
            stringThreadLocal.set("b");
            System.out.println(Thread.currentThread().getName() + ": " + stringThreadLocal.get());
        }).start();
        new Thread(() -> {
            stringThreadLocal.set("c");
            stringThreadLocal.set("ggg");
            System.out.println(Thread.currentThread().getName() + ": " + stringThreadLocal.get());
        }).start();
        new Thread(() -> {
            stringThreadLocal.set("d");
            System.out.println(Thread.currentThread().getName() + ": " + stringThreadLocal.get());
        }).start();
    }
}
