package com.app.wuyang.myweather.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.app.wuyang.myweather.R;

/**
 * Created by wuyang on 16-2-11.
 */
public class AboutActivity extends AppCompatActivity {
    private WebView webView;
    private TextView titleUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);
        webView= (WebView) findViewById(R.id.webViewAbout);
        titleUrl = (TextView) findViewById(R.id.titleUrl);

        setWebView();
    }

    private void setWebView(){
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                titleUrl.setText(url);
                return true;
            }
        });

        webView.loadUrl("file:///android_asset/about.html");

    }
}
