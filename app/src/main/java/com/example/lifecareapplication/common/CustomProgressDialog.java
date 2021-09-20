package com.example.lifecareapplication.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.example.lifecareapplication.R;

public class CustomProgressDialog extends Dialog {

    private String mMessage;

    public CustomProgressDialog(Context context, int theme, String mesg) {
        super(context, theme);
        mMessage = mesg;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.custom_progress_dialog);


        ((TextView) findViewById(R.id.progress_mesg)).setText(mMessage);

        setCancelable(false);
    }

    public void setMessage(String msg) {
        ((TextView) findViewById(R.id.progress_mesg)).setText(msg);
    }


}
