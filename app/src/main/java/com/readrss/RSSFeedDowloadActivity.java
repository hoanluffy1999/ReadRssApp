package com.readrss;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.readrss.serveices.DB.DbServices;
import com.readrss.serveices.DB.FeedReaderModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
// Danh sách tin đã tải
public class RSSFeedDowloadActivity extends AppCompatActivity {


    private ProgressBar pDialog;
    ArrayList<HashMap<String, String>> rssItemList = new ArrayList<>();
private ListView lv;
    RSSParser rssParser = new RSSParser();
    Toolbar toolbar;

    List<RSSItem> rssItems = new ArrayList<>();
    private static String TAG_TITLE = "title";
    private static String TAG_LINK = "link";
    private static String TAG_PUB_DATE = "pubDate";
    private static  String TAG_IMG = "img";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_feed);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarList);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        String rss_link = getIntent().getStringExtra("rssLink");
        String title = getIntent().getStringExtra("title");
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(title);
        new LoadRSSFeedDowloadItems(this).execute(rss_link);

         lv = findViewById(R.id.list);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent in = new Intent(getApplicationContext(), BrowserActivity.class);
                String page_url = ((TextView) view.findViewById(R.id.page_url)).getText().toString().trim();
                in.putExtra("url", page_url);
                startActivity(in);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    // Lấy danh sách tin từ Sqlite và hiện ra
    public class LoadRSSFeedDowloadItems extends AsyncTask<String, String, String> {
        private Context mContext;
        private DbServices dbServices;
        public  LoadRSSFeedDowloadItems(Context context){

            mContext = context;
            dbServices = new DbServices(mContext);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressBar(RSSFeedDowloadActivity.this, null, android.R.attr.progressBarStyleLarge);


            RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );

            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            pDialog.setLayoutParams(lp);
            pDialog.setVisibility(View.VISIBLE);
            relativeLayout.addView(pDialog);
        }
        //
        @Override
        protected String doInBackground(String... args) {
            // rss link url
           // String rss_url = args[0];
            ArrayList<FeedReaderModel> data = dbServices.Get();
           // rssItems = rssParser.getRSSFeedItems(rss_url);
            Log.e("data count", data.size() + "");
            // looping through each item
            for (FeedReaderModel item : data) {
                // creating new HashMap
                if (item.getUrl().toString().equals(""))
                    break;
                HashMap<String, String> map = new HashMap<String, String>();




                map.put(TAG_TITLE, item.getTitle());
                map.put(TAG_LINK, item.getUrl());
                map.put(TAG_PUB_DATE, " "); // If you want parse the date
                map.put(TAG_IMG,item.img);

                // adding HashList to ArrayList
                rssItemList.add(map);
            }

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(
                            RSSFeedDowloadActivity.this,
                            rssItemList, R.layout.rss_item_list_row,
                            new String[]{TAG_LINK, TAG_TITLE, TAG_PUB_DATE,TAG_IMG},
                            new int[]{R.id.page_url, R.id.title, R.id.pub_date,R.id.img});

                    // updating listview
                   lv.setAdapter(adapter);
                }
            });
            return null;
        }

        protected void onPostExecute(String args) {
            pDialog.setVisibility(View.GONE);
        }
    }
}
