package com.taboola.sdk3example.sdk_web;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.taboola.sdk3example.R;


public class SDKWebMenu extends AppCompatActivity implements OnClickListener {


    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_d_k__web__menu);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.ic_taboola);
        getSupportActionBar().setTitle("SDK Web");
        resetToolbarTitle();

        Button widgetFeed=findViewById(R.id.widgetFeed);
        Button splitfeed=findViewById(R.id.splitfeed);
        Button viewPager=findViewById(R.id.viewPager);
        widgetFeed.setOnClickListener(this);
        splitfeed.setOnClickListener(this);
        viewPager.setOnClickListener(this);



    }
    @Override
    public void onClick(View v) {
        Class targetActivityClass;
        switch (v.getId()){
            case R.id.widgetFeed:
                targetActivityClass= SDKWeb.class;
                break;
            case R.id.splitfeed:
                targetActivityClass= SDKWebSplitFeed.class;
                break;
            case R.id.viewPager:
                targetActivityClass=WebPagerviajs.class;
                break;
            default:
                return;
        }
        startActivity(new Intent(this,targetActivityClass));
    }

    private void resetToolbarTitle() {
        toolbar.setTitle("SDK Web");
    }
}