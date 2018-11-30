package f22labs.thiyagu.com.f22labs.Data;

public class FoodPojo {

    private String averageRating;

    private String imagebase64;

    private String itemName;

    private String itemPrice;


    public FoodPojo(String averageRating, String imagebase64, String itemName, String itemPrice) {
        this.averageRating = averageRating;
        this.imagebase64 = imagebase64;
        this.itemName = itemName;
        this.itemPrice = itemPrice;

    }

    public String getAverageRating() {
        return averageRating;
    }

    public String getImagebase64() {
        return imagebase64;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }



}