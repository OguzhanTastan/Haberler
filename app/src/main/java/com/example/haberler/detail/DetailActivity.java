package com.example.haberler.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.haberler.AppLogger;
import com.example.haberler.BaseActivity;
import com.example.haberler.R;
import com.example.haberler.service.CategoryType;
import com.example.haberler.service.HurriyetNewsApi;
import com.example.haberler.service.NewsDetailModel;
import com.example.haberler.service.NewsModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailActivity extends BaseActivity {

    public static final String ITEM = "item";

    public NewsModel item;

    public ImageView ivdNews;
    public TextView tvdDate;
    public TextView tvdTitle;
    public TextView tvdContent;
    public TextView tvdTags;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        item = (NewsModel) getIntent().getSerializableExtra(ITEM);

        ivdNews = findViewById(R.id.ivdNews);
        tvdTitle = findViewById(R.id.tvdTitle);
        tvdContent = findViewById(R.id.tvdContent);
        tvdDate = findViewById(R.id.tvdDate);
        tvdTags = findViewById(R.id.tvdTags);


        getSupportActionBar().setTitle(R.string.detailScreenTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendGetRequest();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        sendGetRequest();
        changeTextView();


    }

    public void changeTextView() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String text_size = sharedPreferences.getString("text_size", "");
        String text_color = sharedPreferences.getString("text_color", "");

        if (text_size.equals("Small")) {
            changeBaseDetailTextSize(tvdTitle, CategoryType.SMALL);
            changeBaseDetailTextSize(tvdContent, CategoryType.SMALL);
            changeBaseDetailTextSize(tvdDate, CategoryType.SMALL);
            changeBaseDetailTextSize(tvdTags, CategoryType.SMALL);
        }
        if (text_size.equals("Medium")) {
            changeBaseDetailTextSize(tvdTitle, CategoryType.MEDIUM);
            changeBaseDetailTextSize(tvdContent, CategoryType.MEDIUM);
            changeBaseDetailTextSize(tvdDate, CategoryType.MEDIUM);
            changeBaseDetailTextSize(tvdTags, CategoryType.MEDIUM);
        }
        if (text_size.equals("Large")) {
            changeBaseDetailTextSize(tvdTitle, CategoryType.LARGE);
            changeBaseDetailTextSize(tvdContent, CategoryType.LARGE);
            changeBaseDetailTextSize(tvdDate, CategoryType.LARGE);
            changeBaseDetailTextSize(tvdTags, CategoryType.LARGE);
        }
        if (text_color.equals("Default")) {
            changeBaseDetailColor(tvdTitle, CategoryType.Default);
            changeBaseDetailColor(tvdContent, CategoryType.Default);
            changeBaseDetailColor(tvdDate, CategoryType.Default);
            changeBaseDetailColor(tvdTags, CategoryType.Default);
        }
        if (text_color.equals("User1")) {
            changeBaseDetailColor(tvdTitle, CategoryType.USER1);
            changeBaseDetailColor(tvdContent, CategoryType.USER1);
            changeBaseDetailColor(tvdDate, CategoryType.USER1);
            changeBaseDetailColor(tvdTags, CategoryType.USER1);
        }
        if (text_color.equals("User2")) {
            changeBaseDetailColor(tvdTitle, CategoryType.USER2);
            changeBaseDetailColor(tvdContent, CategoryType.USER2);
            changeBaseDetailColor(tvdDate, CategoryType.USER2);
            changeBaseDetailColor(tvdTags, CategoryType.USER2);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.mnShare) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, item.Title);
            intent.putExtra(Intent.EXTRA_TEXT, "Bu habere mutlaka bakmalısın: " + item.Url);
            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.mnOpenInBrowser) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(item.Url)));
        } else if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(menuItem);
    }

    private void sendGetRequest() {
        showLoading();
        HurriyetNewsApi api = new HurriyetNewsApi();
        api.getNewsDetail(item.Id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                AppLogger.err(e);
                new AlertDialog.Builder(activity)
                        .setTitle(R.string.errorTitle)
                        .setMessage(R.string.errorDesc)
                        .setCancelable(false)
                        .setPositiveButton(R.string.tryAgain, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sendGetRequest();
                            }
                        })
                        .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                final NewsDetailModel detail = gson.fromJson(response.body().string(), NewsDetailModel.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissLoading();
                        bindData(detail);
                    }
                });
            }
        });

    }

    private void bindData(final NewsDetailModel detail) {
        Picasso.get()
                .load(item.Files.get(0).FileUrl)
                .placeholder(R.drawable.ic_photo)
                .into(ivdNews);


        String date = "" + item.CreatedDate;
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("dd/MM/yyyy , hh:mm");
        date = spf.format(newDate);


        tvdTitle.setText(item.Title);
        tvdDate.setText(date);
        tvdContent.setText(HtmlCompat.fromHtml(detail.Text, HtmlCompat.FROM_HTML_MODE_COMPACT));
        tvdTags.setText(TextUtils.join(",", detail.Tags));

    }

}
