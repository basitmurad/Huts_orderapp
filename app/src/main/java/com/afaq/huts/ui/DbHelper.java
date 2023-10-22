package com.afaq.huts.ui;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.afaq.huts.model.DishDetail;
import com.afaq.huts.model.OrderDetails;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "DishDetail";
    private static final String COLUMN_NAME = "dishName";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_NEW_QUANTITY = "new_quantity";
    private static final String COLUMN_NEW_PRICE = "new_price";
    private static final String COLUMN_HUT_NAME = "hutName"; // Add the hutName column


//    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" +
//            COLUMN_NAME + " TEXT NOT NULL PRIMARY KEY, " +
//            COLUMN_PRICE + " INTEGER, " +
//            COLUMN_QUANTITY + " INTEGER, " +
//            COLUMN_NEW_PRICE + " INTEGER, " + // Add the new_price column
//            COLUMN_NEW_QUANTITY + " INTEGER, " + // Add the new_quantity column
//            COLUMN_IMAGE + " BLOB);";
private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" +
        COLUMN_NAME + " TEXT NOT NULL PRIMARY KEY, " +
        COLUMN_PRICE + " INTEGER, " +
        COLUMN_QUANTITY + " INTEGER, " +
        COLUMN_NEW_PRICE + " INTEGER, " +
        COLUMN_NEW_QUANTITY + " INTEGER, " +
        COLUMN_IMAGE + " BLOB, " +
        COLUMN_HUT_NAME + " TEXT);";

    public DbHelper(@Nullable Context context   ) {
        super(context, "user.db", null, 2);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public long insertDish(String hutName, String name, int price, int quantity, byte[] imageByteArray, int newPrice, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HUT_NAME, hutName); // Insert the hutName
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_IMAGE, imageByteArray);
        values.put(COLUMN_NEW_PRICE, newPrice);
        values.put(COLUMN_NEW_QUANTITY, newQuantity);
        return db.insert(TABLE_NAME, null, values);
    }


//    public long insertDish(String name, int price,int quantity , byte[] imageByteArray, int newPrice , int newQuantity) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NAME, name);
//        values.put(COLUMN_PRICE, price);
//        values.put(COLUMN_QUANTITY, quantity);
//        values.put(COLUMN_IMAGE, imageByteArray);
//        values.put(COLUMN_NEW_PRICE, newPrice);
//        values.put(COLUMN_NEW_QUANTITY,newQuantity);
//        return db.insert(TABLE_NAME, null, values);
//    }
//

    public void updateDishQuantityAndPrice(String name, int newQuantity, int newPrice) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NEW_QUANTITY, newQuantity);
        values.put(COLUMN_NEW_PRICE, newPrice);

        db.update(TABLE_NAME, values, COLUMN_NAME + "=?", new String[]{name});
        db.close();
    }

    public void deleteDish(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_NAME + "=?", new String[]{name});
        db.close();
    }



    public ArrayList<DishDetail> getAllDishes() {
        ArrayList<DishDetail> dishList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String hutName = cursor.getString(cursor.getColumnIndex(COLUMN_HUT_NAME));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range") int price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE));
                @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
                @SuppressLint("Range") byte[] imageByteArray = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));

                DishDetail dish = new DishDetail(hutName ,name, price, quantity, imageByteArray);
                dishList.add(dish);
            }
            cursor.close();
        }

        return dishList;
    }


    public ArrayList<OrderDetails> getAll() {
        ArrayList<OrderDetails> orderDetailsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                COLUMN_NAME,
                COLUMN_NEW_PRICE,
                COLUMN_NEW_QUANTITY,
                COLUMN_IMAGE
        };

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range") int price = cursor.getInt(cursor.getColumnIndex(COLUMN_NEW_PRICE));
                @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_NEW_QUANTITY));


//                @SuppressLint("Range") byte[] imageUriByteArray = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));

//                String imageUri = new String(imageUriByteArray);


                OrderDetails dish = new OrderDetails(name, price, quantity);
                orderDetailsList.add(dish);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return orderDetailsList;
    }

    public void deleteAllOrders() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }


    public double calculateTotalNewPrice() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(" + COLUMN_NEW_PRICE + ") FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        double totalNewPrice = 0.0;

        if (cursor.moveToFirst()) {
            totalNewPrice = cursor.getDouble(0);
        }

        cursor.close();
        return totalNewPrice;
    }






}
