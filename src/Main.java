import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        BlockingQueue<Order> kitchenCounter=new ArrayBlockingQueue<>(5);
        KitchenMetrics metrics=new KitchenMetrics();

        ExecutorService executor=Executors.newFixedThreadPool(3);

        Producer orderTaker=new Producer(kitchenCounter);
        executor.execute(orderTaker);
        executor.execute(new Consumer(kitchenCounter, "Chef Mario",metrics));
        executor.execute(new Consumer(kitchenCounter, "chef Luigi", metrics));

        Thread.sleep(5000);

        System.out.println(">>>> CLOSING TIME <<<<");
        orderTaker.stop();
        executor.shutdown();

        if(executor.awaitTermination(10, TimeUnit.SECONDS)){
            System.out.println("All Chefs finished. Kitchen clean");
        }
        else{
            System.out.println("Forcing Closure");
            executor.shutdownNow();
        }
        metrics.printStats();
    }
}
