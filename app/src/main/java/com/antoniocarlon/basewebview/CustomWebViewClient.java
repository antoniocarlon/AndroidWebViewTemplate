package com.antoniocarlon.basewebview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebViewClient extends WebViewClient {
    private Context context;
    private View loadingView;
    private View errorView;
    private boolean isError = false;

    public CustomWebViewClient(Context context, View loadingView, View errorView) {
        this.context = context;
        this.loadingView = loadingView;
        this.errorView = errorView;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        Log.e("WebView Error", error.getErrorCode() + ": " + error.getDescription());
        error();
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description,
                                String failingUrl) {
        Log.e("WebView Error", errorCode + ": " + description);
        error();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        success();
    }

    private void error() {
        isError = true;

        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }

        if (errorView != null) {
            errorView.setVisibility(View.VISIBLE);
        }
    }

    private void success() {
        if (!isError) {
            if (loadingView != null) {
                loadingView.setVisibility(View.GONE);
            }

            if (errorView != null) {
                errorView.setVisibility(View.GONE);
            }
        }
    }
}
