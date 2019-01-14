package com.suning;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName PrintABA
 * @Description TODO
 * @Author 18070888
 * @Date 2018/9/20 16:58
 * @Version 1.0
 **/
public class PrintABA {

    private ReentrantLock lock = new ReentrantLock();

    private boolean flag = true;

    public void printABA(){
        new ThreadA(lock).start();
        new ThreadB(lock).start();
    }

    public static void main(String[] args) {
        PrintABA printABA = new PrintABA();
        printABA.printABA();
    }

    public class ThreadA extends Thread{

        private ReentrantLock lock;

        public ThreadA(ReentrantLock lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            int i = 20;
            while (true){
                //System.out.println("ARUN");
                lock.lock();
                try {
                    //System.out.println("ARUN");
                    if (i < 0) {
                        break;
                    }
                    if (flag) {
                        //System.out.println("A:" + i);
                        System.out.println("A" + i);
                        flag = !flag;
                        i--;
                    }
                }finally {
                    lock.unlock();
                    try{
                        Thread.sleep(1);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
                /*try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        }


    public class ThreadB extends Thread{

        private ReentrantLock lock;

        public ThreadB(ReentrantLock lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            int i = 20;
            while (true){
                //System.out.println("BRUN");
                lock.lock();
                try {
                    //System.out.println("BRUN");
                    if (i < 0) {
                        break;
                    }
                    if (!flag) {
                        //System.out.println("B:" + i);
                        System.out.println("B" + i);
                        flag = !flag;
                        i--;
                    }
                }finally {
                        lock.unlock();
                        try{
                            Thread.sleep(1);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
                /*try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

        }
    }
}
