package com.example.lifecareapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifecareapplication.R;

public class OTPActivity extends Activity implements View.OnClickListener {

    EditText edt_otp;
    TextView btn_submit;
    String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        edt_otp=findViewById(R.id.edt_otp);
        btn_submit=findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view==btn_submit)
        {
            boolean flag = true;

            otp=edt_otp.getText().toString().trim();
            if(otp.equals(""))
            {
                flag = false;
                edt_otp.requestFocus();
                edt_otp.setError(getString(R.string.otp_enter));

            }
            else if(!otp.equals("1234"))
            {
                flag = false;
                edt_otp.requestFocus();
                edt_otp.setError(getString(R.string.otp_incorrect));

            }
            else if(otp.equals("1234"))
            {
                Toast.makeText(this,"registration is successfully completed !! ",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();

            }

        }
    }
}