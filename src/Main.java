public class Main {
    public static void main(String[] args){
        PizzaQueue kitchenCounter=new PizzaQueue(5);

        //Start 1 order taker
        Thread orderTaker=new Thread(new Producer(kitchenCounter));

        Thread chef1=new Thread(new Consumer(kitchenCounter, "Chef Mario"));
        Thread chef2=new Thread(new Consumer(kitchenCounter, "Chef Luigi"));

        orderTaker.start();
        chef1.start();
        chef2.start();
    }
}
