import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class KitchenMetrics {
    private final AtomicInteger totalOrdersProcessed=new AtomicInteger(0);
    private final AtomicLong totalWaitTime=new AtomicLong(0);

    public void recordOrderProcessed(long waitTime){
        totalOrdersProcessed.incrementAndGet();
        totalWaitTime.addAndGet(waitTime);
    }

    public void printStats(){
        int count=totalOrdersProcessed.get();
        long avgWait=count==0?0 : totalWaitTime.get()/count;
        System.out.println("--- KITCHEN STATS ---");
        System.out.println("Total Pizzas Served: "+count);
        System.out.println("Average Prep Wait Time: "+avgWait+"ms");
        System.out.println("---------------------");
    }
}
