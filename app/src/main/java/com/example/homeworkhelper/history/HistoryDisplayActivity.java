package com.example.homeworkhelper.history;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.homeworkhelper.history.bean.RecordData;
import com.example.homeworkhelper.history.request.HistoryRequestUtils;
import com.example.homeworkhelper.utils.TransferUtils;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
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
    private ArrayList<RecordData> recordDataList = new ArrayList<>();
    private HistoryFragment allFragment;
    private HistoryFragment markedFragment;
    private ArrayList<RecordData> markedDataList = new ArrayList<>();
    private HistoryRequestUtils utils = new HistoryRequestUtils();
    private Handler handler = new Handler(){
        //接收请求完成消息
        @Override
        public void handleMessage(@NonNull Message msg){
            super.handleMessage(msg);
            String res = (String) msg.obj;
            new TransferUtils(res, RecordData.class, recordDataList);
            for (int i = 0; i < recordDataList.size(); i++) {
                System.out.println(recordDataList.get(i).getIs_marked());
                if (recordDataList.get(i).getIs_marked() == 1) markedDataList.add(recordDataList.get(i));
            }
            allFragment.refreshData();
            markedFragment.refreshData();
        }
    };


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
        binding.historyToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //初始化ViewPager
    private void initPager(ArrayList<String> tabName) {
        MyPageAdapter adapter = new MyPageAdapter(this, getSupportFragmentManager());

        //页面的数据
        utils.setHandler(handler);
        String res = utils.getAllHistory("http://10.0.2.2:8888/helper/history/allHistory", "1");
        recordDataList = new TransferUtils(res, RecordData.class).getResult();

        //所有历史记录
        allFragment = new HistoryFragment(recordDataList, this);
        Bundle bundle = new Bundle();
        bundle.putString("tabName", tabName.get(0));
        allFragment.setArguments(bundle);
        adapter.addFragment(allFragment, tabName.get(0));

        //错题历史记录
        markedFragment = new HistoryFragment(markedDataList, this);
        bundle = new Bundle();
        bundle.putString("tabName", tabName.get(1));
        markedFragment.setArguments(bundle);
        adapter.addFragment(markedFragment, tabName.get(1));

        //viewPager绑定
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