public class Producer implements Runnable {
    private final PizzaQueue queue;
    private int orderIdCounter = 1;

    public Producer(PizzaQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Order order = new Order(orderIdCounter++);
                queue.addOrder(order);

                // Simulate time taken to take customer order
                Thread.sleep(800);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
