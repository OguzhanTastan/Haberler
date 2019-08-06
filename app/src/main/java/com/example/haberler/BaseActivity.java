package com.example.haberler;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.Touch;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.haberler.service.CategoryType;

public class BaseActivity extends AppCompatActivity {

    public AppCompatActivity activity;
    public ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    public void showLoading() {
        // android progressbar dialog
        progressDialog.show();


    }

    public void dismissLoading() {
        progressDialog.dismiss();
    }

    public void changeBaseDetailTextSize(TextView textView , CategoryType categoryType) {
        textView.setTextSize(categoryType.textSize(textView.getTextSize()));


    }

    public void changeBaseDetailColor(TextView textView ,CategoryType categoryType) {
        textView.setTextColor(categoryType.textColor(textView.getCurrentTextColor()));



    }

}
