package f22labs.thiyagu.com.f22labs.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

import f22labs.thiyagu.com.f22labs.ActivityCartModule.CartActivtymodel;
import f22labs.thiyagu.com.f22labs.Data.CartDetails;
import f22labs.thiyagu.com.f22labs.Data.CartPojo;
import f22labs.thiyagu.com.f22labs.Data.Food;
import f22labs.thiyagu.com.f22labs.Data.FoodPojo;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "DatabaseHelper";
    private static SQLiteDatabase db;
    private static DatabaseHelper instance;
    private static Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "food.db";
    private static final String TABLE_NAME_PRODUCTLIST = "productlist";
    private static final String TABLE_NAME_CARTLIST = "cartlist";

    private static final String ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_FEEDBACK = "feedback";
    private static final String COLUMN_PRICE = "price";


    private static final String SQL_CREATE_TABLE_PRODUCT = "CREATE TABLE " + DatabaseHelper.TABLE_NAME_PRODUCTLIST + " (" + DatabaseHelper.ID + " INTEGER PRIMARY KEY," + DatabaseHelper.COLUMN_NAME + " TEXT," + DatabaseHelper.COLUMN_IMAGE + " BLOB," + DatabaseHelper.COLUMN_FEEDBACK + " TEXT," + DatabaseHelper.COLUMN_PRICE + " TEXT)";


    private static final String COLUMN_CART_PRODUCT_QUANTITY = "quantity";
    private static final String COLUMN_CART_PRICE = "price";
    private static final String COLUMN_CART_IMAGE = "image";
    private static final String COLUMN_CART_TOTAL_PRICE = "totalprice";
    private static final String COLUMN_CART_NAME = "name";

    private static final String SQL_CREATE_TABLE_CART = "CREATE TABLE " + DatabaseHelper.TABLE_NAME_CARTLIST + " (" + DatabaseHelper.ID + " INTEGER PRIMARY KEY," + DatabaseHelper.COLUMN_CART_PRODUCT_QUANTITY + " TEXT," + DatabaseHelper.COLUMN_CART_NAME + " TEXT," + DatabaseHelper.COLUMN_CART_PRICE + " INTEGER," + DatabaseHelper.COLUMN_CART_TOTAL_PRICE + " INTEGER," + DatabaseHelper.COLUMN_CART_IMAGE + " TEXT)";

    CartActivtymodel cartActivtymodel;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        cartActivtymodel = new CartActivtymodel();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PRODUCT);
        db.execSQL(SQL_CREATE_TABLE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PRODUCTLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CARTLIST);
        onCreate(db);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
            db = instance.getWritableDatabase();
        }

        return instance;
    }


    public void addProduct(FoodPojo food) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, food.getItemName());
        values.put(COLUMN_IMAGE, food.getImage());
        values.put(COLUMN_FEEDBACK, food.getAverageRating());
        values.put(COLUMN_PRICE, food.getItemPrice());
        db.insert(TABLE_NAME_PRODUCTLIST, null, values);
        db.close();


    }


    public void addToCart(CartDetails cartDetails) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CART_PRICE, cartDetails.getPrice());
        values.put(COLUMN_CART_NAME, cartDetails.getName());
        values.put(COLUMN_CART_PRODUCT_QUANTITY, cartDetails.getQuantity());
        values.put(COLUMN_CART_IMAGE, cartDetails.getImage());
        db.insert(TABLE_NAME_CARTLIST, null, values);
        db.close();

    }


    public List < CartPojo > getAllCart() {
        List < CartPojo > list = new ArrayList < > ();
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor res = db.rawQuery("select * from " + TABLE_NAME_CARTLIST + " where " + COLUMN_CART_PRODUCT_QUANTITY + " >= 1", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {

            list.add(new CartPojo(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4), res.getString(5)));

            res.moveToNext();
        }
        db.close();
        return list;
    }

    public int getGrandPrice() {
        int value;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(" + COLUMN_CART_TOTAL_PRICE + ") as Total FROM " + TABLE_NAME_CARTLIST + " where " + COLUMN_CART_PRODUCT_QUANTITY + " >= 1", null);
        res.moveToFirst();
        value = res.getInt(0);
        res.close();
        db.close();


        return value;
    }

    public List < Food > sortByPrice() {
        List < Food > list = new ArrayList < > ();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_PRODUCTLIST + " ORDER BY '" + COLUMN_PRICE + "' DESC", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            Food food = new Food(res.getDouble(3), res.getString(2), res.getString(1), res.getInt(4));
            list.add(food);
            res.moveToNext();
        }
        db.close();
        return list;
    }


    public List < Food > getAllProducts() {
        List < Food > list = new ArrayList < > ();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_PRODUCTLIST, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            Food food = new Food(res.getDouble(3), res.getString(2), res.getString(1), res.getInt(4));
            list.add(food);
            res.moveToNext();
        }
        db.close();
        return list;
    }

    public List < Food > sortByRating() {
        List < Food > list = new ArrayList < > ();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_PRODUCTLIST + " ORDER BY " + COLUMN_FEEDBACK + " DESC", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            Food food = new Food(res.getDouble(3), res.getString(2), res.getString(1), res.getInt(4));
            list.add(food);
            res.moveToNext();
        }
        db.close();
        return list;
    }

    public boolean find(String name) {
        int i = 0;

        //to get cart size
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select count(*) from " + TABLE_NAME_CARTLIST + " WHERE " + COLUMN_CART_NAME + " LIKE \"%" + name + "%\"", null);

        res.moveToFirst();

        while (!res.isAfterLast()) {

            i = res.getInt(0);


            res.moveToNext();
        }
        db.close();
        if (i == 0) {
            return false;
        } else {

            return true;
        }


    }

    public int getAllCartSize() {
        int size = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select count(*) from " + TABLE_NAME_CARTLIST + " where " + COLUMN_CART_PRODUCT_QUANTITY + " >= 1", null);
        res.moveToFirst();
        size = res.getInt(0);
        res.close();
        db.close();
        return size;
    }


    public boolean deletedata() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("delete from " + TABLE_NAME_PRODUCTLIST, null);
        res.moveToFirst();

        return true;
    }


    public int getCountSize(String productname) {

        int value = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + COLUMN_CART_PRODUCT_QUANTITY + " from " + TABLE_NAME_CARTLIST + " where " + COLUMN_NAME + " = " + "'" + productname + "'";

        Cursor res = db.rawQuery(query, null);


        res.moveToFirst();

        while (!res.isAfterLast()) {

            value = res.getInt(0);


            res.moveToNext();

        }
        res.close();


        db.close();
        return value;
    }


    public void UpdateQuantity(String itemName, int ii) {


        int value = 0;
        String valuee = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "update " + TABLE_NAME_CARTLIST + " SET " + COLUMN_CART_PRODUCT_QUANTITY + "  = " + "'" + ii + "'" + " where " + COLUMN_CART_NAME + " = " + "'" + itemName + "'";

        Cursor res = db.rawQuery(query, null);


        res.moveToFirst();

        while (!res.isAfterLast()) {

            value = res.getInt(0);


            res.moveToNext();

        }
        res.close();


        db.close();


    }

    public int TotalPricePerItem(String itemName) {


        int value = 0;
        String valuee = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_CART_TOTAL_PRICE + " FROM cartlist where " + COLUMN_CART_NAME + " ='" + itemName + "'";

        Cursor res = db.rawQuery(query, null);


        res.moveToFirst();

        value = res.getInt(0);

        res.close();


        db.close();

        return value;
    }

    public void UpdatePrice(String itemName, double price, int quantity) {

        int value = 0;
        String valuee = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "update " + TABLE_NAME_CARTLIST + " SET " + COLUMN_CART_TOTAL_PRICE + "  = " + "'" + price * quantity + "'" + " where " + COLUMN_CART_NAME + " = " + "'" + itemName + "'";

        Cursor res = db.rawQuery(query, null);


        res.moveToFirst();

        while (!res.isAfterLast()) {

            value = res.getInt(0);


            res.moveToNext();

        }
        res.close();


        db.close();


    }


}