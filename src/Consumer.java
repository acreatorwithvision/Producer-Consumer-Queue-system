public class Consumer implements Runnable{
    private final PizzaQueue queue;
    private final String chefName;


    public Consumer(PizzaQueue queue, String chefName){
        this.queue=queue;
        this.chefName=chefName;
    }

    @Override
    public void run(){
        try{
            while(true){
                Order order=queue.takeOrder();

                //Metric calculation
                long waitTime=System.currentTimeMillis()-order.getCreatedAt();

                System.out.println(chefName+" started cooking Order #"+order.getId()+" (Wait time: "+waitTime+"ms)");
                
                //Simulate cooking time
                Thread.sleep(2000);
                System.out.println(chefName+" finished Order #"+order.getId());
            }
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
