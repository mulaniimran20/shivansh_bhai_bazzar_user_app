package com.aarfaatechnovision.kjl.shivanshbhajibazar.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.cart.CartlistModel;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.model.product.Product;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.ID;

public class DBUtils {

    private static final String DB_NAME = "db_vegatbles";

    private SQLiteDatabase mDb;

    private static DBUtils dbUtils = null;
    Context c;

    DBUtils(Context context) {

        mDb = new ProductsHelper(context)
                .getWritableDatabase();
        c = context;
    }

    public static DBUtils getInstance(Context context) {

        if (dbUtils == null) {
            dbUtils = new DBUtils(context);
        }

        return dbUtils;
    }
    public int count(int ID){

        Cursor cursor = mDb.query("productv",
                null,
                "productId = ?", new String[] {String.valueOf(ID)},
                null,
                null,
                null,
                null);

        int countNo = cursor.getCount();

        if (countNo >= 0) {

            Log.e("tag", " Count= " + countNo);
            return countNo;

        } else {
            Log.e("tag", " Count= " + countNo);
            return countNo;
        }

    }


 public boolean updateProduct(int productId,int productQuantity) {

        ContentValues values = new ContentValues();
        values.put("productId", productId);
        values.put("productQuantity", productQuantity);

        Log.e("tag", " updated:  " + " ID: " + productId + " Qty: " + productQuantity );

        int count =
                mDb.update("productv",
                        values,
                        "ID = ?",
                        new String[]{String.valueOf(ID)}
                );

        if (count >= 0) {
            return true;
        } else {
            return false;
        }

    }


    public boolean deleteProduct(int ID) {

        int count = mDb.delete("productv",
                "ID = ?",
                new String[]{String.valueOf(ID)}
        );

        Log.e("tag", " deleted " + ID);

        if (count >= 0) {
            System.out.println("here");
            return true;
        } else {
            return false;
        }
    }

    public Cursor getCursor() {

        return mDb.query("productv",
                null, null, null, null, null,
                null);

    }



    public void close() {
        if (dbUtils != null) {
            dbUtils.close();
        }
    }

}