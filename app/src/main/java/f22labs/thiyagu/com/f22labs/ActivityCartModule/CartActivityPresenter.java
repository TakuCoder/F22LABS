package f22labs.thiyagu.com.f22labs.ActivityCartModule;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import f22labs.thiyagu.com.f22labs.Database.DatabaseHelper;

public class CartActivityPresenter implements CartActivityContract.presenter{
    CartActivityContract.view view;
    DatabaseHelper databaseHelper;
Context context ;
    public CartActivityPresenter(CartActivityContract.view view, Context context)
    {

this.view = view;
this.context = context;
databaseHelper = DatabaseHelper.getInstance(context);
    }


    @Override
    public void  getInitialGrandPrice() {
        CartActivtymodel cartActivtymodel = new CartActivtymodel();
        int i=databaseHelper.getGrandPrice();

        if(i>1)
        {

            view.updateGrandpriceTotal(i+cartActivtymodel.getDeliveryCharge());


        }
        else
        {

            view.updateGrandpriceTotal(i);

        }
    }

    @Override
    public void RedeemOnClickListener(View vieww,String coupon_value,int grandtotal) {
        switch (coupon_value)
        {

            case "F22LABS":

                if(grandtotal>=400)
                {
                    grandtotal = grandtotal-((grandtotal*20)/100);
                   // textview_grandprice.setText("\u20B9 "+String.valueOf(grandtotal));
                }
                else {


                    view.ShowToast("Sorry you cant avail this offer");

                }


                break;
            case "FREEDEL":

                if(grandtotal>=100)
                {

                    grandtotal = grandtotal-30;
                    //textview_grandprice.setText(String.valueOf(grandtotal));
                   // delivery_price.setText("\u20B9 "+ 0);

                }
                else {

                   // Toast.makeText(CartActivity.this,"Sorry you cant avail this offer",Toast.LENGTH_LONG).show();

                    view.ShowToast("Sorry you cant avail this offer");
                }

                break;


            default:



                view.ShowToast("no offer found");


                break;

        }


    }
}
