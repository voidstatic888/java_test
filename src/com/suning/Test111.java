package com.suning;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Test111
 * @Description TODO
 * @Author 18070888
 * @Date 2018/10/10 11:47
 * @Version 1.0
 **/
public class Test111 {
    public static void main(String[] args) {
        //Task task = new Task();
        //System.out.println(task.getName());
        /*int cap = 85;
        int MAXIMUM_CAPACITY = 1 << 30;
        int n = cap - 1;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 1;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 2;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 4;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 8;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 16;
        System.out.println(Integer.toBinaryString(n));
        System.out.println((n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1);*/
        //long num = Long.parseLong("1492153290365");
        //System.out.println(new Timestamp(num));
        //System.out.println(System.currentTimeMillis());
        //System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        System.out.println(Double.doubleToLongBits(23L));
        System.out.println(Double.doubleToRawLongBits(23L));
    }

    static class Task{
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
