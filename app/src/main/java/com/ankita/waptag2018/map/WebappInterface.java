package com.ankita.waptag2018.map;

import android.app.Activity;
import android.webkit.JavascriptInterface;

/**
 * Created by qlooit-9 on 10/11/16.
 */
public class WebappInterface extends Activity {

    MapsActivity mapsActivity;

    public WebappInterface(MapsActivity mapsActivity) {
        this.mapsActivity = mapsActivity;
    }

    @JavascriptInterface
    public void showMessage(String msg, String id) {
        mapsActivity.callClickMe(msg,id);
    }
}
