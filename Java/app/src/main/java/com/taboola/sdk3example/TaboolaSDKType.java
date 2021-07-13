package com.taboola.sdk3example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.taboola.sdk3example.sdk_native.SDKNativeMenu;
import com.taboola.sdk3example.sdk_web.SDKWebMenu;

public class TaboolaSDKType extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Button sdk_classic=findViewById(R.id.sdk_classic);
        Button sdk_native=findViewById(R.id.sdk_native);
        Button sdk_web=findViewById(R.id.sdk_web);
        sdk_classic.setOnClickListener(this);
        sdk_native.setOnClickListener(this);
        sdk_web.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Class targetActivityClass;
        switch (v.getId()){
            case R.id.sdk_classic:
                targetActivityClass= SDKClassicMenu.class;
                break;
                case R.id.sdk_web:
                    targetActivityClass= SDKWebMenu.class;
                break;
              case R.id.sdk_native:
              targetActivityClass= SDKNativeMenu.class;
                break;
            default:
                return;
        }
        startActivity(new Intent(this,targetActivityClass));
    }
}