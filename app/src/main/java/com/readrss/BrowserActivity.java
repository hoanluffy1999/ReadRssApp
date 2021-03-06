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
import android.os.Handler;
import android.os.Looper;
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
import com.readrss.serveices.DB.FeedReaderModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
// màn hiển thị chi tiết tin
// Mỗi file activity sẽ có 1 file xml tương ứng trong res
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
        setContentView(binding.getRoot()); // set view cho màn
        buttonGo = binding.buttonGo;


        Intent in = getIntent();
        // lấy link tin
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
    // Code xử lý hiển thị tin lên màn hình
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

    // Code xử lý tải tin và lưu vào Sqlite
    public class DownloadTask extends AsyncTask<String, Void, Void> {
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
                Element title = document.getElementsByClass("title_news_detail").first();
                FeedReaderModel model = new FeedReaderModel(title.text(),document.html(),strings[0],"","");
                dbServices.Create(model);
                postToastMessage("Tải tin thành công" + model.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public void postToastMessage(final String message) {
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(BrowserActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}


