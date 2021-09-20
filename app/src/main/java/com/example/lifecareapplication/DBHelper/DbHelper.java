package com.example.lifecareapplication.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lifecareapplication.Model.CustModel;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "lifecare.db";

    private static final String SQL_CREATE_STUD =
            "CREATE TABLE CUSTOMER (cno INTEGER PRIMARY KEY, cfname TEXT,clname TEXT,cmail TEXT, cphone TEXT,pass TEXT)";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_STUD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_CREATE_STUD);
    }

    public ArrayList<CustModel> getData() {
        ArrayList<CustModel> arrayList = new ArrayList<>();

        // select all query
        String select_query= "SELECT *FROM " + "CUSTOMER";

        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CustModel custModel = new CustModel();
                custModel.setId(cursor.getString(0));
                custModel.setName(cursor.getString(1));
                custModel.setAdd(cursor.getString(2));
                custModel.setPhone(cursor.getString(3));
                custModel.setPhone(cursor.getString(4));
                arrayList.add(custModel);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }

    public boolean checkUser(String phone, String password) {
        // array of columns to fetch
        Log.e("111111111",phone+"");
        Log.e("222222222",password+"");

        String[] columns = {
                "cphone","pass"
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = "cphone" + " = ?" + " AND " + "pass" + " = ?";
        // selection arguments
        String[] selectionArgs = {phone, password};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query("CUSTOMER", //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
}

