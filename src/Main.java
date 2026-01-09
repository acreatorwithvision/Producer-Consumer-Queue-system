import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Order> queue = new ArrayBlockingQueue<>(10);
        KitchenMetrics metrics = new KitchenMetrics();

        // Custom Factory to name threads (e.g., "PizzaWorker-1")
        ThreadFactory namedFactory = new ThreadFactory() {
            private final AtomicInteger count = new AtomicInteger(1);
            public Thread newThread(Runnable r) {
                return new Thread(r, "PizzaWorker-" + count.getAndIncrement());
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(3, namedFactory);

        // Update Producer constructor to accept metrics
        Producer orderTaker = new Producer(queue, metrics);
        
        executor.execute(orderTaker);
        executor.execute(new Consumer(queue, "Chef Mario", metrics));
        executor.execute(new Consumer(queue, "Chef Luigi", metrics));

        // Run for 8 seconds to generate enough data
        Thread.sleep(8000);

        System.out.println(">>> CLOSING TIME! <<<");
        orderTaker.stop();
        executor.shutdown(); 
        
        if (executor.awaitTermination(5, TimeUnit.SECONDS)) {
            System.out.println("Kitchen clean.");
        } else {
            executor.shutdownNow();
        }

        metrics.printDashboard();
    }
}