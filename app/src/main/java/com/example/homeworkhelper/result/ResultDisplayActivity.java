package com.example.homeworkhelper.result;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.homeworkhelper.R;
import com.example.homeworkhelper.history.bean.RecordData;
import com.example.homeworkhelper.mainPage.MainPageActivity;
import com.example.homeworkhelper.utils.JsonUtils;
import com.example.homeworkhelper.utils.TransferUtils;
import com.example.homeworkhelper.utils.common.Img;
import com.example.homeworkhelper.utils.common.Item;
import com.example.homeworkhelper.utils.common.LogRecord;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Base64;

public class ResultDisplayActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ImageView searchImg;
    private TextView findSimi;
    private ImageView mistakeAdd;
    private Bundle bundle;
    private MyPageAdapter adapter;
    private Toolbar toolbar;
    private int tabCount = 0;
    private ArrayList<Item> itemArrayList = new ArrayList<>();
    private ArrayList<String> tabNames = new ArrayList<>();
    private ArrayList<SearchResult> fragments = new ArrayList<>();
    private TabLayout tabs;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg){
            super.handleMessage(msg);
            LogRecord logRecord = JsonUtils.parseJson((String) msg.obj);
            if(logRecord != null) itemArrayList = logRecord.getItemArrayList();
            System.out.println(itemArrayList.size());
            tabCount = itemArrayList.size();
//            ArrayList<String> tabNames = new ArrayList(tabCount);
            for (int i = 0; i < tabCount; i++) {
                tabNames.add("");
                Item item = itemArrayList.get(i);
                String content = item.getContent();
                String answer = item.getAnswer();
                String hint = item.gethint();
                String remark = item.getremark();
                fragments.add(new SearchResult(JsonUtils.parseHtml(content), JsonUtils.parseHtml(answer), JsonUtils.parseHtml(hint), JsonUtils.parseHtml(remark)));
                System.out.println(fragments.get(i));
            }
            System.out.println(tabNames.size());
            adapter.notifyDataSetChanged();

//            tabs = new TabLayout(ResultDisplayActivity.this);
            tabs.setupWithViewPager(viewPager);
            findSimi.setText("找到" + tabCount + "个近似结果");
            TabLayout.Tab tab;
            for (int i = 0; i < tabs.getTabCount(); i++) {
                tab = tabs.getTabAt(i);
                if (i == 0) {
                    tab.setCustomView(adapter.getTabView("结果1", true));
                } else {
                    tab.setCustomView(adapter.getTabView("" + (i + 1), false));
                }
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_display);
        searchImg = findViewById(R.id.search_img);
        findSimi = findViewById(R.id.find_3_simi);
        mistakeAdd = findViewById(R.id.add_mistake);
        toolbar = findViewById(R.id.toolbar);
        tabs = findViewById(R.id.tabs);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        MainPageActivity.setHandler(handler);
        Intent intent = getIntent();
        String msg = getApiResult();
        searchImg.setImageBitmap(getBitmap());
        LogRecord logRecord = JsonUtils.parseJson(msg);
        if (logRecord != null) itemArrayList = logRecord.getItemArrayList();
        // 错题本选择
        mistakeAdd.setOnClickListener(v -> mistakeAdd.setSelected(!mistakeAdd.isSelected()));

        // 初始化
        tabCount = itemArrayList.size();
        findSimi.setText("找到" + tabCount + "个近似结果");

        for (int i = 0; i < tabCount; i++) {
            tabNames.add("");
            Item item = itemArrayList.get(i);
            String content = item.getContent();
            String answer = item.getAnswer();
            String hint = item.gethint();
            String remark = item.getremark();
            fragments.add(new SearchResult(JsonUtils.parseHtml(content), JsonUtils.parseHtml(answer), JsonUtils.parseHtml(hint), JsonUtils.parseHtml(remark)));
        }

        adapter = initPager(tabNames, fragments);
        // 初始化


        tabs.setupWithViewPager(viewPager);
        TabLayout.Tab tab;
        for (int i = 0; i < tabs.getTabCount(); i++) {
            tab = tabs.getTabAt(i);
            if (i == 0) {
                tab.setCustomView(adapter.getTabView("结果1", true));
            } else {
                tab.setCustomView(adapter.getTabView("" + (i + 1), false));
            }
            //给tab加上id后面好监听
            tab.setId(i);
        }
        //监听变化
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setCustomView(null);
                tab.setCustomView(adapter.getTabView("结果" + (tab.getId() + 1), true));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setCustomView(null);
                tab.setCustomView(adapter.getTabView("" + (tab.getId() + 1), false));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.setCustomView(null);
                tab.setCustomView(adapter.getTabView("结果" + (tab.getId() + 1), true));
            }
        });

    }


    private MyPageAdapter initPager(ArrayList<String> tabName, ArrayList<SearchResult> fragments) {
        MyPageAdapter adapter = new MyPageAdapter(this, getSupportFragmentManager(), fragments, tabName);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        return adapter;
    }

    private String getApiResult(){
        bundle = this.getIntent().getExtras();
        return bundle.getString("APIresult");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Bitmap getBitmap(){
        Bundle bundle = this.getIntent().getExtras();
        String base64 =  bundle.getString("Bitmap");
        Bitmap bitmap = null;
        byte[] bitmapArray = Base64.getDecoder().decode(base64);
        bitmap = BitmapFactory.decodeByteArray(bitmapArray,0,bitmapArray.length);
        return bitmap;
    }

}
