package com.example.homeworkhelper.result;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.homeworkhelper.R;
import com.example.homeworkhelper.utils.LayoutUtils;

import java.util.ArrayList;
import java.util.List;

public class MyPageAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
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

    public View getTabView(String tabTitle, boolean is_selected) {
        int height = 32;
        int selectedWidth = 90;
        int unselectedWidth = 45;
        mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.item_result, null);
        TextView tv =view.findViewById(R.id.tab_result);
        tv.setText(tabTitle);
        if (is_selected) {
            tv.setLayoutParams(new LinearLayout.LayoutParams(LayoutUtils.dip2px(mContext, selectedWidth),LayoutUtils.dip2px(mContext, height)));
        }
        else {
            tv.setLayoutParams(new LinearLayout.LayoutParams(LayoutUtils.dip2px(mContext, unselectedWidth),LayoutUtils.dip2px(mContext, height)));
        }
        return view;
    }


}