package com.example.lixin.lixin1506a20170821.adapter;
/**
 * fragment的适配器
 * 姓名 李鑫
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lixin.lixin1506a20170821.MyFragment;

/**
 * Created by hua on 2017/8/21.
 */

public class MyPageAdapter extends FragmentPagerAdapter {

    private String[] titles = {"推荐","热点","杭州","时尚","科技","体育","财经","社会"};

    public MyPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        MyFragment myFragment = new MyFragment();
        return myFragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
