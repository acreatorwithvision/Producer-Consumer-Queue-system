import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args){
        BlockingQueue<Order> kitchenCounter=new ArrayBlockingQueue<>(5);
        KitchenMetrics metrics=new KitchenMetrics();

        //Start 1 order taker
        new Thread(new Producer(kitchenCounter)).start();
        new Thread(new Consumer(kitchenCounter, "Chef Mario", metrics)).start();
        new Thread(new Consumer(kitchenCounter, "Chef Luigi",metrics)).start();

    }
}
