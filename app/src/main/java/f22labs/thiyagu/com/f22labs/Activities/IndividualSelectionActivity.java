package f22labs.thiyagu.com.f22labs.Activities;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import f22labs.thiyagu.com.f22labs.Data.CartDetails;
import f22labs.thiyagu.com.f22labs.Database.DatabaseHelper;
import f22labs.thiyagu.com.f22labs.R;

public class IndividualSelectionActivity extends AppCompatActivity {
    Button minus;
    Button plus;
    ImageView imageview;
    TextView name, price, rating, quantity;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_selection);
        final String item_name = getIntent().getStringExtra("itemname");
        final String item_price  = getIntent().getStringExtra("price");
        databaseHelper = new DatabaseHelper(this);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        plus=findViewById(R.id.plus);
        imageview = findViewById(R.id.imageview);
        rating = findViewById(R.id.rating);
        minus = findViewById(R.id.minus);

        quantity = findViewById(R.id.quantity);

        int quantity_value = databaseHelper.getCountSize(item_name);

        quantity.setText(String.valueOf(quantity_value));

        name.setText(item_name);
        price.setText("Rs " + item_price);
        rating.setText(getIntent().getStringExtra("rating"));

        Glide.with(this).load(getIntent().getStringExtra("url")).transition(DrawableTransitionOptions.withCrossFade()).into(imageview);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ii = databaseHelper.getCountSize(item_name);


                Log.v("asdasdsd", String.valueOf(ii));


                boolean b = databaseHelper.find(item_name);


                if (b) {
//found

                    ii = ii + 1;
                    databaseHelper.DecreaseQuantity(item_name, ii);

                    quantity.setText(String.valueOf(databaseHelper.getCountSize(item_name))); //refine needed
                    int quantity = databaseHelper.getCountSize(item_name);
                    databaseHelper.UpdatePrice(item_name, Double.parseDouble(item_price), quantity);

                } else {
                    //not found


                    databaseHelper.addToCart(new CartDetails(ii, item_name, String.valueOf(item_price)));

                    ii = ii + 1;
                    databaseHelper.DecreaseQuantity(item_name, ii);

                    quantity.setText(String.valueOf(databaseHelper.getCountSize(item_name))); //refine needed


                    int quantity = databaseHelper.getCountSize(item_name);
                    databaseHelper.UpdatePrice(item_name,Double.valueOf(item_price), quantity);

                }

            }
        });

    }
}
