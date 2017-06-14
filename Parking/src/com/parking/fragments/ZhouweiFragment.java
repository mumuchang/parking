package com.parking.fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.parking.R;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.parking.news.FragmentAdapter;
import com.parking.news.tengxun;

import java.util.ArrayList;
import java.util.List;

public class ZhouweiFragment extends Fragment {
    View view = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //�������ǵĲ���
        Bundle data = getArguments();//获得从activity中传递过来的值
        ArrayList list = data.getStringArrayList("list");
        ArrayList list2 = data.getStringArrayList("list2");
        view = inflater.inflate(R.layout.tab02, container, false);
        List<String> titles = new ArrayList<String>();
        titles.add("用车");
        titles.add("买车");
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.home_viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.home_tablayout);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), titles, list, list2);

        viewPager.setAdapter(fragmentAdapter);

        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab tab0 = tabLayout.getTabAt(0);

        TabLayout.Tab tab1 = tabLayout.getTabAt(1);



//        tengxun.instantiate(getContext(),"买车").onCreate(savedInstanceState);

        return view;
    }
}
