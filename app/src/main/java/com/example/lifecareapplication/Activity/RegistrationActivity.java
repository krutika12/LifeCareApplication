package com.example.lifecareapplication.Activity;

import androidx.annotation.RequiresApi;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lifecareapplication.DBHelper.DbHelper;
import com.example.lifecareapplication.R;
import com.example.lifecareapplication.common.CommonUtility;

public class RegistrationActivity extends Activity implements View.OnClickListener {

    EditText f_name,l_name,phone_no,e_id_name,edt_password;
    TextView btn_register,sign_in;
    String fname,lname,phone,mail_id,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        f_name=findViewById(R.id.f_name);
        l_name=findViewById(R.id.l_name);
        phone_no=findViewById(R.id.phone_no);
        e_id_name=findViewById(R.id.e_id_name);
        edt_password=findViewById(R.id.edt_password);
        btn_register=findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        sign_in=findViewById(R.id.sign_in);
        sign_in.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onClick(View view) {
        if(view==btn_register)
        {
            boolean flag = true;

            fname=f_name.getText().toString().trim();
            lname=l_name.getText().toString().trim();
            phone=phone_no.getText().toString().trim();
            mail_id=e_id_name.getText().toString().trim();
            password=edt_password.getText().toString().trim();

            if(fname.equals(""))
            {
                flag = false;
                f_name.requestFocus();
                f_name.setError(getString(R.string.f_name));

            }
            if(lname.equals(""))
            {
                flag = false;
                l_name.requestFocus();
                l_name.setError(getString(R.string.l_name));

            }
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
            else if (!isEmailValid(mail_id)){
                flag = false;
                e_id_name.requestFocus();
                e_id_name.setError(getString(R.string.invalidemail));
                // valid_email = null;
            }
            if (flag) {

                savedata();

            } else {
                return;
            }

        }

        if(view==sign_in)
        {
            Intent intent=new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public  void savedata()
    {
        CommonUtility.showCustomProgressDialog(this, "", getString(R.string.please_wait));
        DbHelper mDbHelper = new DbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put("cfname",fname);
        values.put("clname",lname);
        values.put("cphone",phone);
        values.put("cmail",mail_id);
        values.put("pass",password);

        long newRowId = db.insert("CUSTOMER",null,values);

        Log.e("newRowId",newRowId+"");
        CommonUtility.cancelProgressDialog();

        Intent intent=new Intent(this, OTPActivity.class);
        startActivity(intent);
        finish();
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }

}