package com.ankita.waptag2018.videos;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.ankita.waptag2018.BaseAppCompatActivity;
import com.ankita.waptag2018.R;

/**
 * Created by qlooit-9 on 9/11/16.
 */
public class VideoViewActivity extends BaseAppCompatActivity {


    private String URL;
    private WebView myWebView;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view_activity);
        Bundle bundle = getIntent().getExtras();
        setUpActionBar(bundle.getString("TITLE"));
        getSupportActionBar().setElevation(0);
        URL = bundle.getString("URL");

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }

        initcontrol();
    }

    private void initcontrol() {

        progressBar = ProgressDialog.show(VideoViewActivity.this,"Loading Video",  "fetching data...");

        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        myWebView.getSettings().setAppCacheMaxSize(1024 * 8);
        myWebView.getSettings().setAppCacheEnabled(true);

        myWebView.getSettings().setUseWideViewPort(false);
        myWebView.setWebChromeClient(new MyCustomWebChromeClient());
        myWebView.setWebViewClient(new MyCustomWebViewClient());

        // these settings speed up page load into the webview
        myWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        myWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


//        myWebView.loadUrl(URL);
        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        String html = getHTML();
        myWebView.loadDataWithBaseURL("", html, mimeType, encoding, "");


    }

    public String getHTML() {
        String html = "<body style=\"margin: 0px;background: black;\"><iframe width=\"100%\" src=" + URL  + " frameborder=\"0\" allowfullscreen=\"allowfullscreen\" mozallowfullscreen=\"mozallowfullscreen\" msallowfullscreen=\"msallowfullscreen\" oallowfullscreen=\"oallowfullscreen\" webkitallowfullscreen=\"webkitallowfullscreen\"></iframe></body>";
        return html;
    }


    private class MyCustomWebViewClient extends WebViewClient {

        MyCustomWebViewClient(){}

        public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
        {
            paramWebView.loadUrl(paramString);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            if (progressBar.isShowing()) {
                progressBar.dismiss();
            }
        }
    }

    private class MyCustomWebChromeClient extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        public MyCustomWebChromeClient() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (VideoViewActivity.this == null) {
                return null;
            }
            return BitmapFactory.decodeResource(VideoViewActivity.this.getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)VideoViewActivity.this.getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            VideoViewActivity.this.getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            VideoViewActivity.this.setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = VideoViewActivity.this.getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = VideoViewActivity.this.getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)VideoViewActivity.this.getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            VideoViewActivity.this.getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        myWebView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myWebView.onResume();
    }

}
