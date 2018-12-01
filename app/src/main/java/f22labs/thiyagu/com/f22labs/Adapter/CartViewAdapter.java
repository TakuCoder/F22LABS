package f22labs.thiyagu.com.f22labs.Adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.List;

import f22labs.thiyagu.com.f22labs.ActivityCartModule.CartActivityContract;
import f22labs.thiyagu.com.f22labs.Data.CartPojo;
import f22labs.thiyagu.com.f22labs.Database.DatabaseHelper;
import f22labs.thiyagu.com.f22labs.R;

public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.DataObjectHolder> {
    private static final String LOG_TAG = "CartViewAdapter";
    DatabaseHelper databaseHelper;
    private List<CartPojo> mDataset;
    CartActivityContract.view vieww;
    //BitmapUtils bitmapUtils = BitmapUtils.getInstance();


    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView product_name;
        TextView product_price, quantity, totalprice, quantity_text, total_price;
        ImageView imageholder;
        CardView cardView;

        Button minus, plus;


        public DataObjectHolder(View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            imageholder = itemView.findViewById(R.id.image_holder);
            cardView = itemView.findViewById(R.id.card_view);
          //  totalprice = itemView.findViewById(R.id.totalprice);
            quantity = itemView.findViewById(R.id.quantity);
            total_price = itemView.findViewById(R.id.total_price);
            quantity_text = itemView.findViewById(R.id.quantity_text);

            minus = itemView.findViewById(R.id.minus);
            plus = itemView.findViewById(R.id.plus);


        }


    }


    public CartViewAdapter(List<CartPojo> myDataset, Context context,CartActivityContract.view view) {
        mDataset = myDataset;

        databaseHelper = DatabaseHelper.getInstance(context);

    this.vieww = view;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_cart, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        Log.v(LOG_TAG, String.valueOf(position));


        String quantity_value = String.valueOf(databaseHelper.getCountSize(mDataset.get(position).getName()));
        holder.product_name.setText(mDataset.get(position).getName());
        holder.product_price.setText("Price Rs :" + String.valueOf(mDataset.get(position).getPrice()));
        holder.quantity.setText(quantity_value);
        holder.total_price.setText(String.valueOf(databaseHelper.TotalPricePerItem(mDataset.get(position).getName())));
        holder.quantity_text.setText(quantity_value);
      //  vieww.updateGrandpriceTotal(databaseHelper.getGrandPrice());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean b = databaseHelper.find(mDataset.get(position).getName());
                if (b) {

                    int ii = databaseHelper.getCountSize(mDataset.get(position).getName()); //to get specific item quantity
                    Log.v("currentsize", String.valueOf(ii));
                    if (ii > 0) {
                        ii = ii - 1;


                        databaseHelper.DecreaseQuantity(mDataset.get(position).getName(), ii);
                        holder.quantity.setText(String.valueOf(databaseHelper.getCountSize(mDataset.get(position).getName()))); //refine needed


                        int quantity = databaseHelper.getCountSize(mDataset.get(position).getName());
                        databaseHelper.UpdatePrice(mDataset.get(position).getName(), mDataset.get(position).getPrice(), quantity);
                        Log.v("sdasdsad", String.valueOf(quantity));
                        if (quantity == 0) {

                            deleteItem(position);
                        }

                    } else {

                        //databaseHelper.DecreaseQuantity(mDataset.get(position).getName(), ii);
                        //deleteItem(position);
                    }

                } else {

                }


            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ii = databaseHelper.getCountSize(mDataset.get(position).getName());
                boolean b = databaseHelper.find(mDataset.get(position).getName());


                if (b) {
//found

                    ii = ii + 1;
                    databaseHelper.DecreaseQuantity(mDataset.get(position).getName(), ii);

                    holder.quantity.setText(String.valueOf(databaseHelper.getCountSize(mDataset.get(position).getName()))); //refine needed
                    int quantity = databaseHelper.getCountSize(mDataset.get(position).getName());
                    databaseHelper.UpdatePrice(mDataset.get(position).getName(), mDataset.get(position).getPrice(), quantity);
                    holder.quantity_text.setText(String.valueOf(quantity));
                    holder.total_price.setText(String.valueOf(databaseHelper.TotalPricePerItem(mDataset.get(position).getName())));

                    vieww.updateGrandpriceTotal(databaseHelper.getGrandPrice());

                }

            }
        });

    }

    public void addItem(CartPojo dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index, mDataset.size());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}