package f22labs.thiyagu.com.f22labs.ActivityCartModule;

import android.content.Context;
import android.view.View;


import f22labs.thiyagu.com.f22labs.Database.DatabaseHelper;

public class CartActivityPresenter implements CartActivityContract.presenter {
    CartActivityContract.view view;
    DatabaseHelper databaseHelper;
    Context context;
    CartActivtymodel cartActivtymodel = new CartActivtymodel();

    public CartActivityPresenter(CartActivityContract.view view, Context context) {

        this.view = view;
        this.context = context;
        databaseHelper = DatabaseHelper.getInstance(context);
    }


    @Override
    public void getInitialGrandPrice() {

        int i = databaseHelper.getGrandPrice();
        updateGrand(i);


    }

    @Override
    public void RedeemOnClickListener(View vieww, String coupon_value) {

        int totalprice = databaseHelper.getGrandPrice();

        switch (coupon_value) {

            case "F22LABS":

                if (totalprice >= 400) {
                    totalprice = totalprice - ((totalprice * 20) / 100);
                    updateGrand(totalprice);
                    view.updateDeliveryPrice(cartActivtymodel.getDeliveryCharge());
                } else {
                    view.ShowToast("Sorry you cant avail this offer");

                }
                break;
            case "FREEDEL":
                if (totalprice >= 100) {
                    totalprice = totalprice - 30;
                    view.updateGrandpriceTotal(totalprice);
                    view.updateDeliveryPrice(0);


                } else {
                    view.ShowToast("Sorry you cant avail this offer");
                }

                break;


            default:
                view.ShowToast("no offer found");


                break;

        }


    }

    @Override
    public void updateGrand(int i) {
        if (i > 1) {

            view.updateGrandpriceTotal(i + cartActivtymodel.getDeliveryCharge());


        } else {

            view.updateGrandpriceTotal(i);

        }
    }

    @Override
    public void updateDelivery() {
        view.updateDeliveryPrice(cartActivtymodel.getDeliveryCharge());
    }
}