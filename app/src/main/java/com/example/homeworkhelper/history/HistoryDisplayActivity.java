package com.example.homeworkhelper.history;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.homeworkhelper.history.bean.RecordData;
import com.example.homeworkhelper.history.request.HistoryRequestUtils;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import com.example.homeworkhelper.databinding.ActivityHistoryDisplayBinding;

import java.util.ArrayList;
import java.util.List;

public class HistoryDisplayActivity extends AppCompatActivity {

    private ActivityHistoryDisplayBinding binding;
    private ViewPager viewPager;
    private HistoryRequestUtils utils = new HistoryRequestUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHistoryDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //初始化Tab标签
        ArrayList<String> tabName = new ArrayList<>();
        tabName.add("全部");
        tabName.add("错题");

        initPager(tabName);
        TabLayout tabs = binding.tabs;

        tabs.setupWithViewPager(viewPager);

        //设置返回按钮和删除记录按钮
        Button back = binding.recordBack;
        Button del = binding.recordDel;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回上一个界面
                Toast.makeText(HistoryDisplayActivity.this, "返回", Toast.LENGTH_LONG).show();
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除历史记录
                Toast.makeText(HistoryDisplayActivity.this, "删除", Toast.LENGTH_LONG).show();
            }
        });
    }

    //初始化ViewPager
    private void initPager(ArrayList<String> tabName) {
        MyPageAdapter adapter = new MyPageAdapter(this, getSupportFragmentManager());

        //页面的数据
        List<RecordData> recordDataList = utils.getAllHistory("http://10.0.2.2:8888/helper/history/allHistory", "1");



        HistoryFragment fragment = new HistoryFragment(recordDataList, this);
        for (int i = 0; i < tabName.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putString("tabName", tabName.get(i));
            fragment.setArguments(bundle);
            adapter.addFragment(fragment, tabName.get(i));
            fragment = new HistoryFragment(recordDataList, this);
        }

        viewPager = binding.viewPager;
        viewPager.setAdapter(adapter);
    }

    //重写Pager适配器
    private class MyPageAdapter extends FragmentPagerAdapter {
        private final Context mContext;
        ArrayList<String> tabName = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();


        public void addFragment(Fragment fragment, String name){
            tabName.add(name);
            fragments.add(fragment);
        }

        public MyPageAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabName.get(position);
        }
    }
}