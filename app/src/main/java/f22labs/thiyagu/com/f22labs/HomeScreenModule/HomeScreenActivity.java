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

import f22labs.thiyagu.com.f22labs.ActivityCartModule.CartActivity;
import f22labs.thiyagu.com.f22labs.Adapter.FoodRecyclerViewAdapter;
import f22labs.thiyagu.com.f22labs.Data.Food;
import f22labs.thiyagu.com.f22labs.R;


public class HomeScreenActivity extends AppCompatActivity implements HomeScreenContract.View {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Food> list = new ArrayList<>();
    private ShimmerFrameLayout mShimmerViewContainer;
    HomeScreenContract.Presenter presenter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);

        InitView();
        InitPresenter();
        presenter.deleteDb();
        startShimmer();
        getFoodList();


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
    public boolean onOptionsItemSelected(MenuItem item)

    {
        presenter.onOptionsItemSelected(item);


        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();


        presenter.resume();

    }

    @Override
    public void showToast(String s) {
        Toast.makeText(HomeScreenActivity.this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void ShowResumeData(List<Food> list) {
        mAdapter = new FoodRecyclerViewAdapter(list, HomeScreenActivity.this);
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
        presenter = new HomeScreenPresenter(this, this);
    }



    @Override
    public void stopShimmer() {
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
    }

    @Override
    public void displayFoodItems(List<Food> foodResponse) {

        mAdapter = new FoodRecyclerViewAdapter(foodResponse, HomeScreenActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void displayError(String s) {
        showToast(s);
    }

    @Override
    public void ShowFilterPopup() {


        View menuItemView = findViewById(R.id.filter);

        PopupMenu popupMenu = new PopupMenu(HomeScreenActivity.this, menuItemView);
        popupMenu.getMenuInflater()
                .inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                presenter.popupMenuLsitener(menuItem);

                return true;
            }
        });
        popupMenu.show();

    }

    @Override
    public void priceSelection(List<Food> list) {

        mAdapter = new FoodRecyclerViewAdapter(list, HomeScreenActivity.this);
        mRecyclerView.setAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void ratingSelection(List<Food> list) {

        mAdapter = new FoodRecyclerViewAdapter(list, HomeScreenActivity.this);
        mRecyclerView.setAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void movetoCart() {

        Intent intent = new Intent(HomeScreenActivity.this, CartActivity.class);
        startActivity(intent);
    }


    public void getFoodList() {
        presenter.getFoodList();
    }
}