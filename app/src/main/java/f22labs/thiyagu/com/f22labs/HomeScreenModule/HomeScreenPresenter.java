package f22labs.thiyagu.com.f22labs.HomeScreenModule;

import android.util.Log;

import java.util.List;

import f22labs.thiyagu.com.f22labs.App;
import f22labs.thiyagu.com.f22labs.Data.Food;
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


    HomeScreenPresenter(HomeScreenContract.View mvi) {
        this.mvi = mvi;

    }


    @Override
    public void getFoodList() {
        getObservable().subscribeWith(getObserver());
    }

    @Override
    public void deleteDb() {
        databaseHelper =new DatabaseHelper(App.getContext());
        databaseHelper.deletedata();
    }


    public Observable<List<Food>> getObservable() {


        return NetworkClient.getRetrofit().create(NetworkInterface.class).getFood().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }


    public DisposableObserver<List<Food>> getObserver() {


        return new DisposableObserver<List<Food>>() {
            @Override
            public void onNext(List<Food> foods) {
                Log.d(TAG, "OnNext" + foods.get(0).getImageUrl());


                mvi.displayFoodItems(foods);
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
