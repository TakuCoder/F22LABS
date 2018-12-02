package f22labs.thiyagu.com.f22labs.ActivityDescriptionModule;

import android.view.View;

public class DescriptionContract {

    interface view {
        void initview();

        void SetQuantity(int s);

    }

    interface presenter {
        void getQuantity(String itemname);

        void plusOnClickListener(View view, String item_name, String item_price, String image);
    }

}
