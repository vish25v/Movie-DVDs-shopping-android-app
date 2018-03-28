package com.example.vish.homestore.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vish.homestore.Database.DBModel;
import com.example.vish.homestore.Database.DatabaseHelper;
import com.example.vish.homestore.FeedItems;
import com.example.vish.homestore.R;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class item_fullScreen extends AppCompatActivity {

    TextView tv_price, tv_title, tv_description;
    ImageView imageView_detail;
    String url_string = "";
    String url_string_stars = "";
    URL img_url;
    ImageView imageView_Star_Rating;
    Button btn_add_to_cart;
    static public int counter = 0;
    ArrayList<FeedItems> add_to_cart_list;
    public static TextView tvCounter = null;
    private DatabaseHelper db;
    private List<DBModel> cartList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_full_screen);
        tv_price = (TextView) findViewById(R.id.tv_detail_price);
        tv_title = (TextView) findViewById(R.id.tv_detail_title);
        tv_description = (TextView) findViewById(R.id.tv_detail_description);
        imageView_detail = (ImageView) findViewById(R.id.iv_img_detail);
        imageView_Star_Rating = (ImageView) findViewById(R.id.iv_star);
        btn_add_to_cart = (Button) findViewById(R.id.button_addToCart);
        tvCounter = (TextView) findViewById(R.id.tv_counter);
        db = new DatabaseHelper(this);
        //..........DB


        final ArrayList<FeedItems> currentList = new ArrayList<FeedItems>();
        add_to_cart_list = new ArrayList<FeedItems>();

        final Bundle bundle = getIntent().getExtras();
        final Integer position = bundle.getInt("pos");
        final String title = bundle.getString("Title");
        final String price = bundle.getString("Price");
        final String imgsrc = bundle.getString("ImgSrc");

        try {
            FeedItems f = null;
            Log.d("POSITION:", String.valueOf(position));
           // f=currentList.get(position);
           // Log.d("Current Price : ", f.getPrice());
            tv_price.setText(bundle.getString("Price"));
            tv_title.setText(bundle.getString("Title"));
            url_string = bundle.getString("ImgSrc");
            url_string_stars =  bundle.getString("StarUrl");


            //img_url = new URL(url_string);
            Picasso.with(getApplicationContext()).load(url_string).into(imageView_detail);
            Picasso.with(getApplicationContext()).load(url_string_stars).into(imageView_Star_Rating);

            tv_description.setText(bundle.getString("Description"));


        } catch (NullPointerException e) {
            e.printStackTrace();
        }



        /////////////////////////////////////

        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //counter = MainActivity.counter;
                    counter = counter + 1;
                    String c = String.valueOf(counter);
                    Log.d("counterVal: ", c);
                    //tvCounter =  MainActivity.tvCounter;
                    tvCounter.setText(c);
                    // INSERT IN DB
                    //Todo 2: fix the DB bug
                   // AddItemToCart(title, price, imgsrc);
                   // BACKUP SHARED PREFRENCES
                    SharedPreferences sharedPreferences = getSharedPreferences("ItemAdded", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("TITLE" + c, title);
                    editor.putString("PRICE" + c, price);
                    editor.putString("IMGSRC" +c, imgsrc);
                    editor.apply();
                    Toast.makeText(item_fullScreen.this, "Item added to the cart", Toast.LENGTH_LONG).show();

                    //MyAdapter adapter;
//                    FeedItems current_feeditem;
//                    current_feeditem = currentList.get(position);
//                    String t = current_feeditem.title;
//                    Log.d("ITEM CLICKED:", t);

                }
            });
//
        }


//    public void savedata(View view){
//        SharedPreferences sharedPreferences = getSharedPreferences("ItemAdded", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        String c = String.valueOf(counter);
//        editor.putString("TITLE" + c, title);
//
//    }
    private void AddItemToCart(String title, String price, String imgsrc) {
        // inserting note in db and getting
        // newly inserted note id
        long id = db.insertItem(title, price, imgsrc);
        Log.d("Database", "check");

        // get the newly inserted note from db
        DBModel n = db.getDBModel(id);

        if (n != null) {
            // adding new note to array list at 0 position
            cartList.add(0, n);

            // refreshing the list
            //ShoppingCart.madapter.notifyDataSetChanged();

        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        String c = String.valueOf(counter);
        Log.d("counterVal_LATER: ", c);
        //tvCounter =  MainActivity.tvCounter;
        tvCounter.setText(c);

    }
}

