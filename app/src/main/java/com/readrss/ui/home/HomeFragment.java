package com.readrss.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.readrss.R;
import com.readrss.RSSFeedActivity;
import com.readrss.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    public ArrayList<String> rssLinks = new ArrayList<>();

    public ListView lístNewspaperPage;
    public  String tutorials[]
            = { "https://vnexpress.net/","https://tuoitre.vn/" };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        lístNewspaperPage = binding.listSource;
        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(
                binding.getRoot().getContext(),
               R.layout.support_simple_spinner_dropdown_item,
                tutorials);
        lístNewspaperPage.setAdapter(arr);
        lístNewspaperPage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(binding.getRoot().getContext(), RSSFeedActivity.class);
                String webSource = tutorials[position];
                intent.putExtra("rssLink",  webSource+"rss/tin-moi-nhat.rss");
                Log.e("rsslink",webSource);
                intent.putExtra("title", webSource);
                startActivity(intent);
            }
        });
        Log.e("log","Home");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}