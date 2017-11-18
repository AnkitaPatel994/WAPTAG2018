package com.ankita.waptag2018.map;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.ankita.waptag2018.R;

/**
 * Created by qlooit-9 on 10/11/16.
 */
public class MyWebChromeClient extends WebChromeClient {

    MapsActivity mapsActivity;

    public MyWebChromeClient(MapsActivity mapsActivity) {
        this.mapsActivity = mapsActivity;
    }

    public boolean onJsAlert(WebView view, String url, final String message, final JsResult result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                .setTitle("WAPTAG EXPO 2017")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do your stuff
                                result.confirm();
                                //mainActivity.callClickMe(message);
                            }
                        });

        final FrameLayout frameView = new FrameLayout(view.getContext());
        builder.setView(frameView);

        final AlertDialog alertDialog = builder.create();
        LayoutInflater inflater = alertDialog.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.custom_map_view, frameView);
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.LEFT;
        wmlp.x = 50;   //x position
        wmlp.y = 475;  //y position
        alertDialog.show();
        return true;
    }

}
