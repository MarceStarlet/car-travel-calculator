package org.marcestarlet.cartravelcalculator.api;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.*;

public class TravelControllerTest {

    private static final int THREAD_POOL_SIZE = 1000;

    @Test
    public void requestTravel() {

        TravelController travelCtrll = new TravelController(Arrays.asList("P","Q","S"));
        assertEquals("S",travelCtrll.requestTravel("A"));
    }

    @Test
    public void requestTravelMulti_threading_ExecutorSubmit_DiffCtrl(){
        ExecutorService execSrv = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        TravelController travelCtrlA = new TravelController(Arrays.asList("P","Q","S"));
        TravelController travelCtrlE = new TravelController(Arrays.asList("A10","A9","A7"));
        TravelController travelCtrlF = new TravelController(Arrays.asList("A9","A10","A12"));

        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            execSrv.execute(() -> {
                String threadName = "Thread = [" + Thread.currentThread().getName() + "]";

                String resultA = travelCtrlA.requestTravel("A");
                String resultE = travelCtrlE.requestTravel("E");
                String resultF = travelCtrlF.requestTravel("F");

                assertEquals("S", resultA);
                assertEquals("A7", resultE);
                assertEquals("A10", resultF);

                System.out.println(threadName + " A -> " + resultA);
                System.out.println(threadName + " E -> " + resultE);
                System.out.println(threadName + " F -> " + resultF);

            });
        }

        try {
            execSrv.shutdown();
            execSrv.awaitTermination(30, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            System.out.println("task interrupted");
        } finally {
            if (!execSrv.isTerminated()){
                System.out.println("cancel non-finished task");
            }
            execSrv.shutdownNow();
            System.out.println("task finished");
        }
    }

    @Test
    public void requestTravelMulti_threading_ExecutorCallable_SameCtrl(){
        ExecutorService execSrv = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        TravelController travelCtrl = new TravelController(Arrays.asList("P","Q","S"));

        List<Callable<String>> callableTravelSrv = Arrays.asList(
                () -> {
                        System.out.println("Thread = [" + Thread.currentThread().getName() + "] A -> ");
                        String result = travelCtrl.requestTravel("A");
                        assertEquals("S", result);
                        return result;
                    },
                () -> {
                        System.out.println("Thread = [" + Thread.currentThread().getName() + "] E -> ");
                        String result = travelCtrl.requestTravel("E");
                        assertNotEquals("A7", result);
                        return result;
                      },
                () -> {
                        System.out.println("Thread = [" + Thread.currentThread().getName() + "] F -> ");
                        String result = travelCtrl.requestTravel("F");
                        assertNotEquals("A10", result);
                        return result;
                      }
        );

        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            try {
                execSrv.invokeAll(callableTravelSrv)
                        .stream()
                        .map(future -> {
                            try {
                                return future.get();
                            } catch (Exception e) {
                                throw new IllegalStateException(e);
                            }
                        })
                        .forEach(System.out::println);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            execSrv.shutdown();
            execSrv.awaitTermination(5, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            System.out.println("task interrupted");
        } finally {
            if (!execSrv.isTerminated()){
                System.out.println("cancel non-finished task");
            }
            execSrv.shutdownNow();
            System.out.println("task finished");
        }

    }
}