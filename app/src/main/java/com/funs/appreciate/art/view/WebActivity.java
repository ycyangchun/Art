package com.funs.appreciate.art.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.funs.appreciate.art.R;

;

public class WebActivity extends Activity implements Runnable {

    private String theUrl;
    private WebView webView;
    private boolean handleBackEvent;
    private int checkCount = 0;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_web);

        theUrl = "";
        handleBackEvent = false;
        handler = new Handler();
        webView = (WebView) findViewById(R.id.webView);
        initWebView();

        Intent intent = getIntent();
        if (intent != null)
            theUrl = intent.getStringExtra("art_url");

        if (!TextUtils.isEmpty(theUrl)) {
            webView.loadUrl(theUrl);
            handler.postDelayed(this, 2000);
        } else {
            handleBackEvent = true;
        }


    }


    @Override
    protected void onDestroy() {
        destroyWebView();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
//            case KeyEvent.KEYCODE_DPAD_LEFT://左
//                jsCall("golive_left()");
//                break;
//            case KeyEvent.KEYCODE_DPAD_UP://上
//                jsCall("golive_up()");
//                break;
//            case KeyEvent.KEYCODE_DPAD_RIGHT://右
//                jsCall("golive_right()");
//                break;
//            case KeyEvent.KEYCODE_DPAD_DOWN://下
//                jsCall("golive_down()");
//                break;
//            case KeyEvent.KEYCODE_DPAD_CENTER://确定
//            case KeyEvent.KEYCODE_ENTER:
//                jsCall("golive_ok()");
//                break;
//            case KeyEvent.KEYCODE_MENU://菜单
//                jsCall("golive_menu()");
//                break;
            case KeyEvent.KEYCODE_BACK://返回
        	if (handleBackEvent){
        		finish();
        	}
//        	else{
//	        	jsCall("golive_back()");
//	        	return true;
//        	}
               /* if (webView.canGoBack()) {
                    webView.goBack();// 返回前一个页面
                    return true;
                }*/
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void run() {
//        checkCooperation1();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
//		webView.clearFocus();
        /***打开本地缓存提供JS调用**/
        webView.getSettings().setDomStorageEnabled(true);
        // Set cache size to 8 mb by default. should be more than enough
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        // This next one is crazy. It's the DEFAULT location for your app's cache
        // But it didn't work for me without this line.
        // UPDATE: no hardcoded path. Thanks to Kevin Hawkins
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        // 设置 缓存模式
        webView.getSettings().setCacheMode(
                WebSettings.LOAD_NO_CACHE);
        // webView.getSettings().setBlockNetworkImage(true);// 把图片加载放在最后来加载渲染
        //清除网页访问留下的缓存
        //由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
        webView.clearCache(true);

        //清除当前webview访问的历史记录
        //只会webview访问历史记录里的所有记录除了当前访问记录
        webView.clearHistory();

        //这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
        webView.clearFormData();
        webView.getSettings().setRenderPriority(RenderPriority.HIGH);
        // 支持多窗口
        webView.getSettings().setSupportMultipleWindows(true);
        // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        // 开启 Application Caches 功能
        webView.getSettings().setAppCacheEnabled(true);
//		webView.addJavascriptInterface(new AndroidBridge(), "android");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println("WebActivity: " + url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                //handler.cancel(); 默认的处理方式，WebView变成空白页
                //handler.proceed(); // 接受证书
            }


        });
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    @SuppressLint("NewApi")
    private void jsCall(String jsCode) {
        if (TextUtils.isEmpty(jsCode)) return;

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                webView.evaluateJavascript("javascript:" + jsCode + ";", null);
            } else {
                webView.loadUrl("javascript:" + jsCode);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    private void checkCooperation1() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                webView.evaluateJavascript("javascript:golive_advert_cooperation;", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        checkCooperation2(value);
                    }
                });
            } else {
                webView.loadUrl("javascript:window.golive.isCooperation(golive_advert_cooperation)");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void checkCooperation2(String value) {
        if (TextUtils.isEmpty(value) || "null".equalsIgnoreCase(value) || "undefined".equalsIgnoreCase(value)) {
            checkCount += 1;
            if (checkCount >= 2) {
                handleBackEvent = true;
            } else {
                handler.postDelayed(this, 2000);
            }
        } else {
            handleBackEvent = false;
        }
    }

    public final class MyWebChromeClient extends WebChromeClient {
//		@Override
//        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//            result.confirm();
//            return true;
//        }
//
//        @Override
//        public void onProgressChanged(WebView view, int newProgress) {
//        	super.onProgressChanged(view, newProgress);
//        }
    }

	public class AndroidBridge {

		@JavascriptInterface
        public void isCooperation(String value) {
			checkCooperation2(value);
        }

		@JavascriptInterface
        public void exit() {
			finish();
        }

		@JavascriptInterface
	    public void playVideo(String url) {

	    }

    }

	public void destroyWebView() {
		try {
			if (webView != null){
				ViewParent vp = webView.getParent();
				if (vp != null && vp instanceof ViewGroup){
					ViewGroup vg = (ViewGroup)vp;
					vg.removeView(webView);
				}
				webView.removeAllViews();
				webView.destroy();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
