package com.example.lifecareapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lifecareapplication.DBHelper.DbHelper;
import com.example.lifecareapplication.Model.CustModel;
import com.example.lifecareapplication.R;

import java.util.ArrayList;

public class LoginActivity extends
        Activity implements View.OnClickListener {

    EditText edt_password,phone_no;
    TextView sign_up,btn_login;

    String password,phone;
    DbHelper database_helper;
    ArrayList<CustModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_password=findViewById(R.id.edt_password);
        phone_no=findViewById(R.id.phone_no);

        sign_up=findViewById(R.id.sign_up);
        sign_up.setOnClickListener(this);

        btn_login=findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view==btn_login)
        {
            boolean flag = true;

            phone=phone_no.getText().toString().trim();
            password=edt_password.getText().toString().trim();
            if(phone.equals(""))
            {
                flag = false;
                phone_no.requestFocus();
                phone_no.setError(getString(R.string.phone_no));
            }
            else if(phone.length()<10)
            {
                flag = false;
                phone_no.requestFocus();
                phone_no.setError(getString(R.string.phone_valid_no));
            }
            if(password.equals(""))
            {
                flag = false;
                edt_password.requestFocus();
                edt_password.setError(getString(R.string.enter_pass));
            }

            if (flag) {
                LoginFunction();


            } else {
                return;
            }
        }
        else if(view==sign_up)
        {
            Intent intent=new Intent(this, RegistrationActivity.class);
            startActivity(intent);
            finish();
        }
    }


    public void LoginFunction()
    {
        DbHelper mDbHelper = new DbHelper(this);
        String[] columns = {
                "cphone","pass"
        };
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
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
            Intent intent=new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(this,"wrong credential",Toast.LENGTH_SHORT).show();
            return;
        }

    }

}