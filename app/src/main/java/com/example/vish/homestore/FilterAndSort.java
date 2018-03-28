package com.example.vish.homestore;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by VISH on 3/19/2018.
 */

public class FilterAndSort  {


    // Sort: Alphabetical, Ratings, Price
    // Filter: price range, rating range, PG



    // movie: title, actors[], director, price, format, ratings, pg
    final static ArrayList<String> format_list = new ArrayList<>();
    final static ArrayList<String> movieTitle_list = new ArrayList<>();
    final static
    public ArrayList<String> getFormat_list() {
        return format_list;
    }

    public void setFormat_list(ArrayList<FeedItems> format_list) {
        //Collections.sort(format_list, FeedItems.BY_NAME_ALPHABETICAL);
        format_list = format_list;
    }

    public void additem(String item, ArrayList<String> List){
        List.add(item);
    }

    public void add_to_FormatList(String item){
        format_list.add(item);
        Log.d("Item Added status:", item);
        Log.d("Whole List", format_list.toString());
    }

    public static ArrayList<FeedItems> SORT_BY_TITLE(ArrayList<FeedItems> feedItems){

        Collections.sort(feedItems, FeedItems.BY_NAME_ALPHABETICAL);
        return feedItems;
    }
    public static void search_filter(ArrayList<FeedItems> feedItems){

     // ArrayList<FeedItems> filteredList = new ArrayList<>();
      //for (FeedItems items: )

    }
    //...............
    // .....SORTING..................
//
//    Class DVDvalues{
//        String title;
//        String price;
//        String ratings;
//        String format;
//
//
//    };

}

