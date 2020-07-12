package com.aarfaatechnovision.kjl.shivanshbhajibazar.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.cart.CartlistModel;

import java.util.ArrayList;
import java.util.List;

public class ProductsHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "db_vegatbles";



    public ProductsHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table productv( ID integer primary key Autoincrement " +
                ",productName text," + " productPrice text, " +
                "" + "productQuantity integer,"+"productImages text,"+ "productId )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }


    public List<CartlistModel> getAllNotes() {
        List<CartlistModel> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM productv";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CartlistModel note = new CartlistModel();
                note.setProductId(cursor.getInt(cursor.getColumnIndex("productId")));
                note.setProductImages(cursor.getString(cursor.getColumnIndex("productImages")));
                note.setProductPrice(cursor.getString(cursor.getColumnIndex("productPrice")));
                note.setProductName(cursor.getString(cursor.getColumnIndex("productName")));
                note.setProductQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex("productQuantity"))));
                note.setProductDbID(Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID"))));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }





    public boolean addProduct(String productName, String productPrice, int productQuantity
            , String productImages, int productId) {

        SQLiteDatabase db = this.getReadableDatabase();


        String selectquery  = "SELECT * FROM productv WHERE productId = "+productId;

        Cursor cursor = db.rawQuery(selectquery, null);

        System.out.println(cursor.getCount());

        int totalCurrent  = cursor.getCount();

        if(totalCurrent == 0) {
            ContentValues values = new ContentValues();
            values.put("productName", productName);
            values.put("productPrice", productPrice);
            values.put("productQuantity", productQuantity);
            values.put("productImages", productImages);
            values.put("productId", productId);

            Log.e("tag", "Added: " + " " + productName + " "
                    + productPrice + " " + productQuantity + " " + productImages);

            long rowNum = db.insert("productv", null, values);

            if (rowNum >= 0) {
                return true;
            } else {
                return false;
            }
        }
        else{



            CartlistModel cartlistModel = new CartlistModel();

            if (cursor.moveToFirst()) {

                cartlistModel.setProductQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex("productQuantity"))));
                cartlistModel.setProductImages(productImages);
                cartlistModel.setProductId(productId);
                cartlistModel.setProductPrice(productPrice);
                cartlistModel.setProductName(productName);
                cartlistModel.setProductDbID(Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID"))));

                updateNote(cartlistModel);

                return true;

            }
            else{
                return false;
            }

        }
    }

    public int updateNote(CartlistModel note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("productQuantity", note.getProductQuantity());
        values.put("productPrice", note.getProductPrice());

        // updating row
        return db.update("productv", values, "ID" + " = ?",
                new String[]{String.valueOf(note.getProductDbID())});
    }


}