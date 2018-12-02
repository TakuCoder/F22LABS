package f22labs.thiyagu.com.f22labs.Data;

public class CartDetails {

    private int Quantity;
    private String Name;
    private String Price;
    private String Image;


    public CartDetails(int Quantity, String Name, String Price, String image) {

        this.Image = image;
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

    public String getImage() {
        return Image;
    }

    public int getQuantity() {
        return Quantity;
    }
}
