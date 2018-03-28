package com.example.vish.homestore.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.vish.homestore.Adapters.CartAdapter;
import com.example.vish.homestore.FeedItems;
import com.example.vish.homestore.R;

import java.util.ArrayList;

public class ShoppingCart extends AppCompatActivity {
    RecyclerView mrecyclerView;
    CartAdapter madapter;
    ArrayList<FeedItems> addedItems = new ArrayList<FeedItems>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        mrecyclerView = (RecyclerView) findViewById(R.id.rc_cart);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mrecyclerView.hasFixedSize();
        FeedItems f = new FeedItems();
        FeedItems f2 = new FeedItems();


        String c = String.valueOf(item_fullScreen.counter);
        SharedPreferences sharedPreferences = getSharedPreferences("ItemAdded", Context.MODE_PRIVATE);
        String title = sharedPreferences.getString("TITLE"+ c, "");
        String price = sharedPreferences.getString("PRICE"+ c, "");
        String imgsrc = sharedPreferences.getString("IMGSRC"+ c, "");
       if (title!=null )
        f.setTitle(title);
        f.setThumbnailUrl(imgsrc);
        f.setPrice(price);

        f2.setTitle("Test 2");
        f2.setThumbnailUrl("https://images-na.ssl-images-amazon.com/images/I/61-l3dKAfwL._SL160_.jpg");
        f2.setPrice("$50");

        addedItems.add(f);
        addedItems.add(f2);

        Log.d("Saved title:", title);
        //addedItems = getCartItem();

        madapter = new CartAdapter(ShoppingCart.this, addedItems );
        mrecyclerView.setAdapter(madapter);
    }

    public void getCartItem(){
        String c = String.valueOf(item_fullScreen.counter);
        SharedPreferences sharedPreferences = getSharedPreferences("ItemAdded", Context.MODE_PRIVATE);
        String title = sharedPreferences.getString("TITLE"+ c, "");
        String price = sharedPreferences.getString("PRICE"+ c, "");
        String imgsrc = sharedPreferences.getString("IMGSRC"+ c, "");
        Log.d("Saved title:", title);



    }
}
