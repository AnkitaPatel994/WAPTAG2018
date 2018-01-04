package com.ankita.waptag2018.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.ankita.waptag2018.BaseAppCompatActivity;
import com.ankita.waptag2018.R;
import com.ankita.waptag2018.login.LoginActivity;
import com.ankita.waptag2018.main.MainActivity;

public class SplashActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setUpfullScreen();

        setContentView(R.layout.splash_activity);

        /*startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();*/

       initControl();

    }

    private void initControl() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*startActivity(new Intent(SplashActivity.this, LoginActivity.class));*/
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 3500);

    }
}
