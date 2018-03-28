package com.example.vish.homestore;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.vish.homestore.Activities.MainActivity;
import com.example.vish.homestore.Adapters.MyAdapter;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by VISH on 3/18/2018.
 */

public class ReadFeed extends AsyncTask<Void, Void, Void> {

    Context context;
    ProgressDialog progressDialog;
    String address = "https://www.amazon.com/gp/rss/bestsellers/dvd";
    URL url;
    public static String SORT_TYPE = "";
    ArrayList<FeedItems> feedItems;
    private ArrayList<FeedItems> sortedList;
    ArrayList<FilterAndSort> filterAndSorts;
    RecyclerView recyclerView;
    Button btn_sort;
    ArrayList <FeedItems> backupList = new ArrayList<FeedItems>();


    public ReadFeed(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();


    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();

        final MyAdapter adapter = new MyAdapter(context, feedItems);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration( new VerticalSpace(20));

        MainActivity.btn_sort_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTON CLICKED:", "Sort/Filter Button");
                MainActivity.sort_filer_layout.setVisibility(View.VISIBLE);

            }

        });
        MainActivity.imageButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.searchBar.setVisibility(View.GONE);
                feedItems.clear();
                feedItems.addAll(backupList);
                adapter.notifyDataSetChanged();
            }
        });


        // .............SEARCH ...................................
        MainActivity.et_searchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();

                ArrayList<FeedItems> filteredList = new ArrayList<>();
                for (FeedItems items: backupList){
                    if (items.getTitle().toLowerCase().contains(text.toLowerCase())){
                        filteredList.add(items);
                    }
                }
                feedItems.clear();
                feedItems.addAll(filteredList);
                adapter.notifyDataSetChanged();
            }
        });
        MainActivity.spinner.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int i, long l) {
                // TODO Auto-generated method stub
                String s = MainActivity.sortOptions.get(i);
                Context context;
                Log.d("SPINNER:", s);
                if (s.equalsIgnoreCase("Sort by Rating")){
                    SORT_TYPE = "SORT_BY_RATING";
                    Log.d("SORT_TYPE_set_to", SORT_TYPE);
                    Collections.sort(backupList, FeedItems.BY_STARS);
                    adapter.notifyDataSetChanged();
                } else if (s.equalsIgnoreCase("Sort by Price")){
                    SORT_TYPE = "SORT_BY_PRICE";
                    Log.d("SORT_TYPE_set_to", SORT_TYPE);
                    Collections.sort(backupList, FeedItems.BY_PRICE);
                    adapter.notifyDataSetChanged();
                }else if(s.equalsIgnoreCase("Sort by Name")){
                    SORT_TYPE = "SORT_BY_TITLE";
                    Log.d("SORT_TYPE_set_to", SORT_TYPE);
                    Collections.sort(backupList, FeedItems.BY_NAME_ALPHABETICAL);
                    // Collections.
                    adapter.notifyDataSetChanged();
                }
            }
            // If no option selected
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

        //.................... CHECKBOX FILTERS............................

        //addListenerOnPG_Checkboxes();
        backupList.addAll(feedItems);

        MainActivity.btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adapter.notifyDataSetChanged();
                Log.d("BUTTON CLICKED:", "DONE");
                String result = "Selected Courses";
                String formatSelected = "";
                Log.d("PG_S_CHECKED", String.valueOf(MainActivity.PG_S_CHECKED));
                if(MainActivity.PG_S_CHECKED == 1){
                    //feedItems = FeedItems.FILTER_BY_PG(feedItems, "Parental Guidance Suggested");
                    //adapter.notifyDataSetChanged();
                    result += "cb_suggest";

                }
                if(MainActivity.PG_C_CHECKED == 1){
                    //feedItems = FeedItems.FILTER_BY_PG(feedItems, "Parents Strongly Cautioned");
                    //adapter.notifyDataSetChanged();
                    result += "cb_cautioned";
                }

                if (MainActivity.FORMAT_DVD_CHECKED == 1){
                    result += "_DVD_";
                    formatSelected = "DVD";

                }
                if (MainActivity.FORMAT_BLUERAY_CHECKED == 1){
                    result += "_Blue-ray_";
                    formatSelected = "Blu-ray";
                }
                Log.d("CHECKBOX RESULT:", result);

                if (result.contains("Blue-ray") ){
                    ArrayList<FeedItems> retain =
                            new ArrayList<FeedItems>(backupList.size());
                    for (FeedItems feedItems1 : backupList) {
                        if (feedItems1.getFormat().contains("Blu-ray")) {
                            retain.add(feedItems1);
                            Log.d("PG HERE: ", feedItems1.getPG());
                        }
                    }
                    feedItems.clear();
                    feedItems.addAll(retain);
                    adapter.notifyDataSetChanged();
                }
                if (result.contains("DVD") ){
                    ArrayList<FeedItems> retain =
                            new ArrayList<FeedItems>(backupList.size());
                    for (FeedItems feedItems1 : backupList) {
                        if (feedItems1.getFormat().contains("DVD")) {
                            retain.add(feedItems1);
                            Log.d("PG HERE: ", feedItems1.getPG());
                        }
                    }
                    feedItems.clear();
                    feedItems.addAll(retain);
                    adapter.notifyDataSetChanged();
                }

                if (result.contains("cb_cautioned") && !result.contains("cb_suggest")){
                    ArrayList<FeedItems> retain =
                            new ArrayList<FeedItems>(backupList.size());
                    for (FeedItems feedItems1 : backupList) {
                        if (result.contains("DVD") && !result.contains("Blue-ray")) {
                            if (feedItems1.getPG().contains("Parents Strongly Cautioned") && feedItems1.getFormat().contains("DVD")) {
                                retain.add(feedItems1);
                                Log.d("PG HERE: ", feedItems1.getPG());
                            }
                        }else if (result.contains("Blue-ray") && !result.contains("DVD")){
                             if (feedItems1.getPG().contains("Parents Strongly Cautioned") && feedItems1.getFormat().contains("Blu-ray")) {
                                retain.add(feedItems1);
                                Log.d("PG HERE: ", feedItems1.getPG());
                            }
                        }else if (result.contains("DVD") && result.contains("Blue-ray")) {
                            if (feedItems1.getPG().contains("Parents Strongly Cautioned")) {
                                retain.add(feedItems1);
                                Log.d("PG HERE: ", feedItems1.getPG());
                            }
                        } else if (!result.contains("DVD") && !result.contains("Blue-ray")) {
                            if (feedItems1.getPG().contains("Parents Strongly Cautioned")) {
                                retain.add(feedItems1);
                                Log.d("PG HERE: ", feedItems1.getPG());
                            }
                        }
                    }
                    feedItems.clear();
                    feedItems.addAll(retain);
                    adapter.notifyDataSetChanged();

                }else if (result.contains("cb_suggest") && !result.contains("cb_cautioned")){

                    ArrayList<FeedItems> retain =
                            new ArrayList<FeedItems>(backupList.size());
                    for (FeedItems feedItems1 : backupList) {
//                        if (feedItems1.getPG().contains("Parental Guidance Suggested")) {
//                            retain.add(feedItems1);
//                            Log.d("PG HERE: ", feedItems1.getPG());
//                        }
                        if (result.contains("DVD") && !result.contains("Blue-ray")) {
                            if (feedItems1.getPG().contains("Parental Guidance Suggested") && feedItems1.getFormat().contains("DVD")) {
                                retain.add(feedItems1);
                                Log.d("PG HERE: ", feedItems1.getPG());
                            }
                        }else if (result.contains("Blue-ray") && !result.contains("DVD")){
                            if (feedItems1.getPG().contains("Parental Guidance Suggested") && feedItems1.getFormat().contains("Blu-ray")) {
                                retain.add(feedItems1);
                                Log.d("PG HERE: ", feedItems1.getPG());
                            }
                        }else if (result.contains("DVD") && result.contains("Blue-ray")) {
                            if (feedItems1.getPG().contains("Parental Guidance Suggested")) {
                                retain.add(feedItems1);
                                Log.d("PG HERE: ", feedItems1.getPG());
                            }
                        }else if (result.contains("DVD") && result.contains("Blue-ray")) {
                            if (feedItems1.getPG().contains("Parental Guidance Suggested")) {
                                retain.add(feedItems1);
                                Log.d("PG HERE: ", feedItems1.getPG());
                            }
                        }

                        //............

                    }
                    feedItems.clear();
                    feedItems.addAll(retain);
                    adapter.notifyDataSetChanged();
                }else if (result.contains("cb_suggest") && !result.contains("cb_cautioned")){

                    ArrayList<FeedItems> retain =
                            new ArrayList<FeedItems>(backupList.size());
                    for (FeedItems feedItems1 : backupList) {
                        if (feedItems1.getPG().contains("Parental Guidance Suggested")) {
                            retain.add(feedItems1);
                            Log.d("PG HERE: ", feedItems1.getPG());
                        }
                    }
                    feedItems.clear();
                    feedItems.addAll(retain);
                    adapter.notifyDataSetChanged();
                }
                else if (result.contains("cb_suggest") && result.contains("cb_cautioned") || result.contains("DVD") && result.contains("Blue-ray")){
                   feedItems.clear();
                   feedItems.addAll(backupList);
                   adapter.notifyDataSetChanged();
                }
                else if (!result.contains("cb_suggest") && !result.contains("cb_cautioned") && !result.contains("DVD") && !result.contains("Blue-ray")){
                    feedItems.clear();
                    feedItems.addAll(backupList);
                    adapter.notifyDataSetChanged();
                }


                // handling sort/filter card layout visibility
                MainActivity.sort_filer_layout.setVisibility(View.GONE);
            }
        });


    }

    @Override
    protected Void doInBackground(Void... voids) {
        processXML(getData());
        return null;
    }

    private void processXML(Document data) {
        if (data!=null) {
//            Log.d("ROOT", data.getDocumentElement().getNodeName());
            feedItems = new ArrayList<>();
            filterAndSorts = new ArrayList<>();

            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            String htmlString;
            String title_main;
            String Title_trimmed;
            String start;
            String Dir;
            String PG_Rated_trimmed;

            for(int i = 0; i < items.getLength(); i++){
                Node currentChild = items.item(i);
                if (currentChild.getNodeName().equalsIgnoreCase("item")){
                    FeedItems item = new FeedItems();
                    FilterAndSort itemFS = new FilterAndSort();
                    NodeList itemChilds = currentChild.getChildNodes();
                    feedItems.add(item);
                    filterAndSorts.add(itemFS);
                    for(int j = 0 ; j < itemChilds.getLength() ; j++){
                        Node current = itemChilds.item(j);
//                        Log.d("textContent", current.getTextContent());
                        if (current.getNodeName().equalsIgnoreCase("title")){

                            title_main = current.getTextContent();
                            title_main= title_main.substring(title_main.indexOf("#") + 1);
                            Log.d("after # trim:", title_main);
                            title_main = title_main.substring(title_main.indexOf(":"));
                           // Title_trimmed = title_main.replace("#")
                            Log.d("title after : trim", title_main);
                            title_main = title_main.substring(1);
                            item.setTitle(title_main);
                            Log.d("Title:", current.getTextContent());
                            Log.d("itemTitle", item.getTitle());
                        } else if (current.getNodeName().equalsIgnoreCase("description")){
                            item.setDescription(current.getTextContent());
                            htmlString = item.getDescription();
                            String[] x = stripHtml(htmlString);
                            Log.d("htmlStripString:", x[0]);
                            Log.d("priceLinkHtml: ", x[1]);
                            Log.d("StarLink: ", x[2]);
                            Log.d("StarAlt: ", x[3]);
                            start = x[3];
                            start = start.substring(0,3);
                            Log.d("Starcount:", start.trim());
                            Log.d("MovieContributors: ", x[4]);
                            Log.d("Actors:", x[5]);
                            Dir = x[6].replace(",", "").trim();
                            Log.d("Director:", Dir);
                            Log.d("Format", x[7]);
                            itemFS.add_to_FormatList(x[7]);
                           // PG_Rated_trimmed = x[8];
                            PG_Rated_trimmed   = x[8].replace("&nbsp;", "").trim();
                            Log.d("PG_Rated", PG_Rated_trimmed);
                            item.setPrice(x[1]);
                            item.setThumbnailUrl(x[0]);
                            item.setStarUrl(x[2]);
                            item.setDescription(x[4]);
                            item.setRating_stars(start);
                            item.setActors(x[5]);
                            item.setDirector(x[6]);
                            item.setFormat(x[7]);
                            item.setPG(PG_Rated_trimmed);

                            //Log.d("htmlStrip:", x.toString());
                            //Log.d("itemDescription", item.getDescription());
                        } else if(current.getNodeName().equalsIgnoreCase("pubDate")){
                            item.setPubDate(current.getTextContent());
                            Log.d("itemPubDate", item.getPubDate());
                        } else if (current.getNodeName().equalsIgnoreCase("link")) {
                            item.setLink(current.getTextContent());
                            Log.d("itemLink", item.getLink());
                        }
                    }

                }
            }
        }
    }

    public String[] stripHtml(String html) {
        try {


            org.jsoup.nodes.Document document = Jsoup.parse(html);
            //piclink
            Log.d("jsoupDoc", document.data());
            String picLink = document.select("img").first().attr("src");
            //description
//            String text = document.select("p").first().text();
//            text = text.substring(0, text.indexOf('['));
            //String price = document.nodeName().equals("Buy");
            String priceLink = document.select("b").text();
            String text = priceLink;
            ///////////////////////////
            Elements images = document.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
            String StarLink = "";
            String star_alt = "";
            for (org.jsoup.nodes.Element image : images) {
                if (image.attr("src").contains(".gif")) {
                    StarLink = image.attr("src");
                    star_alt = image.attr("alt");
                } else StarLink = "fail";
            }

            Elements spans = document.getElementsByClass("riRssContributor");
            String movieContributors = "";
            ArrayList<String> Actors = new ArrayList<String>();
            String actor = "";
            String Director = "";
            String PG_Rated = "";
            String Format = "";
            Elements spans_byLinePipe = document.getElementsByClass("byLinePipe");
            for (org.jsoup.nodes.Element span :  spans_byLinePipe) {
                if (span.text().contains("(Actor)")) {
                    actor = span.previousSibling().outerHtml();
                    Actors.add(actor);
                } else if (span.text().contains("Director")) {
                    Director = span.previousSibling().outerHtml();
                } else if (span.text().contains("Format")){
                    Format = span.nextSibling().outerHtml();
                } else if (span.text().contains("Rated")){
                    PG_Rated = span.nextSibling().outerHtml();
                }


            }

            for (org.jsoup.nodes.Element span :  spans) {
                movieContributors = span.text();

            }


            // Sample output of the above for loop: Tom Holland (Actor),
            // Hannibal Buress (Actor), Jon Watts (Director) | Rated: PG-13 (Parents Strongly Cautioned) | Format: Blu-ray
//            for (org.jsoup.nodes.Element image : images) {
//
//                System.out.println("\nsrc : " + image.attr("src"));
//                System.out.println("height : " + image.attr("height"));
//                System.out.println("width : " + image.attr("width"));
//                System.out.println("alt : " + image.attr("alt"));
//
//            }
            ////..........................................................................


            String[] linkAndDescription = {picLink, text,StarLink, star_alt, movieContributors, Actors.toString(), Director, Format, PG_Rated };

            return linkAndDescription;

        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

    public void setSORT_TYPE (String sort_type){
        SORT_TYPE =sort_type;
    }

    public void addListenerOnPG_Checkboxes() {



        MainActivity.cb_sugg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    Log.d("filterPG:", "suggested");
                    feedItems = FeedItems.FILTER_BY_PG(feedItems, "Parental Guidance Suggested");
                }

            }
        });

        MainActivity.cb_cautioned.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    Log.d("filterPG:", "Cautioned");
                    feedItems = FeedItems.FILTER_BY_PG(feedItems, "Parents Strongly Cautioned");
                }

            }
        });

    }


    ArrayList<FeedItems> FILTER_BY(ArrayList<FeedItems> list, String text){
        ArrayList<FeedItems> filtered_list = new ArrayList<>();
        ArrayList<String> pglist = new ArrayList<>();
        if(text.isEmpty()){
            //items.addAll(itemsCopy);
            return list;
        } else{
            text = text.toLowerCase();
            for(FeedItems item: list){
                try {
                   // Log.d("PG StTATUS:", item.PG);
                    if(item.PG.toLowerCase().contains(text)){
                        filtered_list.add(item);
                        Log.d("PG HERE:", item.PG);
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

            }
            list.clear();
            list.addAll(filtered_list);
            return list;
        }
    }

     ArrayList<FeedItems> FILTER_BY_PG(ArrayList<FeedItems> list, String clickedItem){

        ArrayList<FeedItems> filtered_list = new ArrayList<FeedItems>();
        if (clickedItem.equalsIgnoreCase("Parents Strongly Cautioned")) {
            filtered_list = FILTER_BY(list, "Parents Strongly Cautioned");
        }
        else if (clickedItem.equalsIgnoreCase("Parental Guidance Suggested")) {
            filtered_list = FILTER_BY(list, "Parental Guidance Suggested");
        }
        return filtered_list;
    }

    public ArrayList<FeedItems> FILTER_LIST(ArrayList<FeedItems> list, String string, String TAG){
        ArrayList<FeedItems> retain =
                new ArrayList<FeedItems>(feedItems.size());
        String checktag="";



        for (FeedItems feedItems1 : feedItems) {
            if (TAG == "PG")
            {checktag = feedItems1.getPG();}


            if (checktag.contains(string)) {
                retain.add(feedItems1);
                Log.d("PG HERE: ", checktag);
            }
        }
        feedItems.clear();
        feedItems.addAll(retain);
        return feedItems;
    }

//    public ArrayList<FeedItems> sortList(ArrayList<FeedItems> list){
//        //ArrayList<FeedItems> sortedL = new ArrayList<>();
//        Collections.sort(list, FeedItems.BY_NAME_ALPHABETICAL);
//        return list;
//    }
    public Document getData(){
        try {
            url =  new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream =  connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(inputStream);
            return xmlDocument;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
