package f22labs.thiyagu.com.f22labs.HomeScreenModule;

import android.content.Context;
import android.util.Log;

import java.util.List;

import f22labs.thiyagu.com.f22labs.App;
import f22labs.thiyagu.com.f22labs.Data.Food;
import f22labs.thiyagu.com.f22labs.Data.FoodPojo;
import f22labs.thiyagu.com.f22labs.Database.DatabaseHelper;
import f22labs.thiyagu.com.f22labs.Network.NetworkClient;
import f22labs.thiyagu.com.f22labs.Network.NetworkInterface;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static android.support.constraint.Constraints.TAG;

public class HomeScreenPresenter implements HomeScreenContract.Presenter {
    DatabaseHelper databaseHelper;

    HomeScreenContract.View mvi;
    Context context;

    HomeScreenPresenter(HomeScreenContract.View mvi, Context context) {
        this.mvi = mvi;
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }


    @Override
    public void getFoodList() {
        getObservable().subscribeWith(getObserver());
    }

    @Override
    public void deleteDb() {
        DatabaseHelper.getInstance(context).deletedata();

    }

    @Override
    public void resume() {


        List<Food> list = DatabaseHelper.getInstance(context).getAllProducts();
        Log.v("sdsad", String.valueOf(list.size()));
        mvi.ShowResumeData(list);
    }


    public Observable<List<Food>> getObservable() {


        return NetworkClient.getRetrofit().create(NetworkInterface.class).getFood().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }


    public DisposableObserver<List<Food>> getObserver() {


        return new DisposableObserver<List<Food>>() {
            @Override
            public void onNext(List<Food> foods) {
                Log.d(TAG, "OnNext" + foods.get(0).getImageUrl());

                for (int i = 0; i < foods.size(); i++) {

                    FoodPojo foodPojo = new FoodPojo(String.valueOf(foods.get(i).getAverageRating()), foods.get(i).getImageUrl(), foods.get(i).getItemName(), String.valueOf(foods.get(i).getItemPrice()));
                    databaseHelper.addProduct(foodPojo);

                }
                mvi.displayFoodItems(foods);
                mvi.stopShimmer();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
                mvi.displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
                mvi.hideProgressBar();
            }
        };

    }
}
