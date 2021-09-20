package com.example.lifecareapplication.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.lifecareapplication.R;
import com.example.lifecareapplication.helper.DownloadFileFromURL;
import com.squareup.picasso.Picasso;

public class ProductDetail extends Activity implements View.OnClickListener {

    ImageView img_download,img;
    String product_id,product_name,product_img;

    TextView txt_title,txt_description;
    String imageUri;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Bundle bundle=getIntent().getExtras();
        product_id=bundle.getString("id");
        product_name=bundle.getString("name");
        product_img=bundle.getString("image");

        img_download=findViewById(R.id.img_download);
        img=findViewById(R.id.img);
        txt_title=findViewById(R.id.txt_title);
        txt_description=findViewById(R.id.txt_description);


         imageUri = "https://lambda.sqyrewards.com/captainFarm/"+product_img;

        Picasso.with(this).load(imageUri).into(img);

        Log.e("imageUri",imageUri+"");
        txt_title.setText(product_name);
        txt_description.setText("Sample Description");


        img_download.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {

        if (!checkPermission()) {
            requestPermission();
        } else {
            DownloadFileFromURL downloadFileFromURL = new DownloadFileFromURL(ProductDetail.this, product_name,imageUri);
            downloadFileFromURL.execute(imageUri);
        }

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 200);
    }
    private boolean checkPermission() {

        //read write storage permission is used for catlogue downloading

        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return result1 == PackageManager.PERMISSION_GRANTED
                && result2 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 200:
                if (grantResults.length > 0) {
                    boolean read_external_storage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean write_external_storage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (read_external_storage && write_external_storage) {
                        DownloadFileFromURL downloadFileFromURL = new DownloadFileFromURL(ProductDetail.this, product_name,imageUri);
                        downloadFileFromURL.execute(imageUri);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) || shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                ActivityCompat.requestPermissions(ProductDetail.this,
                                        new String[]{
                                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        },
                                        200);
                            } else {

                                alertDialog = new android.app.AlertDialog.Builder(ProductDetail.this)
                                        .setTitle(R.string.app_name)
                                        .setMessage("Please enable permission.")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                                intent.setData(uri);
                                                startActivityForResult(intent, 200);
                                                dialog.dismiss();
                                            }
                                        }).show();
                            }
                        }
                    }
                }
                break;
        }
    }

}