package com.readrss;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.readrss.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private DrawerLayout mDrawerLayout;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        ActionBar actionbar = getSupportActionBar();
//        actionbar.setDisplayHomeAsUpEnabled(true);
//        actionbar.setHomeAsUpIndicator(R.mipmap.baseline_toc_black_48);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //Tạo menu
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
//        final Menu menu = navigationView.getMenu();
//          MenuItem item = menu.getItem(1);
//        final SubMenu subMenu = item.getSubMenu();
//        for (int i = 1; i <= 3; i++) {
//            subMenu.add(R.id.group2, Menu.NONE, Menu.NONE, "Menu Item "+i+" Title");
////            itemSub.setIcon(R.mipmap.baseline_toc_black_24);
//        }
        // Xử lý xự kiện click trên menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem menuItem) {
                int idGroup = menuItem.getGroupId();
                Toast.makeText(MainActivity.this,idGroup,Toast.LENGTH_SHORT);
                if(idGroup == R.id.group_category)
                {
                    // mở danh sách tin
                    NavigationRoter(menuItem.getItemId());
                }
                else if(menuItem.getItemId() == R.id.news_dowload)
                {
                    // Mở màn hình tin tải về
                    Intent intent = new Intent(MainActivity.this, RSSFeedDowloadActivity.class);
                    intent.putExtra("rssLink", "vn");
                    intent.putExtra("title", "Tin tải về");
                    startActivity(intent);
                }
                //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()



                //This is for maintaining the behavior of the Navigation view
                NavigationUI.onNavDestinationSelected(menuItem,navController);
                //This is for closing the drawer after acting on it
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
       // Button btnRediff = findViewById(R.id.btnRediff);
      //  Button btnCinemaBlend = findViewById(R.id.btnCinemaBlend);
       // btnRediff.setOnClickListener(this);
    //    btnCinemaBlend.setOnClickListener(this);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    /*@Override
    public void onClick(View view) {
        switch (view.getId()) {s
            case R.id.btnRediff:
                startActivity(new Intent(MainActivity.this, RSSFeedActivity.class).putExtra("rssLink", rssLinks.get(0)));
                break;

            case R.id.btnCinemaBlend:
                startActivity(new Intent(MainActivity.this, RSSFeedActivity.class).putExtra("rssLink", rssLinks.get(1)));
                break;
        }
    }*/
    // Mở các màn hình danh sách
    private  void NavigationRoter(int itemSelected)
    {
        switch (itemSelected)
        {
            case R.id.news_dowload:
                break;
            case R.id.tin_moi :
                StartActivityListRss("https://vnexpress.net/rss/tin-moi-nhat.rss","Tin Mới");
                break;
            case R.id.thoi_su:
                StartActivityListRss("https://vnexpress.net/rss/thoi-su.rss","Thời sự");
                break;
            case R.id.the_thao:
                StartActivityListRss("https://vnexpress.net/rss/the-thao.rss","Thể thao");
                break;
            case R.id.giai_tri:
                StartActivityListRss("https://vnexpress.net/rss/gia-tri.rss","Giải trí");
                break;
            case R.id.giao_duc:
                StartActivityListRss("https://vnexpress.net/rss/giao-duc.rss","Giáo dục");
                break;
            case R.id.suc_khoe:
                StartActivityListRss("https://vnexpress.net/rss/suc-khoe.rss","Sức khỏe");
                break;
        }
    }
    private  void StartActivityListRss(String dataSend,String title)
    {
        Intent intent = new Intent(MainActivity.this, RSSFeedActivity.class);
        intent.putExtra("rssLink", dataSend);
        intent.putExtra("title", title);
        startActivity(intent); }
}
