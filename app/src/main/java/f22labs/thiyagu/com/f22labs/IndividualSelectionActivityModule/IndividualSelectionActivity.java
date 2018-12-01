package f22labs.thiyagu.com.f22labs.IndividualSelectionActivityModule;

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

public class IndividualSelectionActivity extends AppCompatActivity implements IndividualSelectionContract.view {
    Button minus;
    IndividualSelectionContract.presenter presenter;
    Button plus;
    ImageView imageview;
    TextView name, price, rating, textView_quantity;
    private String item_name;
    private String item_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_selection);
        item_name = getIntent().getStringExtra("itemname");
        item_price = getIntent().getStringExtra("price");
        initview();
        presenter = new IndividualSelectionPresenter(this, this);
        getQuantity(item_name);
        name.setText(item_name);
        price.setText("Rs " + item_price);
        rating.setText(getIntent().getStringExtra("rating"));

        Glide.with(this).load(getIntent().getStringExtra("url")).transition(DrawableTransitionOptions.withCrossFade()).into(imageview);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                presenter.plusOnClickListener(view, item_name, item_price);


            }
        });

    }

    private void getQuantity(String itemname) {

        presenter.getQuantity(itemname);
    }

    @Override
    public void initview() {
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        plus = findViewById(R.id.plus);
        imageview = findViewById(R.id.imageview);
        rating = findViewById(R.id.rating);
        minus = findViewById(R.id.minus);
        textView_quantity = findViewById(R.id.quantity);

    }

    @Override
    public void SetQuantity(int s) {
        textView_quantity.setText(String.valueOf(s));
    }
}
