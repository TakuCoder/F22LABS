package f22labs.thiyagu.com.f22labs.HomeScreenModule;

import android.view.View;

import java.util.List;

import f22labs.thiyagu.com.f22labs.Data.Food;

public class HomeScreenContract {


    interface View {


        void showToast(String s);

        void ShowResumeData(List<Food> list);

        void showProgressBar();

        void hideProgressBar();

        void stopShimmer();

        void displayFoodItems(List<Food> foodResponse);

        void displayError(String s);
    }

    interface Presenter {
        void getFoodList();

        void deleteDb();

        void resume();

       // void setOnMenuItemClickListener(android.view.View view);

    }

}
