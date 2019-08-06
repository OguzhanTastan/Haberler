package com.example.haberler.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.haberler.R;
import com.example.haberler.detail.DetailActivity;
import com.example.haberler.service.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsPagerAdapter extends PagerAdapter {


    public List<NewsModel> items;

    public NewsPagerAdapter(List<NewsModel> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View root = LayoutInflater.from(container.getContext()).inflate(R.layout.template_news, container, false);

        final NewsModel item = items.get(position);

        TextView tvTitle = root.findViewById(R.id.tvTitle);
        tvTitle.setText(item.Title);

        ImageView ivNews = root.findViewById(R.id.ivNews);

        Picasso.get()
                .load(item.Files.get(0).FileUrl)
                .placeholder(R.drawable.ic_photo)
                .into(ivNews);

        container.addView(root);
        ivNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.ITEM, item);
                view.getContext().startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}