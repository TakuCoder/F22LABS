package f22labs.thiyagu.com.f22labs.HomeScreenModule;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import f22labs.thiyagu.com.f22labs.Adapter.FoodRecyclerViewAdapter;
import f22labs.thiyagu.com.f22labs.App;
import f22labs.thiyagu.com.f22labs.Data.Food;
import f22labs.thiyagu.com.f22labs.Data.FoodPojo;
import f22labs.thiyagu.com.f22labs.Database.DatabaseHelper;
import f22labs.thiyagu.com.f22labs.Network.NetworkClient;
import f22labs.thiyagu.com.f22labs.Network.NetworkInterface;
import f22labs.thiyagu.com.f22labs.R;
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

    @Override
    public void onOptionsItemSelected(MenuItem item) {


                if(item.getItemId()==R.id.filter)

        {
            mvi.ShowFilterPopup();
           // mvi.showFilerPopup();

        }
        else if(item.getItemId()==R.id.cart)
        {


            mvi.movetoCart();
        }


    }

    @Override
    public void popupMenuLsitener(MenuItem item) {


        switch (item.getItemId())
        {

            case (R.id.price):
mvi.priceSelection(DatabaseHelper.getInstance(context).sortByPrice());
//                list= databaseHelper.sortByPrice();
//                mAdapter = new FoodRecyclerViewAdapter(list,HomeScreenActivity.this);
//                mRecyclerView.setAdapter(null);
//                mRecyclerView.setAdapter(mAdapter);
                break;


            case (R.id.rating):
//                list= databaseHelper.sortByRating();
//                mAdapter = new FoodRecyclerViewAdapter(list,HomeScreenActivity.this);
//                mRecyclerView.setAdapter(null);
//                mRecyclerView.setAdapter(mAdapter);
mvi.ratingSelection(DatabaseHelper.getInstance(context).sortByRating());

                break;



        }

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
