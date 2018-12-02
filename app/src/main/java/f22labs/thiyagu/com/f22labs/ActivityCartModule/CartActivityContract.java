package f22labs.thiyagu.com.f22labs.ActivityCartModule;

import android.view.View;

public class CartActivityContract {


    public interface view {

        void updateGrandpriceTotal(int i);

        void initview();

        void initRecyclerView();

        void preparecartScreen();

        void setInitialGrandPrice(int i);

        void ShowToast(String s);

        void updateDeliveryPrice(int i);
    }

    public interface presenter {

        void getInitialGrandPrice();

        void RedeemOnClickListener(View view, String s);

        void updateGrand(int i);

        void updateDelivery();

    }

    public interface model {
        int getDeliveryCharge();

    }


}