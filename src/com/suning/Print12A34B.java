package com.suning;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName Print12A34B
 * @Description TODO
 * @Author 18070888
 * @Date 2018/9/26 16:16
 * @Version 1.0
 **/
public class Print12A34B {

    public static Print print = new Print();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (print.flagNum){
                    print.printNum();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(print.flagLetter){
                    print.printLetter();
                }
            }
        }).start();

    }

    public static class Print{

        public boolean flagNum = true;

        public boolean flagLetter = true;

        private boolean flag = false;

        private int num = 1;

        private int letter = 65;

        Lock lock = new ReentrantLock();

        private Condition conNum = lock.newCondition();

        private Condition conLetter = lock.newCondition();

        public void printNum(){
            if(num > 52){
                flagNum = false;
                return;
            }
            try{
                lock.lock();
                if(flag){
                    conNum.await();
                }
                System.out.println(num++);
                System.out.println(num++);
                flag = !flag;
                conLetter.signal();
            }catch (InterruptedException e){
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void printLetter(){
            if(letter > 90){
                flagLetter = false;
                return;
            }
            try{
                lock.lock();
                if(!flag){
                    conLetter.await();
                }
                System.out.println((char)letter++);
                flag = !flag;
                conNum.signal();
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
}
