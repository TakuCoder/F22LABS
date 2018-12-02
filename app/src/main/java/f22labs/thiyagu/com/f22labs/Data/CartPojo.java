package f22labs.thiyagu.com.f22labs.Data;



public class CartPojo {

    private int Quantity;
    private String Name;
    private int Price;
    private  int TotalPrice;
    private String Image;

    public CartPojo(int Quantity, String Name, int Price, int TotalPrice, String image) {


        this.Quantity = Quantity;
        this.Name = Name;
        this.Price = Price;
        this.TotalPrice = TotalPrice;
        this.Image = image;


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

    public int getTotalPrice() {
        return TotalPrice;
    }


    public String getImage() {
        return Image;
    }
}
