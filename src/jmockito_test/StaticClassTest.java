package jmockito_test;

public class StaticClassTest {

    public String getStrPS(String str){
        return getStrVS(str);
    }

    private String getStrVS(String str){
        return "private return str " + str;
    }

    public static void getNIO(String str){
        while (true){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getStrNIO(){
        while (true){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
