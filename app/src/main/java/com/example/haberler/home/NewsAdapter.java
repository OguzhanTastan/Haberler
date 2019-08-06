package com.example.haberler.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.haberler.AppUtil;
import com.example.haberler.detail.DetailActivity;
import com.example.haberler.R;
import com.example.haberler.service.CategoryType;
import com.example.haberler.service.NewsModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    public List<NewsModel> items;

    public NewsAdapter(List<NewsModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news, parent, false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final NewsModel item = items.get(position);

        Picasso.get()
                .load(item.Files.get(0).FileUrl)
                .placeholder(R.drawable.ic_photo)
                .into(holder.ivNews);


        String date = "" + item.CreatedDate;
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("d MMM , hh:mm");
        date = spf.format(newDate);


        holder.tvDate.setText(date);
        holder.tvTitle.setText(item.Title);
        holder.tvDesc.setText(item.Description);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.ITEM, item);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivNews;
        public TextView tvDate;
        public TextView tvTitle;
        public TextView tvDesc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNews = itemView.findViewById(R.id.ivNews);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);

            tvDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvDate.getTextSize() * AppUtil.getTextSize());
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvTitle.getTextSize() * AppUtil.getTextSize());
            tvDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvDesc.getTextSize() * AppUtil.getTextSize());


        }


    }


}
