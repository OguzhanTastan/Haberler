package com.example.haberler.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.haberler.AppLogger;
import com.example.haberler.BaseActivity;
import com.example.haberler.BuildConfig;
import com.example.haberler.R;
import com.example.haberler.SettingsActivity;
import com.example.haberler.service.CategoryType;
import com.example.haberler.service.HurriyetNewsApi;
import com.example.haberler.service.NewsModel;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rd.PageIndicatorView;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeActivity extends BaseActivity {

    private final String PREFS_NAME = "MyprefsFile";
    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";
    private static final int NOTIFICATION_ID = 101;
    public static final String MARKET_URL = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
    public static final String ABOUT_MESSAGE = "Hürriyet api kullanılarak geliştirilmiştir\n\n" +
            "Deveoped by @oguzhan\n\n" +
            "Version:" + BuildConfig.VERSION_NAME + " - " + BuildConfig.VERSION_CODE;

    private CategoryType currentCategory = CategoryType.ALL;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private PageIndicatorView pageIndicatorView;
    private RecyclerView rvItems;
    private ViewPager viewPager;
    private SwipeRefreshLayout swipeRefreshLayout;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference news = database.getReference("News");


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        listeners();
        sendGetRequest(CategoryType.ALL);
        firsTimeNotification();
//        changeMainTextViews();

    }

    private void init() {
        rvItems = findViewById(R.id.rvItems);
        viewPager = findViewById(R.id.viewPager);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        toolbar = findViewById(R.id.nav_action);
        swipeRefreshLayout = findViewById(R.id.swipe);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        disableNavigationViewScrollbars(navigationView);
    }

    private void listeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendGetRequest(currentCategory);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.mainPage) {
                    finish();
                    startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                }
                if (menuItem.getItemId() == R.id.agenda) {
                    viewPager.setVisibility(View.GONE);
                    pageIndicatorView.setVisibility(View.GONE);
                    sendGetRequest(CategoryType.AGENDA);
                }
                if (menuItem.getItemId() == R.id.world) {
                    viewPager.setVisibility(View.GONE);
                    pageIndicatorView.setVisibility(View.GONE);
                    sendGetRequest(CategoryType.WORLD);
                }
                if (menuItem.getItemId() == R.id.sports) {
                    viewPager.setVisibility(View.GONE);
                    pageIndicatorView.setVisibility(View.GONE);
                    sendGetRequest(CategoryType.SPORT);
                }
                if (menuItem.getItemId() == R.id.technology) {
                    viewPager.setVisibility(View.GONE);
                    pageIndicatorView.setVisibility(View.GONE);
                    sendGetRequest(CategoryType.TECHNOLOGY);
                }
                if (menuItem.getItemId() == R.id.magazine) {
                    viewPager.setVisibility(View.GONE);
                    pageIndicatorView.setVisibility(View.GONE);
                    sendGetRequest(CategoryType.MAGAZINE);
                }
                if (menuItem.getItemId() == R.id.economy) {
                    viewPager.setVisibility(View.GONE);
                    pageIndicatorView.setVisibility(View.GONE);
                    sendGetRequest(CategoryType.ECONOMY);
                }
                if (menuItem.getItemId() == R.id.hakkında) {
                    new AlertDialog.Builder(activity)
                            .setIcon(R.drawable.icon)
                            .setTitle(R.string.app_name)
                            .setMessage(ABOUT_MESSAGE)
                            .setPositiveButton(R.string.rate, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_URL)));
                                }
                            }).setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // pass
                        }
                    })
                            .show();
                }
                if (menuItem.getItemId() == R.id.settings) {
                    Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });
    }

    private void sendGetRequest(CategoryType categoryType) {
        currentCategory = categoryType;
        showLoading();
        final HurriyetNewsApi api = new HurriyetNewsApi();
        api.getNewsListByCategory(currentCategory, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                AppLogger.err(e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissLoading();
                        new AlertDialog.Builder(activity)
                                .setTitle(R.string.errorTitle)
                                .setMessage(R.string.errorDesc)
                                .setCancelable(false)
                                .setPositiveButton(R.string.tryAgain, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        sendGetRequest(currentCategory);
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
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<NewsModel>>() {
                }.getType();
                final List<NewsModel> items = gson.fromJson(response.body().string(), listType);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissLoading();
                        rvItems.setLayoutManager(new LinearLayoutManager(activity));
                        rvItems.setAdapter(new NewsAdapter(items));
                        clearDatabase(news);
                        for (NewsModel item : items) {
                            pushDatabase(item, news);
                        }

                        if (currentCategory == CategoryType.ALL) {
                            List<NewsModel> gundemItems = new ArrayList<>();
                            for (NewsModel item : items) {
                                if (item.Path.equalsIgnoreCase("/gundem/"))
                                    gundemItems.add(item);
                                pushDatabase(item, news);
                            }


                            viewPager.setAdapter(new NewsPagerAdapter(gundemItems.subList(0, 6)));
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    public void notificationBuilder() {

        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, intent.getFlags());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        builder.setContentTitle("Pratik Haber")
                .setContentText("Pratik habere Hoşgeldiniz")
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis());

        Notification notification = builder.build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, notification);

    }

    public void firsTimeNotification() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean("my_first_time", true)) {
            notificationBuilder();
            settings.edit().putBoolean("my_first_time", false).commit();
        }
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    public void pushDatabase(NewsModel item, DatabaseReference news) {
        news.child("title").push().setValue(item.Title);
        news.child("url").push().setValue(item.Url);
    }

    public void clearDatabase(DatabaseReference news) {
        news.child("title").removeValue();
        news.child("url").removeValue();
    }


}
