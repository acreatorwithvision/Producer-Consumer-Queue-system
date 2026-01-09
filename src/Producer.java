import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private final BlockingQueue<Order> queue;
    private int orderIdCounter = 1;
    private volatile boolean running=true;

    public Producer(BlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public void stop(){ running = false;}

    @Override
    public void run() {
        try {
            while (running) {
                queue.put(new Order(orderIdCounter++)); // Changed to .put()
                Thread.sleep(500); 
            }
            System.out.println("Order Taker: Shop is closed, no more orders.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}