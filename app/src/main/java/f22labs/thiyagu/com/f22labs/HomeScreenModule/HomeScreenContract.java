package f22labs.thiyagu.com.f22labs.HomeScreenModule;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import f22labs.thiyagu.com.f22labs.Data.Food;

public class HomeScreenContract {


    interface View {


        void showToast(String s);

        void ShowResumeData(List<Food> list);


        void stopShimmer();

        void displayFoodItems(List<Food> foodResponse);

        void displayError(String s);

        void ShowFilterPopup();

        void priceSelection(List<Food> foods);
        void ratingSelection(List<Food> foods);
        void movetoCart();
    }

    interface Presenter {
        void getFoodList();

        void deleteDb();

        void resume();


        void onOptionsItemSelected(MenuItem item);
       // void setOnMenuItemClickListener(android.view.View view);
        void popupMenuLsitener(MenuItem item);

    }

}
