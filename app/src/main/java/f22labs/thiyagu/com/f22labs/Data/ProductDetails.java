package f22labs.thiyagu.com.f22labs.Data;

public class ProductDetails {
    private int Id;
    private String Name;
    private String Image;
    private String AverageRating;
    private int Price;


    public ProductDetails(int id, String name, String image, String averageRating, int price) {
        Id = id;
        Name = name;
        Image = image;
        AverageRating = averageRating;
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

    public String getAverageRating() {
        return AverageRating;
    }

    public int getPrice() {
        return Price;
    }
}
