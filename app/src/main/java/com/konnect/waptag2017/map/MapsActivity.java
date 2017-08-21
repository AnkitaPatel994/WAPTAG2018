package com.konnect.waptag2017.map;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.konnect.waptag2017.BaseAppCompatActivity;
import com.konnect.waptag2017.R;
import com.konnect.waptag2017.event.SingleEventActivity;

/**
 * Created by qlooit-9 on 9/11/16.
 */
public class MapsActivity extends BaseAppCompatActivity {

    private WebView webView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map_activity);
        setUpActionBar(getString(R.string.title_map));
        getSupportActionBar().setElevation(0);
        initControl();


    }

    private void initControl() {

        webView = (WebView) findViewById(R.id.webView);

        WebSettings ws = webView.getSettings();
//        ws.setAllowFileAccess(true);
        ws.setJavaScriptEnabled(true);
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        ws.setDomStorageEnabled(true);
        ws.setBuiltInZoomControls(true);
//        ws.setSupportZoom(true);

//        webView.loadUrl("file://" + Environment.getExternalStorageDirectory() + "/WAPTAG EXPO/try.html");
        webView.loadUrl("file:///sdcard/WAPTAG/layout.jpg");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.loadUrl("file:///android_asset/map1_new.jpg");

            }
        });

    }

    public void callClickMe(String msg, String id) {

        new AlertDialog.Builder(MapsActivity.this)
                .setCancelable(false)
                .setTitle("Stall No. 512")
                .setMessage(msg)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("VISIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i = new Intent(getApplicationContext(), SingleEventActivity.class);
                        i.putExtra("FROM_EXHIBITION", "yo");
                        startActivity(i);
                    }
                })
                .create()
                .show();

    }
}
