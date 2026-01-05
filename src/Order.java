public class Order{
    private final int id;
    private final long createdAt;

    public Order(int id){
        this.id=id;
        this.createdAt=System.currentTimeMillis();

    }

    public int getId(){return id;}
    public long getCreatedAt(){return createdAt;}


}