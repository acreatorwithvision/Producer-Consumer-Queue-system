import java.util.LinkedList;
import java.util.Queue;

public class PizzaQueue {
    private final Queue<Order> queue=new LinkedList<>();
    private final int capacity;

    public PizzaQueue(int capacity){
        this.capacity=capacity;

    }

    //Producer calls this method to add an order to the queue
    public synchronized void addOrder(Order order) throws InterruptedException{
        while(queue.size()==capacity){
            wait(); //Queue is full, wait for space
        }
        queue.add(order);
        System.out.println("Order added: "+order.getId() +"Queue size: "+queue.size());
        notifyAll(); //tell consumer there is a new order
    }

    public synchronized Order takeOrder() throws InterruptedException{
        while(queue.isEmpty()){
            wait(); //Queue is empty, wait for new order
        }
        Order order=queue.poll();
        notifyAll(); //tell producer there is space
        return order;


    }
}
