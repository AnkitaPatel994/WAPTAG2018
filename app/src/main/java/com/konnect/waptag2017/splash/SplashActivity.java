package com.konnect.waptag2017.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.konnect.waptag2017.BaseAppCompatActivity;
import com.konnect.waptag2017.R;
import com.konnect.waptag2017.login.LoginActivity;

public class SplashActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setUpfullScreen();

        setContentView(R.layout.splash_activity);

        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();

//        initControl();

    }

    private void initControl() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 3500);

    }
}
