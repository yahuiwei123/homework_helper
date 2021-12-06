package com.example.homeworkhelper.result;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.homeworkhelper.R;
import com.example.homeworkhelper.utils.JsonUtils;
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
    private ScrollView scrollView;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_display);
        searchImg = findViewById(R.id.search_img);
        findSimi = findViewById(R.id.find_3_simi);
        mistakeAdd = findViewById(R.id.add_mistake);
//        Intent intent =getIntent();
        String msg = "{\"items\":[{\"itemId\":\"63655535\",\"content\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/0e9abbfe5b2f159de7f6aff1393fcfda-375.png\\\" width=\\\"335\\\" height=\\\"311\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-311,320-280-338,360-320-311,411-371-284,460-420-284,534-494-257,768-728-257\\\" \\u003e\",\"answer\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/9ad935517d160f3adf6b7c2273e435e0-375.png\\\" width=\\\"335\\\" height=\\\"398\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-398,320-280-392,360-320-392,411-371-398,460-420-365,534-494-338,768-728-338\\\" \\u003e\",\"hint\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/5dcd9c037b7a8d58767e0af0ae6be27b-375.png\\\" width=\\\"335\\\" height=\\\"135\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-135,320-280-135,360-320-135,411-371-135,460-420-108,534-494-81,768-728-81\\\" \\u003e\",\"remark\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/b90a0b7984403ffa312fd81d5ca9dcfd-375.png\\\" width=\\\"335\\\" height=\\\"81\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-81,320-280-81,360-320-81,411-371-54,460-420-54,534-494-54,768-728-27\\\" \\u003e\",\"subject\":2},{\"itemId\":\"669758137\",\"content\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/53c78f44194d8564f2a94ea58906d6e0-375.png\\\" width=\\\"335\\\" height=\\\"232\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-232,320-280-286,360-320-232,411-371-232,460-420-205,534-494-178,768-728-124\\\" \\u003e\",\"answer\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/26213ba6812317b5f8b8a760cdfc8830-375.png\\\" width=\\\"335\\\" height=\\\"27\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-27,320-280-27,360-320-27,411-371-27,460-420-27,534-494-27,768-728-27\\\" \\u003e\",\"hint\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/e299c1930920e946023fb55119c2feda-375.png\\\" width=\\\"335\\\" height=\\\"135\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-135,320-280-135,360-320-135,411-371-135,460-420-108,534-494-81,768-728-81\\\" \\u003e\",\"remark\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/0ad7412106d44cb92faa60eadbcd95da-375.png\\\" width=\\\"335\\\" height=\\\"81\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-81,320-280-81,360-320-81,411-371-54,460-420-54,534-494-54,768-728-27\\\" \\u003e\",\"subject\":2},{\"itemId\":\"4220693\",\"content\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/9e3a21f3ded24126d6f6d1c9535dcf4a-375.png\\\" width=\\\"335\\\" height=\\\"269\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-269,320-280-323,360-320-296,411-371-269,460-420-269,534-494-242,768-728-215\\\" \\u003e\",\"answer\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/5299347fcd22fee176c0f7f2778e190b-375.png\\\" width=\\\"335\\\" height=\\\"153\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-153,320-280-207,360-320-180,411-371-153,460-420-153,534-494-153,768-728-153\\\" \\u003e\",\"hint\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/24b4b1993aff02dca46bfd2aae02c8f5-375.png\\\" width=\\\"335\\\" height=\\\"216\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-216,320-280-216,360-320-216,411-371-216,460-420-162,534-494-162,768-728-135\\\" \\u003e\",\"remark\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/333f6b20d2a336571efd4fe1462f3e57-375.png\\\" width=\\\"335\\\" height=\\\"124\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-124,320-280-180,360-320-124,411-371-96,460-420-96,534-494-96,768-728-64\\\" \\u003e\",\"subject\":2},{\"itemId\":\"37265214\",\"content\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/140690c9bf227a3b9a0d0daaef08d3ef-375.png\\\" width=\\\"335\\\" height=\\\"396\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-396,320-280-423,360-320-396,411-371-396,460-420-369,534-494-369,768-728-342\\\" \\u003e\",\"answer\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/761c05d59177cbf17280da8756d35349-375.png\\\" width=\\\"335\\\" height=\\\"297\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-297,320-280-378,360-320-324,411-371-297,460-420-297,534-494-297,768-728-243\\\" \\u003e\",\"hint\":\"\",\"remark\":\"\",\"subject\":2},{\"itemId\":\"35241890\",\"content\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/31ed07207a268fce4b6a6dfeb0addb28-375.png\\\" width=\\\"335\\\" height=\\\"343\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-343,320-280-371,360-320-343,411-371-315,460-420-315,534-494-315,768-728-287\\\" \\u003e\",\"answer\":\"\\u003cimg class=\\\"snapshot\\\" src=\\\"https://qb-oss.bytededu.cn/snapshot/982d819072e60c4375742e185480cc10-375.png\\\" width=\\\"335\\\" height=\\\"289\\\" font_size=\\\"12\\\" data-spec=\\\"375-335-289,320-280-345,360-320-289,411-371-289,460-420-289,534-494-261,768-728-233\\\" \\u003e\",\"hint\":\"\",\"remark\":\"\",\"subject\":2}],\"BaseResp\":{\"error\":{\"code\":0,\"type\":0,\"title\":\"\",\"e_message\":\"\",\"nlp\":\"\",\"schema\":\"\",\"confirm_button\":\"\",\"cancel_button\":\"\",\"error_comment\":\"\"},\"logId\":\"202112041434510102020871482607AD35\",\"StatusMessage\":\"\",\"StatusCode\":0,\"Extra\":{\"logId\":\"202112041434510102020871482607AD35\"}}}";
        test();
        LogRecord logRecord = JsonUtils.parseJson(msg);
        ArrayList<Item> itemArrayList = logRecord.getItemArrayList();   // logrecord肯可能是null

        // 错题本选择
        mistakeAdd.setOnClickListener(v -> mistakeAdd.setSelected(!mistakeAdd.isSelected()));

        // 初始化
        int tabCount = itemArrayList.size();
        System.out.println(tabCount);
        findSimi.setText("找到" + tabCount + "个近似结果");
        ArrayList<String> tabNames = new ArrayList(tabCount);
        for (int i = 0; i < tabCount; i++) {
            tabNames.add("");
        }
        ArrayList<SearchResult> fragments = new ArrayList(tabCount);
        for (int i = 0; i < tabCount; i++) {
            Item item = itemArrayList.get(i);
            String content = item.getContent();
            String answer = item.getAnswer();
            String hint = item.gethint();
            String remark = item.getremark();
            fragments.add(new SearchResult(JsonUtils.parseHtml(content), JsonUtils.parseHtml(answer), JsonUtils.parseHtml(hint), JsonUtils.parseHtml(remark)));
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void test() {
        System.out.println(getApiResult());
        searchImg.setImageBitmap(getBitmap());
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

    private String getApiResult(){
        Bundle bundle = this.getIntent().getExtras();
        System.out.println("-----------------------");
        return bundle.getString("APIresult");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Bitmap getBitmap(){
        Bundle bundle = this.getIntent().getExtras();
        System.out.println("-----------------------");
        String base64 =  bundle.getString("Bitmap");
        Bitmap bitmap = null;
        byte[] bitmapArray = Base64.getDecoder().decode(base64);
        bitmap = BitmapFactory.decodeByteArray(bitmapArray,0,bitmapArray.length);
        return bitmap;
    }

}
