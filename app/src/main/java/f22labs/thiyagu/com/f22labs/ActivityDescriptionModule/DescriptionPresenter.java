package f22labs.thiyagu.com.f22labs.ActivityDescriptionModule;

import android.content.Context;
import android.view.View;

import f22labs.thiyagu.com.f22labs.Data.CartDetails;
import f22labs.thiyagu.com.f22labs.Database.DatabaseHelper;

public class DescriptionPresenter implements DescriptionContract.presenter {
    DescriptionContract.view view;
    Context context;
    DatabaseHelper databaseHelper;

    public DescriptionPresenter(DescriptionContract.view view, Context context) {
        this.context = context;
        this.view = view;
        databaseHelper = DatabaseHelper.getInstance(context);
    }


    @Override
    public void getQuantity(String itemName) {

        view.SetQuantity(databaseHelper.getCountSize(itemName));
    }

    @Override
    public void plusOnClickListener(View vieww, String item_name, String item_price, String image) {


        int ii = databaseHelper.getCountSize(item_name);


        boolean b = databaseHelper.find(item_name);


        if (b) {
            //found

            ii = ii + 1;
            databaseHelper.UpdateQuantity(item_name, ii);

            view.SetQuantity(databaseHelper.getCountSize(item_name)); //refine needed


            int quantity = databaseHelper.getCountSize(item_name);
            databaseHelper.UpdatePrice(item_name, Double.parseDouble(item_price), quantity);

        } else {
            //not found


            databaseHelper.addToCart(new CartDetails(ii, item_name, String.valueOf(item_price), image));

            ii = ii + 1;
            databaseHelper.UpdateQuantity(item_name, ii);

            view.SetQuantity(databaseHelper.getCountSize(item_name)); //refine needed


            int quantity = databaseHelper.getCountSize(item_name);
            databaseHelper.UpdatePrice(item_name, Double.valueOf(item_price), quantity);

        }


    }

}