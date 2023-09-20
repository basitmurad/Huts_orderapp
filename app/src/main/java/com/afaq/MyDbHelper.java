package com.afaq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class MyDbHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME1 = "OrderDetail";
    private static final String COLUMN_NAME1 = "OrderdishName";
    private static final String COLUMN_PRICE1 = "Orderdishprice";
    private static final String COLUMN_QUANTITY1 = "Orderdishquantity";
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME1 + " (" +
            COLUMN_NAME1 + " TEXT NOT NULL PRIMARY KEY, " +
            COLUMN_PRICE1 + " INTEGER, " +
            COLUMN_QUANTITY1 + " INTEGER )";
    public MyDbHelper(@Nullable Context context) {
        super(context, "order.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);
    }

    public long insertDish(String name, int price, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME1, name);
        values.put(COLUMN_PRICE1, price);
        values.put(COLUMN_QUANTITY1, quantity);

        return db.insert(TABLE_NAME1, null, values);
    }

}
