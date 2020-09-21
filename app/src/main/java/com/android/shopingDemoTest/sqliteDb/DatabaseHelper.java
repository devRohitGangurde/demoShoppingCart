package com.android.shopingDemoTest.sqliteDb;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.android.shopingDemoTest.model.ProductModel;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "demoShopping.db";
    public static final String TABLE_NAME = "addToCart";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "PRODUCT_NAME";
    public static final String COL_3 = "PRICE";
    public static final String COL_4 = "VENDOR_NAME";
    public static final String COL_5 = "VENDOR_ADDRESS";
    public static final String COL_6 = "PHONE_NUMBER";
    public static final String COL_7 = "IS_ADDED";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,PRODUCT_NAME TEXT,PRICE TEXT,VENDOR_NAME TEXT,VENDOR_ADDRESS TEXT,PHONE_NUMBER TEXT,IS_ADDED TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(ProductModel productModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,productModel.getProductname());
        contentValues.put(COL_3,productModel.getPrice());
        contentValues.put(COL_4,productModel.getVendorname());
        contentValues.put(COL_5,productModel.getVendoraddress());
        contentValues.put(COL_6,productModel.getPhoneNumber());
        contentValues.put(COL_7,productModel.getIsAdded());
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<ProductModel> getProductsList(){
        SQLiteDatabase db=this.getWritableDatabase();
        String mflag="0" ;
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" where IS_ADDED ="+mflag,null);
        ArrayList<ProductModel> productModelArrayList=new ArrayList<>();

        while(cursor.moveToNext()){
            ProductModel productModel=new ProductModel();
            productModel.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
            productModel.setProductname(cursor.getString(cursor.getColumnIndex(COL_2)));
            productModel.setPrice(cursor.getString(cursor.getColumnIndex(COL_3)));
            productModel.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COL_6)));
            productModel.setVendorname(cursor.getString(cursor.getColumnIndex(COL_4)));
            productModel.setVendoraddress(cursor.getString(cursor.getColumnIndex(COL_5)));
            productModel.setIsAdded(cursor.getString(cursor.getColumnIndex(COL_7)));
            productModelArrayList.add(productModel);
        }
        return productModelArrayList;
    }

    public ArrayList<ProductModel> getCartProducts(){
        SQLiteDatabase db=this.getWritableDatabase();
        String mflag="1" ;
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" where IS_ADDED ="+mflag,null);
        ArrayList<ProductModel> productModelArrayList=new ArrayList<>();

        while(cursor.moveToNext()){
            ProductModel productModel=new ProductModel();
            productModel.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
            productModel.setProductname(cursor.getString(cursor.getColumnIndex(COL_2)));
            productModel.setPrice(cursor.getString(cursor.getColumnIndex(COL_3)));
            productModel.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COL_6)));
            productModel.setVendorname(cursor.getString(cursor.getColumnIndex(COL_4)));
            productModel.setVendoraddress(cursor.getString(cursor.getColumnIndex(COL_5)));
            productModel.setIsAdded(cursor.getString(cursor.getColumnIndex(COL_7)));
            productModelArrayList.add(productModel);
        }
        return productModelArrayList;
    }



    public boolean updateData(String id,String mFlag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_7,mFlag);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData () {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, null,null);

    }
}