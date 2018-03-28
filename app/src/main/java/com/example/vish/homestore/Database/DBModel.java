package com.example.vish.homestore.Database;

/**
 * Created by VISH on 3/23/2018.
 */

public class DBModel {

    public static final String TABLE_NAME = "CartTable";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_IMGSRC = "imgsrc";

    private int id;
    private String title;
    private String price;
    private String imgsrc;



    // Create table SQL query
//    public static final String CREATE_TABLE =
//            "CREATE TABLE " + TABLE_NAME + "("
//                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                    + COLUMN_TITLE + " TEXT,"
//                    + COLUMN_PRICE + " TEXT,"
//                    + COLUMN_IMGSRC + " TEXT"
//                    + ")";
    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TITLE + " TEXT,"
            + COLUMN_PRICE + " TEXT, " + COLUMN_IMGSRC + " TEXT" + ")";

    public DBModel() {
    }

    public DBModel(int id, String title, String price, String imgsrc) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.imgsrc = imgsrc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }
}
