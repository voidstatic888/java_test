package threads_learn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class TimeTest {
    public static void main(String[] args) {
        test();
        System.out.println(System.currentTimeMillis());
        Properties prop = new Properties();
        prop.put("11", "12");
        System.out.println(prop.containsKey("111"));
        System.out.println(prop.containsKey("11"));
        Properties oldProp = new Properties();
        oldProp.put("a", 1);
        Properties newProp = new Properties();
        newProp.putAll(oldProp);
        oldProp.put("a", 2);
        System.out.println("oldProp: " + oldProp);
        System.out.println("newProp: " + newProp);
        System.out.println(newProp == change(newProp));
        System.out.println(newProp);
        System.out.println(change(newProp));
    }

    public static Properties change(Properties oldProp){
        Properties newProp = new Properties();
        newProp.putAll(oldProp);
        return newProp;
    }

    public static void test(){
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try{
            date = myDateFormat.parse("2018-11-27 00:00:00");
        }catch (ParseException e){
            e.printStackTrace();
        }
        if (date != null){
            System.out.println(date.getTime()/1000);
        }
        //System.out.println(Calendar.getInstance().getTimeInMillis()/1000);
    }
}
