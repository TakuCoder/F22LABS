package f22labs.thiyagu.com.f22labs.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Food {
    @SerializedName("average_rating")
    @Expose
    private double averageRating;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_price")
    @Expose
    private double itemPrice;

    public Food(double averageRating, String imageUrl, String itemName, int itemPrice) {
        this.averageRating = averageRating;
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }
}