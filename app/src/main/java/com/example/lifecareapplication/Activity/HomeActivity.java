package com.example.lifecareapplication.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lifecareapplication.Adapter.ArticlesAdapter;
import com.example.lifecareapplication.R;
import com.example.lifecareapplication.common.CommonUtility;
import com.example.lifecareapplication.helper.Api;
import com.example.lifecareapplication.helper.QuestionsResponse;
import com.example.lifecareapplication.helper.RecyclerData;
import com.example.lifecareapplication.helper.fpNetworkHelper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends Activity {

    RecyclerView list;
    fpNetworkHelper apiInterface;
    ArticlesAdapter mAdapter;
    LinearLayoutManager layoutManager;
    ArrayList<RecyclerData> recyclerDataArrayList;



    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        apiInterface = Api.getRetrofitInstance().create(fpNetworkHelper.class);
        list=findViewById(R.id.list);

        recyclerDataArrayList = new ArrayList<>();

        //getAllArticles();
        getProducts();

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void getAllArticles() {
        try {
            if (CommonUtility.isInternetAvailable(this)) {
                CommonUtility.showCustomProgressDialog(this, "", getString(R.string.please_wait));

                Call<ArrayList<RecyclerData>> questionsResponseCall = apiInterface.getarticles();

                questionsResponseCall.enqueue(new Callback<ArrayList<RecyclerData>>() {
                    @Override
                    public void onResponse(Call<ArrayList<RecyclerData>> call, Response<ArrayList<RecyclerData>> response) {
                        Log.e("response","response.body()"+"");
                        Log.e("response",response.body()+"");
                        Log.e("response6 Notification",new Gson().toJson(response.body())+"");
                        Log.e("1",recyclerDataArrayList.size()+"");


                        if(response!=null && response.isSuccessful())
                        {

                            if (recyclerDataArrayList != null) {

                                CommonUtility.cancelProgressDialog();
//                                mAdapter = new ArticlesAdapter(HomeActivity.this, HomeActivity.this, recyclerDataArrayList);
//
//                                layoutManager = new LinearLayoutManager(HomeActivity.this);
//                                list.setLayoutManager(layoutManager);
//                                list.setAdapter(mAdapter);

                            }
                            else
                            {
                                CommonUtility.cancelProgressDialog();
                                Toast.makeText(HomeActivity.this,"There is no data",Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            CommonUtility.cancelProgressDialog();
                            Toast.makeText(HomeActivity.this,"There seems some issue in the system, Please Try After Some Time",Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<RecyclerData>> call, Throwable t) {
                        CommonUtility.cancelProgressDialog();
                        Toast.makeText(HomeActivity.this,"There seems some issue in the system, Please Try After Some Time",Toast.LENGTH_SHORT).show();
                        Log.e("tag3", t.toString());
                    }
                });

            } else {
                Toast.makeText(HomeActivity.this,"Please check internet connection",Toast.LENGTH_SHORT).show();

            }
        } catch (Exception ex) {
            Log.e("tag4", ex.toString());
            CommonUtility.cancelProgressDialog();
            Toast.makeText(HomeActivity.this,"There seems some issue in the system, Please Try After Some Time",Toast.LENGTH_SHORT).show();
            ex.printStackTrace();

        }
    }



    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void getProducts() {
        try {
            if (CommonUtility.isInternetAvailable(this)) {
                CommonUtility.showCustomProgressDialog(this, "", getString(R.string.please_wait));


                Call<QuestionsResponse> questionsResponseCall = apiInterface.getProducts();

                questionsResponseCall.enqueue(new Callback<QuestionsResponse>() {
                    @Override
                    public void onResponse(Call<QuestionsResponse> call, final Response<QuestionsResponse> response) {
                        Log.e("get product",new Gson().toJson(response.body())+"");


                        if(response!=null && response.isSuccessful())
                        {

                            if (response.body().getStatus().equals("true")) {

                                CommonUtility.cancelProgressDialog();


                                for(int i=0;i<response.body().getProducts().size();i++)
                                {

                                    mAdapter = new ArticlesAdapter(HomeActivity.this, HomeActivity.this, response.body().getProducts());

                                    layoutManager = new LinearLayoutManager(HomeActivity.this);
                                    list.setLayoutManager(layoutManager);
                                    list.setAdapter(mAdapter);


                                }


                            }
                            else
                            {
                                CommonUtility.cancelProgressDialog();
                                Toast.makeText(HomeActivity.this,"There is no data",Toast.LENGTH_SHORT).show();

                            }
                        }
                        else{
                            CommonUtility.cancelProgressDialog();
                            Toast.makeText(HomeActivity.this,"There is no data",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<QuestionsResponse> call, Throwable t) {
                        CommonUtility.cancelProgressDialog();
                        Toast.makeText(HomeActivity.this,"There seems some issue in the system, Please Try After Some Time",Toast.LENGTH_SHORT).show();
//                        Log.e("tag3", t.toString());
                    }
                });

            } else {
                Toast.makeText(HomeActivity.this,"Please check internet connection",Toast.LENGTH_SHORT).show();

            }
        } catch (Exception ex) {
//            Log.e("tag4", ex.toString());
            CommonUtility.cancelProgressDialog();
            Toast.makeText(HomeActivity.this,"There seems some issue in the system, Please Try After Some Time",Toast.LENGTH_SHORT).show();
            ex.printStackTrace();

        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yesd), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HomeActivity.this.finish();
                            finish();
                        }
                    })
                    .setNegativeButton(getString(R.string.nod), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

    }



}