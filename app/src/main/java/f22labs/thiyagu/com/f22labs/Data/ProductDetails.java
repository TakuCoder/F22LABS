package f22labs.thiyagu.com.f22labs.Data;

public class ProductDetails {
    private int Id;
    private String Name;
    private String Image;
    private String Feedback;
    private int Price;


    public ProductDetails(int id, String name, String image, String feedback, int price) {
        Id = id;
        Name = name;
        Image = image;
        Feedback = feedback;
        Price = price;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public String getFeedback() {
        return Feedback;
    }

    public int getPrice() {
        return Price;
    }
}
