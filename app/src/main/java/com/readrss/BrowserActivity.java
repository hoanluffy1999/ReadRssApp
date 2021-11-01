package com.readrss;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.readrss.databinding.ActivityBrowserBinding;
import com.readrss.databinding.ActivityMainBinding;
import com.readrss.serveices.DB.DbServices;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;


public class BrowserActivity extends AppCompatActivity {


    WebView webView;
    String url;
    private Button buttonGo;
    private ActivityBrowserBinding binding;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBrowserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        buttonGo =binding.buttonGo;

//        setSupportActionBar(binding.toolbardowload);
//        Toolbar toolbar = binding.toolbardowload;
//        toolbar.inflateMenu(R.menu.main);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (item.getItemId() == R.id.action_settings) {
//                    Log.e("test","a");
//                    new DownloadTask().execute(url);
//                    return true; // event is handled.
//                }
//                return false;
//            }
//        });
        Intent in = getIntent();
        url = in.getStringExtra("url");
       // new DownloadTask().execute(url);
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(getApplicationContext(), "URL not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        webView = findViewById(R.id.webView);

        initWebView();
        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(BrowserActivity.this,"Bạn đã click",Toast.LENGTH_SHORT).show();
                new DownloadTask(BrowserActivity.this).execute(url);

            }
        });
        webView.loadUrl(url);


    }

    private void initWebView() {
        webView.setWebChromeClient(new MyWebChromeClient(this));
        webView.clearCache(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                invalidateOptionsMenu();
            }
        });
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
    }

    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.news_dowload:
//                Log.e("test","a");
//                new DownloadTask().execute(url);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
    static class DownloadTask extends AsyncTask<String, Void, Void> {
         private Context mContext;
        public  DownloadTask(Context context){
            mContext = context;
        }
        @Override
        protected Void doInBackground(String... strings) {
            Document document = null;

            DbServices dbServices = new DbServices(mContext);
            try {
                document = (Document)Jsoup.connect(strings[0]).get();
                Log.e("url",strings[0]);
                Element title = document.getElementsByClass("title_news_detail").first();
                Log.e("html",title.html());
            //    Toast.makeText(mContext, title.html(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}


