package aboutunsafe_learn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {

    private static Integer count = 0;

    private static AtomicInteger count_ato = new AtomicInteger(0);

    private static volatile Integer count_volatile = 0;

    public static void addNum(){
        //count_volatile ++;
        //count ++;
        count_ato.addAndGet(1);
    }


    public static void main(String[] args) {
        int num_threads = 20;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < num_threads; i++){
            threads.add(new Thread(() -> {
                for (int j = 0; j < 1000; j++){
                    addNum();
                }
            }));
        }
        for (Thread thread: threads){
            thread.start();
        }
        if (Thread.activeCount() > 1){
            try{
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        //System.out.println("allCountresult: " + count);
        System.out.println("allCountresult: " + count_ato);
        //System.out.println("allCountresult: " + count_volatile);
    }
}
