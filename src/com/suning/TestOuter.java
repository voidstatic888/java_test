package com.suning;

/**
 * @ClassName TestOuter
 * @Description TODO
 * @Author 18070888
 * @Date 2018/9/21 15:46
 * @Version 1.0
 **/
public class TestOuter {
    public static void main(String[] args) {
        outer:
        while (true){
            int i = 0;
            while (true){
                System.out.println("A");
                if(i > 10){
                    break outer;
                }
                i++;
            }
        }
    }
}
