package com.e.sdk_example_app_30.sdk_native;

import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.e.sdk_example_app_30.R;
import com.google.android.material.tabs.TabLayout;


public class SdkNativeMain extends AppCompatActivity {



    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdk_native_main);


        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setLogo(R.drawable.ic_taboola);
        getSupportActionBar().setTitle(R.string.toolbar_title);
        resetToolbarTitle();

        Fragment mFragment = null;
        mFragment = new TabFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.nativeframe, mFragment).commit();

    }

    private void resetToolbarTitle() {
        mToolbar.setTitle("SDK Native");
    }



}
