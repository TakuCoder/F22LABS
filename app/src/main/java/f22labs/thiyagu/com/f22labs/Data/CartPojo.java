package f22labs.thiyagu.com.f22labs.Data;



public class CartPojo {

    private int Quantity;
    private String Name;
    private int Price;
    private  int totalprice;

    public CartPojo(int Quantity, String Name, int Price, int totalprice) {


        this.Quantity = Quantity;
        this.Name = Name;
        this.Price = Price;
        this.totalprice = totalprice;


    }

    public String getName() {
        return Name;
    }



    public int getPrice() {
        return Price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getTotalprice() {
        return totalprice;
    }
}
