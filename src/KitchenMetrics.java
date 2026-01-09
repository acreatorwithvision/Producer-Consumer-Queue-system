import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class KitchenMetrics {
    private final AtomicInteger totalOrdersProcessed = new AtomicInteger(0);
    private final AtomicInteger failedOrders = new AtomicInteger(0);
    private final AtomicLong totalWaitTime = new AtomicLong(0);
    private final AtomicInteger peakQueueDepth = new AtomicInteger(0);

    public void recordOrderProcessed(long waitTime) {
        totalOrdersProcessed.incrementAndGet();
        totalWaitTime.addAndGet(waitTime);
    }

    public void recordFailure() {
        failedOrders.incrementAndGet();
    }

    // Thread-safe way to update maximum value
    public void updateQueueDepth(int currentSize) {
        peakQueueDepth.accumulateAndGet(currentSize, Math::max);
    }

    public void printDashboard() {
        int count = totalOrdersProcessed.get();
        int failed = failedOrders.get();
        long avgWait = count == 0 ? 0 : totalWaitTime.get() / count;

        System.out.println("\n================ KITCHEN DASHBOARD ================");
        System.out.printf("| %-25s | %15d |\n", "Total Pizzas Served", count);
        System.out.printf("| %-25s | %15d |\n", "Failed/Burnt Orders", failed);
        System.out.printf("| %-25s | %15d ms |\n", "Avg Prep Wait Time", avgWait);
        System.out.printf("| %-25s | %15d |\n", "Peak Queue Depth", peakQueueDepth.get());
        System.out.println("===================================================\n");
    }
}