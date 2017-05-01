package com.ign.agajan.agajancodefoo;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ajumakuliyev on 4/30/17.
 */
public class WebViewActivity extends Activity {
    private String url = "http://www.ign.com";
    private WebView webView;

    public void setUrl(String url) {
        this.url = url;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_webview);
        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
    }
}
