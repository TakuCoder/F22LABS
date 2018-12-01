package f22labs.thiyagu.com.f22labs.IndividualSelectionActivityModule;

import android.content.Context;
import android.util.Log;
import android.view.View;

import f22labs.thiyagu.com.f22labs.Data.CartDetails;
import f22labs.thiyagu.com.f22labs.Database.DatabaseHelper;

public class IndividualSelectionPresenter implements IndividualSelectionContract.presenter {
    IndividualSelectionContract.view view;
    Context context;
DatabaseHelper databaseHelper;

    public IndividualSelectionPresenter(IndividualSelectionContract.view view, Context context)
    {
     this.context = context;
     this.view = view;
databaseHelper =  DatabaseHelper.getInstance(context);
    }


    @Override
    public void getQuantity(String itemName) {

       view.SetQuantity( databaseHelper.getCountSize(itemName));
    }

    @Override
    public void plusOnClickListener(View vieww,String item_name,String item_price) {


        int ii = databaseHelper.getCountSize(item_name);


        Log.v("asdasdsd", String.valueOf(ii));


        boolean b = databaseHelper.find(item_name);


        if (b) {
//found

            ii = ii + 1;
            databaseHelper.DecreaseQuantity(item_name, ii);

            view.SetQuantity(databaseHelper.getCountSize(item_name)); //refine needed



            int quantity = databaseHelper.getCountSize(item_name);
            databaseHelper.UpdatePrice(item_name, Double.parseDouble(item_price), quantity);

        } else {
            //not found


            databaseHelper.addToCart(new CartDetails(ii, item_name, String.valueOf(item_price)));

            ii = ii + 1;
            databaseHelper.DecreaseQuantity(item_name, ii);

            view.SetQuantity(databaseHelper.getCountSize(item_name)); //refine needed


            int quantity = databaseHelper.getCountSize(item_name);
            databaseHelper.UpdatePrice(item_name,Double.valueOf(item_price), quantity);

        }



    }

}
