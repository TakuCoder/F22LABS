package f22labs.thiyagu.com.f22labs.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;


import java.util.List;

import f22labs.thiyagu.com.f22labs.IndividualSelectionActivityModule.IndividualSelectionActivity;
import f22labs.thiyagu.com.f22labs.Data.CartDetails;
import f22labs.thiyagu.com.f22labs.Data.Food;
import f22labs.thiyagu.com.f22labs.Database.DatabaseHelper;
import f22labs.thiyagu.com.f22labs.R;

public class FoodRecyclerViewAdapter extends RecyclerView.Adapter<FoodRecyclerViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "FoodRecyclerViewAdapter";
    private List<Food> mDataset;
    private Context context;
    DatabaseHelper databaseHelper;


    public FoodRecyclerViewAdapter(List<Food> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.name.setText(mDataset.get(position).getItemName());
        holder.price.setText("Rs " + String.valueOf(mDataset.get(position).getItemPrice()));
        holder.rating.setText(String.valueOf(mDataset.get(position).getAverageRating()));
        holder.quantity.setText(String.valueOf(databaseHelper.getCountSize(mDataset.get(position).getItemName()))); //refine needed
        Glide.with(context).load(mDataset.get(position).getImageUrl())
                //.placeholder(R.drawable.piwo_48)
                //.transform(new CircleTransform(context))

                .transition(DrawableTransitionOptions.withCrossFade()).apply(new RequestOptions().override(300, 600)).into(holder.imageView);


        Log.v("sdasdsad", String.valueOf(databaseHelper.getCountSize(mDataset.get(position).getItemName())));

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int ii = databaseHelper.getCountSize(mDataset.get(position).getItemName());


                Log.v("asdasdsd", String.valueOf(ii));


                boolean b = databaseHelper.find(mDataset.get(position).getItemName());


                if (b) {
//found

                    ii = ii + 1;
                    databaseHelper.DecreaseQuantity(mDataset.get(position).getItemName(), ii);

                    holder.quantity.setText(String.valueOf(databaseHelper.getCountSize(mDataset.get(position).getItemName()))); //refine needed
                    int quantity = databaseHelper.getCountSize(mDataset.get(position).getItemName());
                    databaseHelper.UpdatePrice(mDataset.get(position).getItemName(), mDataset.get(position).getItemPrice(), quantity);

                } else {
                    //not found


                    databaseHelper.addToCart(new CartDetails(ii, mDataset.get(position).getItemName(), String.valueOf(mDataset.get(position).getItemPrice())));

                    ii = ii + 1;
                    databaseHelper.DecreaseQuantity(mDataset.get(position).getItemName(), ii);

                    holder.quantity.setText(String.valueOf(databaseHelper.getCountSize(mDataset.get(position).getItemName()))); //refine needed


                    int quantity = databaseHelper.getCountSize(mDataset.get(position).getItemName());
                    databaseHelper.UpdatePrice(mDataset.get(position).getItemName(), mDataset.get(position).getItemPrice(), quantity);

                }


            }
        });


        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean b = databaseHelper.find(mDataset.get(position).getItemName());
                if (b) {

                    int ii = databaseHelper.getCountSize(mDataset.get(position).getItemName());

                    if (ii > 0) {
                        ii = ii - 1;


                        databaseHelper.DecreaseQuantity(mDataset.get(position).getItemName(), ii);
                        holder.quantity.setText(String.valueOf(databaseHelper.getCountSize(mDataset.get(position).getItemName()))); //refine needed


                        int quantity = databaseHelper.getCountSize(mDataset.get(position).getItemName());
                        databaseHelper.UpdatePrice(mDataset.get(position).getItemName(), mDataset.get(position).getItemPrice(), quantity);

                    } else {


                    }

                } else {

                }


            }
        });
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, IndividualSelectionActivity.class);
                intent.putExtra("itemname", mDataset.get(position).getItemName());
                intent.putExtra("url", mDataset.get(position).getImageUrl());
                intent.putExtra("price", String.valueOf(mDataset.get(position).getItemPrice()));
                intent.putExtra("rating", String.valueOf(mDataset.get(position).getAverageRating()));
                context.startActivity(intent);
            }
        });

    }

    public void addItem(Food dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemRangeChanged(index, mDataset.size());
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRangeChanged(index, mDataset.size());
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView rating;
        TextView name;
        TextView price;
        ImageView imageView;
        TextView quantity;
        Button plus, minus;
        CardView card_view;


        public DataObjectHolder(View itemView) {
            super(itemView);
            card_view = itemView.findViewById(R.id.card_view);
            rating = itemView.findViewById(R.id.rating);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.imageView);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
            quantity = itemView.findViewById(R.id.quantity);
            Log.i(LOG_TAG, "Adding Listener");


        }


    }
}