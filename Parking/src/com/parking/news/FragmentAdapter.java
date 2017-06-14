package com.parking.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/6/3.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private List<String> titles;
    private ArrayList newlist;
    private ArrayList newlist2;

    public FragmentAdapter(FragmentManager fm, List<String> titles, ArrayList newlist, ArrayList newlist2) {
        super(fm);
        this.titles = titles;
        this.newlist = newlist;
        this.newlist2 = newlist2;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            Fragment xinhua = new xinhua();
            Bundle b2 = new Bundle();
            b2.putStringArrayList("list", newlist2);
            xinhua.setArguments(b2);
            return xinhua;
        }
        Fragment t = new tengxun();
        Bundle b = new Bundle();
        b.putStringArrayList("list", newlist);
        t.setArguments(b);
        return t;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
