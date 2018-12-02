package f22labs.thiyagu.com.f22labs.Data;

public class FoodPojo {

    private String averageRating;

    private String image;

    private String itemName;

    private String itemPrice;


    public FoodPojo(String averageRating, String image, String itemName, String itemPrice) {
        this.averageRating = averageRating;
        this.image = image;
        this.itemName = itemName;
        this.itemPrice = itemPrice;

    }

    public String getAverageRating() {
        return averageRating;
    }

    public String getImage() {
        return image;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }



}