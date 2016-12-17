package com.antoniocarlon.basewebview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    // Customize this URL
    private static final String BASE_URL = "http://www.google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        LinearLayout loadingView = (LinearLayout) findViewById(R.id.loading);
        ProgressBar loadingBar = (ProgressBar) findViewById(R.id.loadingBar);
        if (loadingBar != null) {
            loadingBar.getIndeterminateDrawable().setColorFilter(
                    getResources().getColor(R.color.colorPrimary),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        }

        LinearLayout errorView = (LinearLayout) findViewById(R.id.error);
        Button retry = (Button) findViewById(R.id.errorRetry);
        if (retry != null) {
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final Intent i = MainActivity.this
                            .getBaseContext()
                            .getPackageManager()
                            .getLaunchIntentForPackage(
                                    MainActivity.this.getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    MainActivity.this.startActivity(i);
                }
            });
        }

        WebView webView = (WebView) findViewById(R.id.webview);
        if (webView != null) {
            webView.setWebViewClient(new CustomWebViewClient(this, loadingView, errorView));
            webView.canGoBack();
            webView.canGoForward();
            webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
            webView.getSettings().setBuiltInZoomControls(false);
            webView.getSettings().setDisplayZoomControls(false);
            webView.getSettings().setSupportZoom(false);
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            webView.getSettings().setJavaScriptEnabled(true); // only if needed!!!
            webView.loadUrl(BASE_URL);
        }
    }
}