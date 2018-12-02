package f22labs.thiyagu.com.f22labs.ActivityCartModule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import f22labs.thiyagu.com.f22labs.Adapter.CartViewAdapter;
import f22labs.thiyagu.com.f22labs.Database.DatabaseHelper;
import f22labs.thiyagu.com.f22labs.R;

public class CartActivity extends AppCompatActivity implements CartActivityContract.view {
    public static RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    public static TextView empty_note;
    public static TextView totalprice;
    CartActivityContract.presenter presenter;
    EditText coupon;
    DatabaseHelper databaseHelper;
    TextView grandprice;
    TextView delivery_price;
    Button redeem;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initview();
        initRecyclerView();
        preparecartScreen();
        presenter = new CartActivityPresenter(this, this);
        getInitialGrandPrice();
        presenter.updateDelivery();
        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.RedeemOnClickListener(view, coupon.getText().toString());

            }
        });


    }

    private void getInitialGrandPrice() {
        presenter.getInitialGrandPrice();

    }

    @Override
    public void initview() {
        grandprice = findViewById(R.id.textview_grandprice);
        coupon = findViewById(R.id.textview_coupon);
        redeem = findViewById(R.id.button_redeem);
        empty_note = findViewById(R.id.empty_note);
        delivery_price = findViewById(R.id.textview_delivery_price);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        totalprice = findViewById(R.id.textview_total_price);
        databaseHelper = DatabaseHelper.getInstance(this);
    }

    @Override
    public void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void preparecartScreen() {

        int cartsize = databaseHelper.getAllCartSize();
        if (cartsize == 0) {
            empty_note.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);

        } else {
            mAdapter = new CartViewAdapter(databaseHelper.getAllCart(), this, this);
            mRecyclerView.setAdapter(mAdapter);

        }
    }

    @Override
    public void setInitialGrandPrice(int i) {

        grandprice.setText("\u20B9 " + String.valueOf(i));
    }

    @Override
    public void ShowToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateDeliveryPrice(int i) {
        delivery_price.setText("\u20B9 " + String.valueOf(i));
    }

    @Override
    public void updateGrandpriceTotal(int i) {


        grandprice.setText("\u20B9 " + String.valueOf(i));

    }


}