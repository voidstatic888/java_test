package threads_learn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CompletableFutureTest {

    public static void testPrint(){
        ExecutorService executor = Executors.newFixedThreadPool(24);
        CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < 24; i++){
                System.out.println("i=" + i);
            }
            return "success";
        }, executor);
        //executor.shutdown();
    }

    public static void testCompletableFuture(){
        ExecutorService executor = Executors.newFixedThreadPool(24);

        long start = System.currentTimeMillis();
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(()->{
            try{
                Thread.sleep(5000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return "s";
        }, executor);
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(()->{
            try{
                Thread.sleep(5000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return "u";
        }, executor);
        CompletableFuture<String> completableFuture3 = CompletableFuture.supplyAsync(()->{
            try{
                Thread.sleep(5000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return "c";
        }, executor);
        CompletableFuture<String> completableFuture4 = CompletableFuture.supplyAsync(()->{
            try{
                Thread.sleep(5000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return "c";
        }, executor);
        CompletableFuture<String> completableFuture5 = CompletableFuture.supplyAsync(()->{
            try{
                Thread.sleep(5000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return "e";
        }, executor);
        CompletableFuture<String> completableFuture6 = CompletableFuture.supplyAsync(()->{
            try{
                Thread.sleep(5000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return "s";
        }, executor);
        CompletableFuture<String> completableFuture7 = CompletableFuture.supplyAsync(()->{
            try{
                Thread.sleep(5000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return "s";
        }, executor);
        List<CompletableFuture> list = new ArrayList<>();
        list.add(completableFuture1);
        list.add(completableFuture2);
        list.add(completableFuture3);
        list.add(completableFuture4);
        list.add(completableFuture5);
        list.add(completableFuture6);
        list.add(completableFuture7);
        System.out.println("1：" + (System.currentTimeMillis() - start) + "ms");
        long mapBefore = System.currentTimeMillis();
        list.stream().map(CompletableFuture::join);
        System.out.println("map：" + (System.currentTimeMillis() - mapBefore) + "ms");
        long printBefore = System.currentTimeMillis();
        int i = 1;
        for (CompletableFuture<String> completableFuture: list){
            String value = "null";
            try{
                long now = System.currentTimeMillis();
                value = completableFuture.get();
                System.out.println("第" + i + "个completableFuture耗时：" + (System.currentTimeMillis() - now));
            }catch (ExecutionException e1){
                e1.printStackTrace();
            }catch (InterruptedException e2){
                e2.printStackTrace();
            }
            System.out.println(value);
            i++;
        }
        System.out.println("print：" + (System.currentTimeMillis() - printBefore) + "ms");
        System.out.println("总耗时：" + (System.currentTimeMillis() - start) + "ms");
        executor.shutdown();
        //long start = System.currentTimeMillis();
        /*CompletableFuture<Void> completableFuture = CompletableFuture.allOf(completableFuture1, completableFuture2,
                completableFuture3, completableFuture4, completableFuture5, completableFuture6, completableFuture7);
        try{
            System.out.println(System.currentTimeMillis());
            completableFuture.complete(completableFuture.get());
            System.out.println(System.currentTimeMillis());
            System.out.println(completableFuture1.get());
            System.out.println(System.currentTimeMillis());
            System.out.println(completableFuture2.get());
            System.out.println(System.currentTimeMillis());
            System.out.println(completableFuture3.get());
            System.out.println(System.currentTimeMillis());
            System.out.println(completableFuture4.get());
            System.out.println(System.currentTimeMillis());
            System.out.println(completableFuture5.get());
            System.out.println(System.currentTimeMillis());
            System.out.println(completableFuture6.get());
            System.out.println(System.currentTimeMillis());
            System.out.println(completableFuture7.get());
            System.out.println(System.currentTimeMillis());
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("总耗时：" + (System.currentTimeMillis() - start) + "ms");
        */
    }

    public static void testThenApply(){
        long start = System.currentTimeMillis();
        String result = CompletableFuture.supplyAsync(() -> {
            try{
                Thread.sleep(5000);
            }catch (Exception e){
                e.printStackTrace();
            }
            return "hello";
        }).thenApply(s -> {
            try{
                Thread.sleep(5000);
            }catch (Exception e){
                e.printStackTrace();
            }
            return s + " Apply";
        }).join();
        System.out.println(result);
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
    }

    public static void testThenAccept(){
        CompletableFuture.supplyAsync(() -> {
            try{
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }
            return "hello";
        }).thenAccept(s -> {
            try{
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(s + " Accept");
        }).join();
    }

    public static void testThenRun(){
        CompletableFuture.supplyAsync(()->{
            try{
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }
            return "hello";
        }).thenRunAsync(()->{
            try{
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("hello Run");
        }).join();
    }

    public static void testThenCombine(){
        long start = System.currentTimeMillis();
        String result = CompletableFuture.supplyAsync(() -> {
            try{
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }
            return "hello";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try{
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }
            return " Combine";
        }), (s1, s2) -> {
            try{
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }
            return s1 + s2;
        }).join();
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
        System.out.println(result);
    }

    public static void testThenAcceptBoth(){
        long start = System.currentTimeMillis();
        CompletableFuture.supplyAsync(() -> {
            try{
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }
            return "hello";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            try{
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }
            return " AcceptBoth";
        }), (s1, s2) -> System.out.println(s1 + s2 + System.currentTimeMillis())).join();
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
    }

    public static void testRunAfterBoth(){
        long start = System.currentTimeMillis();
        CompletableFuture.supplyAsync(() -> {
            try{
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }
            return "hello";
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            try{
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }
            return " AcceptBoth";
        }), () -> System.out.println("hello RunAfterBoth")).join();
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
    }

    public static void testApplyToEither(){
        long start = System.currentTimeMillis();
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "applyToEither1";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "applyToEither2";
        }), s -> s).join();
        System.out.println(result);
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
    }

    public static void testAcceptEither(){
        long start = System.currentTimeMillis();
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "applyToEither1";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "applyToEither2";
        }), System.out::println).join();
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
    }

    public static void testRunAfterEither(){
        long start = System.currentTimeMillis();
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "runAfterEither1";
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "runAfterEither2";
        }), () -> System.out.println("runafterEither")).join();
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
    }

    public static void testExceptionally(){
        long start = System.currentTimeMillis();
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1== 1)
                throw new RuntimeException("异步执行出错了!");
            return "common";
        }).exceptionally(e1 -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(e1.getMessage());
            return "exception";
        }).join();
        System.out.println("result:" + result);
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
    }

    public static void testwhenComplete(){
        long start = System.currentTimeMillis();
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1==1){
                throw new RuntimeException("errors happen");
            }
            return "common result";
        }).whenComplete((s, t) -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("result: " + s);
            System.out.println("exceptions: " + t);
        }).exceptionally(throwable -> {
            System.out.println(throwable.getMessage());
            return "errors happen";
        }).join();
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
    }

    public static void testHandle(){
        long start = System.currentTimeMillis();
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1==1){
                throw new RuntimeException("errors happen");
            }
            return "result";
        }).handle((res, throwable) -> {
            if (throwable != null){
                return "error";
            }
            return "common result";
        }).join();
        System.out.println(result);
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
    }

    public static void main(String[] args) {
        //testHandle();
        testPrint();
        System.out.println("over");
    }


}
