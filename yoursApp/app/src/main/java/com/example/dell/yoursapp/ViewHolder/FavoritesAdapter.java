package com.example.dell.yoursapp.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dell.yoursapp.Common.Common;
import com.example.dell.yoursapp.Database.Database;
import com.example.dell.yoursapp.FoodDetail;
import com.example.dell.yoursapp.FoodList;
import com.example.dell.yoursapp.Interface.ItemClickListener;
import com.example.dell.yoursapp.Model.Favorites;
import com.example.dell.yoursapp.Model.Food;
import com.example.dell.yoursapp.Model.Order;
import com.example.dell.yoursapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesViewHolder> {

    private Context context;
    private List<Favorites> favoritesList;


    public FavoritesAdapter(Context context, List<Favorites> favoritesList) {
        this.context = context;
        this.favoritesList = favoritesList;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context)
                .inflate(R.layout.favorites_item,parent,false);
        return new FavoritesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder viewHolder, final int position) {
        viewHolder.food_name.setText(favoritesList.get(position).getFoodName());
        viewHolder.food_price.setText(String.format("$ %s",favoritesList.get(position).getFoodPrice().toString()));
        Picasso.with(context).load(favoritesList.get(position).getFoodImage()).into(viewHolder.food_image);


        viewHolder.quick_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isExists=new Database(context).checkFoodExists(favoritesList.get(position).getFoodId(), Common.currentUser.getPhone());
                if (!isExists) {
                    new Database(context).addToCart(new Order(
                            Common.currentUser.getPhone(),
                            favoritesList.get(position).getFoodId(),
                            favoritesList.get(position).getFoodName(),
                            "1",
                            favoritesList.get(position).getFoodPrice(),
                            favoritesList.get(position).getFoodDiscount(),
                            favoritesList.get(position).getFoodImage()
                    ));
                } else {
                    new Database(context).increaseCart(Common.currentUser.getPhone(),
                           favoritesList.get(position).getFoodId());
                }
                Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });


        final Favorites local = favoritesList.get(position);
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                //start new activity
                Intent foodDetail =  new Intent(context, FoodDetail.class);
                foodDetail.putExtra("FoodId", favoritesList.get(position).getFoodId());
                context.startActivity(foodDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    public  void removeItem(int position){
        favoritesList.remove(position);
        notifyItemRemoved(position);
    }

    public  void restoreItem(Favorites item,int position){
        favoritesList.add(position,item);
        notifyItemInserted(position);
    }

    public Favorites getItem(int position){
        return favoritesList.get(position);
    }
}
