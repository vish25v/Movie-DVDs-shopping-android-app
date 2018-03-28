package com.example.vish.homestore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.vish.homestore.FeedItems;
import com.example.vish.homestore.R;
import com.example.vish.homestore.Activities.item_fullScreen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by VISH on 3/23/2018.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    ArrayList<FeedItems> feedItems;
    ArrayList<FeedItems> sortedList;

    Context context;

    public CartAdapter(Context context, ArrayList<FeedItems> feedItems) {
        this.feedItems = feedItems;
        this.context = context;
    }

    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        CartAdapter.MyViewHolder holder = new CartAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CartAdapter.MyViewHolder holder, final int position) {
        try {
            final FeedItems current = feedItems.get(position);
            holder.Title.setText(current.getTitle());
            holder.Price.setText(current.getPrice());

            Picasso.with(context).load(current.getThumbnailUrl()).into(holder.iv_Thumbnail);
            YoYo.with(Techniques.FadeIn).playOn(holder.cardView);
            //holder.cardView
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, item_fullScreen.class);
                    Bundle bundle = new Bundle();

                    intent.putExtra("Price", current.getPrice());
                    intent.putExtra("pos", holder.getAdapterPosition());
                    intent.putExtra("ImgSrc", current.getThumbnailUrl());
                    intent.putExtra("Title", current.getTitle());
                    intent.putExtra("StarUrl", current.getStarUrl());
                    intent.putExtra("Description", current.getDescription());
                    intent.putExtra("RatingStars", current.getRating_stars());
                    intent.putExtra("Actors", current.getActors());
                    intent.putExtra("Director", current.getDirector());
                    intent.putExtra("Format", current.getFormat());

                    //intent.putExtra("")

                    //intent.putExtra("")
                    context.startActivity(intent);
                }
            });

        }catch (IllegalArgumentException e){
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Title, Price;
        ImageView iv_Thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.tv_cart_item_title);
            Price = (TextView) itemView.findViewById(R.id.tv_cart_item_price);
            iv_Thumbnail = (ImageView) itemView.findViewById(R.id.iv_img_in_cart);
            cardView =  (CardView) itemView.findViewById(R.id.cart_cardView);

        }
    }
}

