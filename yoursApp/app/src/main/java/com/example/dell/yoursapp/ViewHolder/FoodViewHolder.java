package com.example.dell.yoursapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.yoursapp.Interface.ItemClickListener;
import com.example.dell.yoursapp.R;


/**
 * Created by 123456 on 2017/11/17.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView food_name;
    public ImageView food_image,fav_image;
    public TextView food_price;
    public ImageView quick_cart;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodViewHolder(View itemView) {
        super(itemView);

        food_name =(TextView)itemView.findViewById(R.id.food_name);
        food_image = (ImageView)itemView.findViewById(R.id.food_image);
        food_price=itemView.findViewById(R.id.food_price);
        quick_cart=itemView.findViewById(R.id.btn_quick_cart);
        fav_image=itemView.findViewById(R.id.fav_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view, getAdapterPosition(), false);

    }

}
