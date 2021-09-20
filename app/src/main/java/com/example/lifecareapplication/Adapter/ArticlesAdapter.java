package com.example.lifecareapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifecareapplication.Activity.HomeActivity;
import com.example.lifecareapplication.Activity.ProductDetail;
import com.example.lifecareapplication.R;
import com.example.lifecareapplication.helper.QuestionsResponse;
import com.example.lifecareapplication.helper.RecyclerData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<QuestionsResponse.Product> data;
    private HomeActivity homeActivity;



    public ArticlesAdapter(HomeActivity context, HomeActivity homeActivity, ArrayList<QuestionsResponse.Product> AllArticles) {
        this.mContext = context;
        this.data = AllArticles;
        this.homeActivity = homeActivity;

    }

    @Override
    public ArticlesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        ArticlesAdapter.ViewHolder viewHolder = null;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.articles_list_items, parent, false);
            viewHolder = new ArticlesAdapter.ViewHolder(view);
            view.setTag(viewHolder);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesAdapter.ViewHolder holder, int position) {

        holder.txt_title.setText(data.get(position).getProductName());
        holder.txt_site.setText("Site Name ");

        String imageUri = "https://lambda.sqyrewards.com/captainFarm/"+data.get(position).getProductImg();

        Picasso.with(mContext).load(imageUri).into(holder.img);

        String date=data.get(position).getAddedon().substring(0,data.get(position).getAddedon().indexOf(" "));

        holder.txt_date.setText(date);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txt_title,txt_site,txt_date;

        public ImageView img,img_arrow;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_site = (TextView) itemView.findViewById(R.id.txt_site);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            img = (ImageView) itemView.findViewById(R.id.img);
            img_arrow = (ImageView) itemView.findViewById(R.id.img_arrow);

           // itemView.setOnClickListener(this);
            img_arrow.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            if(v==img_arrow)
            {
                Intent intent=new Intent(mContext, ProductDetail.class);
                intent.putExtra("id",data.get(itemPosition).getId());
                intent.putExtra("name",data.get(itemPosition).getProductName());
                intent.putExtra("image",data.get(itemPosition).getProductImg());
                mContext.startActivity(intent);
                ((Activity)mContext).finish();

            }

        }

    }

}
