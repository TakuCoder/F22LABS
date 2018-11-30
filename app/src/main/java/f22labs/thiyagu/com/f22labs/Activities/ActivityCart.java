package f22labs.thiyagu.com.f22labs.Activities;

import android.content.Context;
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

public class ActivityCart extends AppCompatActivity {
    public static RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView1;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapter1;
    public static TextView empty_note;
    public static TextView totalprice;
    EditText coupon;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "HomeScreenActivity";
    DatabaseHelper databaseHelper;
    TextView textview_grandprice;
    TextView delivery_price;
    Button redeem;
    int grandtotal;
    int deliveryCharge=30;
    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        textview_grandprice = findViewById(R.id.textview_grandprice);
        coupon = findViewById(R.id.coupon);
        databaseHelper = DatabaseHelper.getInstance(this);
        redeem = findViewById(R.id.button_redeem);
        empty_note = findViewById(R.id.empty_note);
        delivery_price= findViewById(R.id.delivery_price);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        totalprice = findViewById(R.id.total_price);
        mRecyclerView.setHasFixedSize(true);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int cartsize = databaseHelper.getAllCartSize();
        if (cartsize == 0) {
            empty_note.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);

        } else {
            mAdapter = new CartViewAdapter(databaseHelper.getAllCart(),this);
            mRecyclerView.setAdapter(mAdapter);
            //updatePrice(databaseHelper.TotalPrice());
        }
        grandtotal = databaseHelper.getGrandPrice()+deliveryCharge;

        textview_grandprice.setText("\u20B9 "+grandtotal);

        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String coupon_value= coupon.getText().toString();

                switch (coupon_value)
                {

                    case "F22LABS":

                        if(grandtotal>=400)
                        {
                            grandtotal = grandtotal-((grandtotal*20)/100);
                            textview_grandprice.setText("\u20B9 "+String.valueOf(grandtotal));
                        }
                        else {

                            Toast.makeText(ActivityCart.this,"Sorry you cant avail this offer",Toast.LENGTH_LONG).show();
                        }


                        break;
                    case "FREEDEL":

                        if(grandtotal>=100)
                        {

                            grandtotal = grandtotal-30;
                            textview_grandprice.setText(String.valueOf(grandtotal));
                            delivery_price.setText("\u20B9 "+ 0);

                        }
                        else {

                            Toast.makeText(ActivityCart.this,"Sorry you cant avail this offer",Toast.LENGTH_LONG).show();
                        }

                        break;


                    default:


                        Toast.makeText(ActivityCart.this,"no offer found",Toast.LENGTH_LONG).show();



                        break;

                }

            }
        });


    }
}
