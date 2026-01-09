import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private final BlockingQueue<Order> queue;
    private int orderIdCounter = 1;

    public Producer(BlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                queue.put(new Order(orderIdCounter++)); // Changed to .put()
                Thread.sleep(800); 
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}