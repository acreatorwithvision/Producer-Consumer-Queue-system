import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private final BlockingQueue<Order> queue;
    private final String chefName;
    private final KitchenMetrics metrics;

    public Consumer(BlockingQueue<Order> queue, String chefName, KitchenMetrics metrics) {
        this.queue = queue;
        this.chefName = chefName;
        this.metrics = metrics;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // If this throws InterruptedException, we are shutting down.
                // We let it bubble up to the outer catch block.
                Order order = queue.take(); 
                
                try {
                    // --- PROCESS ORDER ---
                    long waitTime = System.currentTimeMillis() - order.getCreatedAt();
                    
                    // Simulate a random "Kitchen Accident" (1 in 10 chance)
                    if (Math.random() < 0.1) {
                        throw new RuntimeException("Pizza fell on the floor!");
                    }

                    System.out.println(Thread.currentThread().getName() + " cooking #" + order.getId());
                    Thread.sleep(1500); 
                    
                    metrics.recordOrderProcessed(waitTime);
                    
                } catch (RuntimeException e) {
                    // Handle "business logic" errors here so the thread stays alive
                    System.err.println("!!! ERROR: " + chefName + " failed order #" + order.getId() + ": " + e.getMessage());
                    metrics.recordFailure();
                }
            }
        } catch (InterruptedException e) {
            // This is a "lifecycle" exception (shutdown signal)
            Thread.currentThread().interrupt();
        }
    }
}