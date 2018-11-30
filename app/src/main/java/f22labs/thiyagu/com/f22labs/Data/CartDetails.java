package f22labs.thiyagu.com.f22labs.Data;

public class CartDetails {

    private int Quantity;
    private String Name;
    private String Price;


    public CartDetails(int Quantity, String Name, String Price) {


        this.Quantity = Quantity;
        this.Name = Name;
        this.Price = Price;


    }



    public String getName() {
        return Name;
    }



    public String getPrice() {
        return Price;
    }

    public int getQuantity() {
        return Quantity;
    }
}
