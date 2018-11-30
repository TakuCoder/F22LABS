package f22labs.thiyagu.com.f22labs.HomeScreenModule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;


import java.util.ArrayList;
import java.util.List;
import f22labs.thiyagu.com.f22labs.Activities.ActivityCart;
import f22labs.thiyagu.com.f22labs.Adapter.FoodRecyclerViewAdapter;
import f22labs.thiyagu.com.f22labs.Data.Food;
import f22labs.thiyagu.com.f22labs.Database.DatabaseHelper;
import f22labs.thiyagu.com.f22labs.R;



public class HomeScreenActivity extends AppCompatActivity implements HomeScreenContract.View{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Food> list = new ArrayList<>();
    private ShimmerFrameLayout mShimmerViewContainer;
    HomeScreenContract.Presenter presenter;
    DatabaseHelper databaseHelper ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);









        InitView();
        InitPresenter();
        presenter.deleteDb();
        startShimmer();
        getFoodList();

//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.addInterceptor(logging);
//        Retrofit retrofit = new Retrofit.Builder().client(httpClient.build()).addConverterFactory(GsonConverterFactory.create()).baseUrl(SERVER_URL).build();
//        NetworkInterface apiService = retrofit.create(NetworkInterface.class);



//
//        Call<List<Food>> call = apiService.getFood();
//        call.enqueue(new Callback<List<Food>>() {
//            @Override
//            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
//
//                list = response.body();
//                mAdapter = new FoodRecyclerViewAdapter(list,HomeScreenActivity.this);
//                mRecyclerView.setAdapter(mAdapter);
//
//
//                for(int i=0;i<list.size();i++)
//                {
//
//
//                    FoodPojo foodPojo = new FoodPojo(String.valueOf(list.get(i).getAverageRating()),list.get(i).getImageUrl(),list.get(i).getItemName(),String.valueOf(list.get(i).getItemPrice()));
////                    databaseHelper.addProduct(foodPojo);
//
//                }
//

//
//
//
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Food>> call, Throwable t) {
//
//                if (t instanceof IOException) {
//                    Toast.makeText(HomeScreenActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
//                    // logging probably not necessary
//                } else {
//                    Toast.makeText(HomeScreenActivity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
//                    // todo log to some central bug tracking service
//                }
//
//
//            }
//        });

    }

    private void startShimmer() {
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.filter)

        {
            View menuItemView = findViewById(R.id.filter);

            PopupMenu popupMenu = new PopupMenu(HomeScreenActivity.this,menuItemView);
            popupMenu.getMenuInflater()
                    .inflate(R.menu.popup_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {



                    switch (menuItem.getItemId())
                    {

                        case (R.id.price):

                           list= databaseHelper.sortByPrice();
                            mAdapter = new FoodRecyclerViewAdapter(list,HomeScreenActivity.this);
                            mRecyclerView.setAdapter(null);
                            mRecyclerView.setAdapter(mAdapter);
                            break;


                        case (R.id.rating):
                            list= databaseHelper.sortByRating();
                            mAdapter = new FoodRecyclerViewAdapter(list,HomeScreenActivity.this);
                            mRecyclerView.setAdapter(null);
                            mRecyclerView.setAdapter(mAdapter);

                            break;

                    }

                    return true;
                }
            });
            popupMenu.show();
        }
else if(item.getItemId()==R.id.cart)
        {


            Intent intent = new Intent(HomeScreenActivity.this,ActivityCart.class);
            startActivity(intent);
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

//       // list = presenter.resume();
////        list = databaseHelper.getAllProducts();
//      mAdapter = new FoodRecyclerViewAdapter(list,HomeScreenActivity.this);
//       mRecyclerView.setAdapter(mAdapter);
//       stopShimmer();

        presenter.resume();

    }

    @Override
    public void showToast(String s) {
        Toast.makeText(HomeScreenActivity.this,s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void ShowResumeData(List<Food> list) {
        mAdapter = new FoodRecyclerViewAdapter(list,HomeScreenActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        stopShimmer();
    }


    public void InitView() {

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
    }



    public void InitPresenter() {
        presenter = new HomeScreenPresenter(this,this);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void stopShimmer() {
                        mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
    }

    @Override
    public void displayFoodItems(List<Food> foodResponse) {

                mAdapter = new FoodRecyclerViewAdapter(foodResponse,HomeScreenActivity.this);
                mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void displayError(String s) {
        showToast(s);
    }
    public void getFoodList()
    {
       presenter.getFoodList();
    }
}