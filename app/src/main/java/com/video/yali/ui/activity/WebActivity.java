package com.video.yali.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.just.agentweb.AgentWeb;
import com.video.yali.R;
import com.video.yali.base.BaseActivity;
import com.video.yali.model.MyModel;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.YlUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebActivity extends BaseActivity {

    @BindView(R.id.ib_title_left)
    ImageButton ibTitleLeft;
    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;

    @BindView(R.id.tv_web_title)
    TextView tvWebTitle;

    public static int WebNotify = 1;
    public static int WebProblem = 2;
    public static int WebAgree = 3;

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.ll_web)
    LinearLayout llWeb;
    private int webId;
    private MyModel model = new MyModel();
    private int webType;
    private AgentWeb mAgentWeb;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initView() {


        tvTitleMiddle.setVisibility(View.VISIBLE);
        webType = getIntent().getIntExtra("webType", 0);
        initWebSet();
        if (webType == WebNotify) {
            tvTitleMiddle.setText(getString(R.string.bulletin_details));
            webId = getIntent().getIntExtra("webId", 0);
            String webTitle = getIntent().getStringExtra("webTitle");
            String webContent = getIntent().getStringExtra("webContent");
            tvWebTitle.setText(webTitle);
            tvWebTitle.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.VISIBLE);
            mWebView.loadDataWithBaseURL(null, webContent, "text/html", "utf-8", null);
            readNotifyNet();
        } else if (webType == WebProblem) {
            tvTitleMiddle.setText(getString(R.string.my_opinion_question));
            String webTitle = getIntent().getStringExtra("webTitle");
            String webContent = getIntent().getStringExtra("webContent");
            tvWebTitle.setText(webTitle);
            tvWebTitle.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.VISIBLE);
            mWebView.loadDataWithBaseURL(null, webContent, "text/html", "utf-8", null);
        } else if (webType == WebAgree) {
            tvTitleMiddle.setText(getString(R.string.user_agree));
            tvWebTitle.setVisibility(View.GONE);
//            mWebView.loadUrl("file:///assets/xieyi.html");
            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent((LinearLayout) llWeb, new LinearLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator(getResources().getColor(R.color.app_theme), 2)
                    // .setWebChromeClient(mWebChromeClient)
                    //.setWebViewClient(mWebViewcLient)
                    .createAgentWeb()
                    .ready()
                    .go("file:///android_asset/xieyi.html");
        } else {
            tvTitleMiddle.setText(getString(R.string.ad_details));
            tvWebTitle.setVisibility(View.GONE);
            String webUrl = getIntent().getStringExtra("webUrl");
//            LogUtils.e("链接--" + webUrl);
            mWebView.setVisibility(View.GONE);
          //  mWebView.loadUrl(webUrl);
            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent((LinearLayout) llWeb, new LinearLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator(getResources().getColor(R.color.app_theme), 2)
                   // .setWebChromeClient(mWebChromeClient)
                    //.setWebViewClient(mWebViewcLient)
                    .createAgentWeb()
                    .ready()
                    .go(webUrl);


            taskReadAdNet();
        }


    }

    private void initWebSet() {

        WebSettings settings = mWebView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        settings.setJavaScriptEnabled(true);
        //设置webView占满整个屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
//            settings.setTextSize(WebSettings.TextSize.LARGEST);
//        settings.setTextZoom(260); // 通过百分比来设置文字的大小，默认值是100
//        settings.setSupportZoom(true); // 可以缩放
//            String content = mArticleBean.getContent();

        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            //证书的设置  支持所有的证书
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });


    }


    @OnClick(R.id.ib_title_left)
    public void onClick() {
        finish();
    }

    protected void readNotifyNet() {
        Object[] ids = {webId};
        model.readNotifyData(this, ids, new RequestCallback() {
            @Override
            public void onSuccess(String data) {

            }

            @Override
            public void onError(int code) {

            }
        });
    }

    protected void taskReadAdNet() {
        if (!YlUtils.judgeUserExist()) {
            return;
        }
        model.taskReadAdData(this, new RequestCallback() {
            @Override
            public void onSuccess(String data) {

            }

            @Override
            public void onError(int code) {

            }
        });
    }



//    @Override
//    protected void onDestroy() {
//        if (webType == ConstantUtils.adtype_guide){
//            ToolUtils.startActivity(this,MainActivity.class);
//        }
//        super.onDestroy();
//    }
}
