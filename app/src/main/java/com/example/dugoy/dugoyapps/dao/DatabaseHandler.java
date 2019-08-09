package com.example.dugoy.dugoyapps.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dugoy.dugoyapps.model.Martabak;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION=1;

    private static final String DATABASE_NAME="ContactManager";

    private static final String TABLE_NAME="contact";
    private static final String KEY_ID="id" ;
    private static final String KEY_NAME="nama_martabak" ;
    private static final String KEY_TEL="tel" ;
    private static final String KEY_TOPPING="toppings" ;
    private static final String KEY_TYPE="type" ;
    private static final String KEY_SUBTOTAL="sub" ;
    private static final String KEY_TOTAL="total" ;

    private final static String CREATE_TABLE= "CREATE TABLE "+TABLE_NAME+
            " ("+KEY_ID+" integer primary key autoincrement," +
            KEY_NAME +" text not null, "+
            KEY_TOPPING +" text, "+
            KEY_TYPE +" text, "+
            KEY_SUBTOTAL +" double, "+
            KEY_TOTAL +" double, "+
            KEY_TEL+" text not null)";

    private SQLiteDatabase myDB;

    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("drop table " + DATABASE_NAME);
            this.onCreate(db);
        }
    }

    public SQLiteDatabase openDB() {
        myDB = this.getWritableDatabase();
        return myDB;
    }

    public void closeDB() { myDB.close(); }

    public long addContact(Martabak martabak) {
        myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, martabak.getName());
        contentValues.put(KEY_TEL, "kosong");
        contentValues.put(KEY_TYPE, martabak.getType());
        contentValues.put(KEY_TOPPING, martabak.getToppings());
        contentValues.put(KEY_SUBTOTAL, martabak.getSubtotal());
        return myDB.insert(TABLE_NAME, null, contentValues);
    }

    public List<Martabak> getAllMartabak() {
        List<Martabak> martabakList = new ArrayList<>();
        myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM "+TABLE_NAME, null);

        if(cursor.getCount() == 0) return new ArrayList<>(0);
        cursor.moveToFirst();

            while(cursor.moveToNext()) {
                Martabak martabak = new Martabak();
                martabak.setId(Integer.parseInt(cursor.getString(0)));
                martabak.setName(cursor.getString(1));
                martabak.setToppings(cursor.getString(2));
                martabak.setType(cursor.getString(3));
                martabak.setSubtotal(cursor.getDouble(4));
                martabakList.add(martabak);
            }

        cursor.close();
        return martabakList;
    }

    public void updateContact(Martabak martabak) {
        myDB = this.getWritableDatabase();
        String sql = "UPDATE "+TABLE_NAME+" SET "+KEY_TOPPING +" = ?," +
                " "+KEY_NAME +" = ?," +
                " "+KEY_TYPE +" = ?," +
                " "+KEY_SUBTOTAL +" = ?" +
                " WHERE " +KEY_ID +" = ?";
        Cursor cursor = myDB.rawQuery(sql,
                new String[]{martabak.getToppings(), martabak.getName(), martabak.getType(), martabak.getSubtotal().toString(), martabak.getStringId()});
        cursor.moveToFirst();
    }

    public void deleteContact(int id) {
        String ids = String.valueOf(id);
        myDB = this.getWritableDatabase();
        String sql = "DELETE FROM "+TABLE_NAME+" WHERE "+KEY_ID + " = ?";
        Cursor cursor = myDB.rawQuery(sql, new String[]{ids});
        cursor.moveToFirst();
        cursor.close();
    }
}
