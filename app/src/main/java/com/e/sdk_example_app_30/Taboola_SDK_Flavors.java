package com.e.sdk_example_app_30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.e.sdk_example_app_30.sdk_native.SdkNativeMain;
import com.e.sdk_example_app_30.sdk_web.SDK_Web_Menu;

public class Taboola_SDK_Flavors extends AppCompatActivity implements View.OnClickListener {

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
        switch (v.getId()){
            case R.id.sdk_classic:
                Intent intent=new Intent(this, SDK_Classic_Menu.class);
                startActivity(intent);
                break;
                case R.id.sdk_web:
                Intent intent2=new Intent(this, SDK_Web_Menu.class);
                startActivity(intent2);
                break;
          case R.id.sdk_native:
                Intent intent1=new Intent(this, SdkNativeMain.class);
                startActivity(intent1);
                break;
//            case R.id.sdk_web:
//                Intent intent2=new Intent(this,SDK_Web_Menu.class);
//                startActivity(intent2);
//                break;
            default:
                break;
        }
    }
}