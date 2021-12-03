package com.example.homeworkhelper.result;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.homeworkhelper.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ResultDisplayActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ImageView searchImg;
    private TextView findSimi;
    private ImageView mistakeAdd;
    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_display);
        searchImg = findViewById(R.id.search_img);
        findSimi = findViewById(R.id.find_3_simi);
        mistakeAdd = findViewById(R.id.add_mistake);

        // 错题本选择
        mistakeAdd.setOnClickListener(v -> mistakeAdd.setSelected(!mistakeAdd.isSelected()));

//        Glide.with(this).load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg1.juimg.com%2F180329%2F330819-1P32913244598.jpg&refer=http%3A%2F%2Fimg1.juimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1640351339&t=0812f213e8fad81874ab0a02fbc7df96").into(searchImg);
        // 初始化
        String[] titleList = {"题目1\n\n\n\n\n", "题目2\n\n\n\n\n", "题目3\n\n\n\n\n", "题目4\n\n\n\n\n", "题目5\n\n\n\n\n"};
        String[] referenceList = {"答案1\n\n\n\n\n", "答案2\n\n\n\n\n", "答案3\n\n\n\n\n", "答案4\n\n\n\n\n", "答案5\n\n\n\n\n"};
        String[] analysisList = {"解析1\n\n\n\n\n", "解析2\n\n\n\n\n", "解析3\n\n\n\n\n", "解析4\n\n\n\n\n", "解析5\n\n\n\n\n"};
        int tabCount = 5;
        findSimi.setText("找到" + tabCount + "个近似结果");
        ArrayList<String> tabNames = new ArrayList(tabCount);
        for (int i = 0; i < tabCount; i++) {
            tabNames.add("");
        }
        ArrayList<SearchResult> fragments = new ArrayList(tabCount);
        for (int i = 0; i < tabCount; i++) {
            fragments.add(new SearchResult(titleList[i], referenceList[i], analysisList[i]));
        }
        MyPageAdapter mAdapter = initPager(tabNames, fragments);
        // 初始化
        TabLayout tabs = findViewById(R.id.tabs);

        tabs.setupWithViewPager(viewPager);
        TabLayout.Tab tab;
        for (int i = 0; i < tabs.getTabCount(); i++) {
            tab = tabs.getTabAt(i);
            if (i == 0) {
                tab.setCustomView(mAdapter.getTabView("结果1", true));
            } else {
                tab.setCustomView(mAdapter.getTabView("" + (i + 1), false));
            }
            //            给tab加上id后面好监听
            tab.setId(i);
        }
        //         监听变化
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setCustomView(null);
                tab.setCustomView(mAdapter.getTabView("结果" + (tab.getId() + 1), true));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setCustomView(null);
                tab.setCustomView(mAdapter.getTabView("" + (tab.getId() + 1), false));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.setCustomView(null);
                tab.setCustomView(mAdapter.getTabView("结果" + (tab.getId() + 1), true));
            }
        });

    }

    private MyPageAdapter initPager(ArrayList<String> tabName, ArrayList<SearchResult> fragments) {
        MyPageAdapter adapter = new MyPageAdapter(this, getSupportFragmentManager());
        for (int i = 0; i < fragments.size(); i++) {
            adapter.addFragment(fragments.get(i), tabName.get(i));
        }
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        return adapter;
    }


}
