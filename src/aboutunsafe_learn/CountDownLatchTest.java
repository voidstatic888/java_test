package aboutunsafe_learn;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {

    private static CountDownLatch countDownLatch = new CountDownLatch(3);

    abstract static class absService implements Runnable{

        private  CountDownLatch countDownLatch;

        public void setCountDownLatch(CountDownLatch latch){
            countDownLatch = latch;
        }

        public void run(){
            try{
                Thread.sleep(5000);
                doService();
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                if (countDownLatch != null){
                    countDownLatch.countDown();
                }
            }
        }

        public void doService(){}
    }

    static class AService extends absService{

        @Override
        public void doService() {
            System.out.println("AService done it!");
        }
    }

    static class BService extends absService{

        @Override
        public void doService() {
            System.out.println("BService done it!");
        }
    }

    static class CService extends absService{

        @Override
        public void doService() {
            System.out.println("CService done it!");
        }
    }

    public static void main(String[] args) {
        AService aService = new AService();
        BService bService = new BService();
        CService cService = new CService();
        aService.setCountDownLatch(countDownLatch);
        bService.setCountDownLatch(countDownLatch);
        cService.setCountDownLatch(countDownLatch);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(aService);
        executorService.execute(bService);
        executorService.execute(cService);
        try{
            countDownLatch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        executorService.shutdown();
        System.out.println("主线程等待所有前置线程执行任务完毕！");


    }
}
