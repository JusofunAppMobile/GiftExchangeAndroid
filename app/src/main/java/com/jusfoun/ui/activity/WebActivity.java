package com.jusfoun.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jusfoun.giftexchange.R;
import com.jusfoun.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 加载HTML页面
 *
 * @时间 2017/6/26
 * @作者 LiuGuangDan
 */

public class WebActivity extends BaseActivity {

    @BindView(R.id.web)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_web);
        ButterKnife.bind(this);

        initWebView();

        String title = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(title))
            setTitle(title);

        String url = getUrl();
        LogUtils.e("URL=" + url);
        webView.loadUrl(url);
    }

    public static void startActivity(Context context, String title, String url, String id, Bundle extraBundle) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("id", id);
        if (extraBundle != null)
            intent.putExtras(extraBundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String title, String url, Bundle extraBundle) {
        startActivity(context, title, url, null, extraBundle);
    }

    public static void startActivity(Context context, String title, String url, String id) {
        startActivity(context, title, url, id, null);
    }

    public static void startActivity(Context context, String title, String url) {
        startActivity(context, title, url, null, null);
    }

    class MyWebViewClient extends WebViewClient {

    }


    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        WebUtil.getUserAgentString(webView.getSettings(), this);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(chromeClient);
    }

    WebChromeClient chromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }
    };

    private String getUrl() {
        return getIntent().getStringExtra("url");
    }
}
