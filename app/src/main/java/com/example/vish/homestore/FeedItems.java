package com.example.vish.homestore;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by VISH on 3/18/2018.
 */

public class FeedItems {

    String title;
    String link;
    String description;
    String pubDate;
    String thumbnailUrl;
    String price;
    String StarUrl;
    String Rating_stars;
    String Format;
    String Actors;
    String Director;
    String PG; //Parental Guide
    String SORT_TYPE;
    FeedItems feedItems;


    public FeedItems(String title, String link, String description, String pubDate, String thumbnailUrl,
                     String price, String starUrl, String rating_stars, String format, String actors, String director, String PG) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.thumbnailUrl = thumbnailUrl;
        this.price = price;
        StarUrl = starUrl;
        Rating_stars = rating_stars;
        Format = format;
        Actors = actors;
        Director = director;
        this.PG = PG;
    }

    public FeedItems() {

    }

    //Declaring Comaprator for sorting : By_NAME_ALPHABETICAL
    public static final Comparator<FeedItems> BY_NAME_ALPHABETICAL = new Comparator<FeedItems>() {
        @Override
        public int compare(FeedItems o1, FeedItems o2) {
            return o1.title.compareTo(o2.title);
        }
    };
    public static final Comparator<FeedItems> BY_PRICE = new Comparator<FeedItems>() {
        @Override
        public int compare(FeedItems o1, FeedItems o2) {
            return o1.price.compareTo(o2.price);
        }
    };
    public static final Comparator<FeedItems> BY_STARS = new Comparator<FeedItems>() {
        @Override
        public int compare(FeedItems o1, FeedItems o2) {
            return o1.Rating_stars.compareTo(o2.price);
        }
    };

    // FILTERING MEATHODS


    public static final ArrayList<FeedItems> FILTER_BY(ArrayList<FeedItems> list, String text){
        ArrayList<FeedItems> filtered_list = new ArrayList<>();
        ArrayList<String> pglist = new ArrayList<>();
        if(text.isEmpty()){
            //items.addAll(itemsCopy);
            return list;
        } else{
            text = text.toLowerCase();
            for(FeedItems item: list){
                try {
                    Log.d("PG StTATUS:", item.PG);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
//                if(item.PG.toLowerCase().contains(text)){
//                    filtered_list.add(item);
//                }
            }
            return filtered_list;
        }
    }

    public static final ArrayList<FeedItems> FILTER_BY_PG(ArrayList<FeedItems> list, String clickedItem){

        ArrayList<FeedItems> filtered_list = new ArrayList<FeedItems>();
        if (clickedItem.equalsIgnoreCase("Parents Strongly Cautioned")) {
            filtered_list = FILTER_BY(list, "Parents Strongly Cautioned");
        }
        else if (clickedItem.equalsIgnoreCase("Parental Guidance Suggested")) {
            filtered_list = FILTER_BY(list, "Parental Guidance Suggested");
        }
        return filtered_list;
    }


    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        this.Actors = actors;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        this.Director = director;
    }

    public String getPG() {
        return PG;
    }

    public void setPG(String PG) {
        this.PG = PG;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }


    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getStarUrl() {
        return StarUrl;
    }

    public void setStarUrl(String starUrl) {
        this.StarUrl = starUrl;
    }

    public void setPrice(String price){
        this.price = price;
    }
    public String getPrice(){
        return price;
    }

    public String getRating_stars() {
        return Rating_stars;
    }

    public void setRating_stars(String rating_stars) {
        this.Rating_stars = rating_stars;
    }
    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }


    public FeedItems getFeedItems() {
        return feedItems;
    }

    public void setFeedItems(FeedItems feedItems) {
        this.feedItems = feedItems;
    }

    public void set_SORT_TYPE(String sort_type){
        this.SORT_TYPE = SORT_TYPE;
    }
    public String getSORTTYPE(){
        return SORT_TYPE;
    }
}
