package com.example.vish.homestore.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vish.homestore.FeedItems;
import com.example.vish.homestore.R;
import com.example.vish.homestore.ReadFeed;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Integer count =0;
    public static TextView tvCounter;
    Button btn_add_to_cart;
    public static Button btn_sort_filter;
    public static Spinner spinner;
    ArrayList<FeedItems> feedItems;
    public static ImageButton imageButtonCancel;
    public static EditText et_searchQuery;
    public static CardView sort_filer_layout;
    public static Button btn_done;
    public static ImageView search;
    public static RelativeLayout searchBar;
    public static Spinner spinner_PG;
    public static int FORMAT_BLUERAY_CHECKED = 0;
    public static int FORMAT_DVD_CHECKED = 0;
    public static int counter = 0;
    public static int PG_C_CHECKED = 0;
    public static int PG_S_CHECKED = 0;
    final static public ArrayList<String> sortOptions  = new ArrayList<String>();
    static public ArrayList<FeedItems> cartList = new ArrayList<FeedItems>();
    //final static public ArrayList<String> pg_Spinner_items = new ArrayList<String>();
    public static CheckBox cb_sugg, cb_cautioned;
    public static ImageView cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //final ArrayList<String> sortOptions = new ArrayList<String>();

        //sortOptions items:
        sortOptions.add("SORT OPTIONS:");
        sortOptions.add("Sort by Name");
        sortOptions.add("Sort by Price");
        sortOptions.add("Sort by Rating");


        cart = (ImageView) findViewById(R.id.iv_cart);
        searchBar = (RelativeLayout) findViewById(R.id.search_bar);
        searchBar.setVisibility(View.GONE);
        et_searchQuery = (EditText) findViewById(R.id.et_search);
        imageButtonCancel = (ImageButton) findViewById(R.id.imgbtn_cancel);
        tvCounter = (TextView) findViewById(R.id.tv_counter);
        search = (ImageView) findViewById(R.id.search);
        cb_sugg = (CheckBox)findViewById(R.id.checkbox_sugg);
        cb_cautioned = (CheckBox)findViewById(R.id.checkbox_cautioned);
        recyclerView = (RecyclerView) findViewById(R.id.rc_main);
        feedItems = new ArrayList<FeedItems>();
        final ReadFeed readFeed =  new ReadFeed(this, recyclerView);
        readFeed.execute();
        btn_sort_filter = (Button)findViewById(R.id.btn_sortAndFilter);
        sort_filer_layout = (CardView) findViewById(R.id.sort_filter_layout);
        btn_done = (Button) findViewById(R.id.btn_done);

        btn_sort_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedItems f = null;
                f.set_SORT_TYPE("SORT_BY_TITLE");
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBar.setVisibility(View.VISIBLE);
            }
        });


        try{
        MainActivity.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ShoppingCart.class);
                startActivity(i);

            }
        });}catch (IllegalArgumentException e){
            e.printStackTrace();
        }


        spinner = (Spinner) findViewById(R.id.spinner1);
        // Create the ArrayAdapter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this
                ,android.R.layout.simple_spinner_item,sortOptions);

        // Set the Adapter
        spinner.setAdapter(arrayAdapter);




    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_sugg:
                str = checked?"sugg Selected":"sugg Deselected";
                PG_S_CHECKED = checked ? 1 : 0;
                Log.d("PG_S_CHECKED", String.valueOf(PG_S_CHECKED));
                break;
            case R.id.checkbox_cautioned:
                str = checked?"cautioned Selected":"cautioned Deselected";
                PG_C_CHECKED = checked  ? 1 : 0;
                Log.d("PG_C_CHECKED", String.valueOf(PG_C_CHECKED));
                break;
            case R.id.checkbox_dvd:
                str = checked?"DVD Selected":"DVD Deselected";
                FORMAT_DVD_CHECKED = checked  ? 1 : 0;
                Log.d("PG_C_CHECKED", String.valueOf(PG_C_CHECKED));
                break;
            case R.id.checkbox_Blue_ray:
                str = checked?"BLUE-RAY Selected":"BLUE-RAY Deselected";
                FORMAT_BLUERAY_CHECKED = checked  ? 1 : 0;
                Log.d("PG_C_CHECKED", String.valueOf(PG_C_CHECKED));
                break;

        }
        Log.d("Checkbox resul", str);
        // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String c = String.valueOf(item_fullScreen.counter);

        tvCounter.setText(c);
        Log.d("counterVal_LATER: ", c);
        //tvCounter =  MainActivity.tvCounter;
        tvCounter.setText(c);
    }


}




