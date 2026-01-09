import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private final BlockingQueue<Order> queue;
    private final KitchenMetrics metrics;
    private int orderIdCounter = 1;
    private volatile boolean running = true;

    public Producer(BlockingQueue<Order> queue, KitchenMetrics metrics) {
        this.queue = queue;
        this.metrics = metrics;
    }

    public void stop() { running = false; }

    @Override
    public void run() {
        try {
            while (running) {
                queue.put(new Order(orderIdCounter++));
                
                // Track how full the queue is right now
                metrics.updateQueueDepth(queue.size());
                
                Thread.sleep(500);
            }
            System.out.println("Order Taker: Shop is closed.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}