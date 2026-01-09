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
                Order order = queue.take(); 
                long waitTime = System.currentTimeMillis() - order.getCreatedAt();
                metrics.recordOrderProcessed(waitTime);
                
                System.out.println(chefName + " cooking #" + order.getId());
                Thread.sleep(1500); 
                
                if (order.getId() % 5 == 0) metrics.printStats();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}