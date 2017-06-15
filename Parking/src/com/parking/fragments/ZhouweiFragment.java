package com.parking.fragments;

import android.content.Context;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.parking.R;
import com.parking.news.NewsFeed;

import org.jdom2.Document;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ZhouweiFragment extends ListFragment implements AbsListView.OnScrollListener{
    private LinearLayout mLoadLayout;
    private ListView mListView;
    //    private ListViewAdapter mListViewAdapter = new ListViewAdapter();
    private ListViewAdapter mListViewAdapter;
    private int mLastItem = 0;
    private int mCount = 20;
    public List newsFeeds = null;
    private final Handler mHandler = new Handler();
    private final LinearLayout.LayoutParams mProgressBarLayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    private final LinearLayout.LayoutParams mTipContentLayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

    public ZhouweiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        mListView = (ListView) getActivity().findViewById(R.id.android_list);

        return inflater.inflate(R.layout.fragment_tengxun, container, false);

//        return mListView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * "������"���֣��˲��ֱ���ӵ�ListView��Footer�С�
         */

        mLoadLayout = new LinearLayout(getActivity().getApplicationContext());
        mLoadLayout.setMinimumHeight(60);
        mLoadLayout.setGravity(Gravity.CENTER);
        mLoadLayout.setOrientation(LinearLayout.HORIZONTAL);
        /**
         * ��"������"���������һ��Բ�ͽ�������
         */
        ProgressBar mProgressBar = new ProgressBar(getActivity().getApplicationContext());
        mProgressBar.setPadding(0, 0, 15, 0);
        mLoadLayout.addView(mProgressBar, mProgressBarLayoutParams);
        /**
         * ��"������"�����������ʾ��Ϣ��
         */
        TextView mTipContent = new TextView(getActivity().getApplicationContext());
        mTipContent.setText("������...");
        mTipContent.setTextColor(getResources().getColor(R.color.text_yellow));
        mLoadLayout.addView(mTipContent, mTipContentLayoutParams);
        /**
         * ��ȡListView���������"������"������ӵ�ListView�����Footer�С�
         */
        mListView = getListView();
        mListView.addFooterView(mLoadLayout);
        /**
         * ��ListView�������Adapter,�����û��������¼���
         */
        this.setListAdapter(mListViewAdapter);
//        mListView.setOnScrollListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();//��ô�activity�д��ݹ�����ֵ
        ArrayList<String> list = data.getStringArrayList("list");
        newsFeeds = (List)list;
        mListViewAdapter = new ListViewAdapter();
    }

    public void onScroll(AbsListView view, int mFirstVisibleItem,
                         int mVisibleItemCount, int mTotalItemCount) {
        mLastItem = mFirstVisibleItem + mVisibleItemCount - 1;
        if (mListViewAdapter.count >= mCount) {
            mListView.removeFooterView(mLoadLayout);
        }
    }

    public void onScrollStateChanged(AbsListView view, int mScrollState) {

        /**
         * ��ListView���������һ����¼ʱ��ʱ�����ǻῴ���Ѿ�����ӵ�ListView��"������"���֣� ��ʱӦ�ü���ʣ�����ݡ�
         */

        if (mLastItem == mListViewAdapter.count
                && mScrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            if (mListViewAdapter.count <= newsFeeds.size()) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListViewAdapter.count += 6;
                        mListViewAdapter.notifyDataSetChanged();
                        mListView.setSelection(mLastItem);
                    }
                }, 1000);
            }
        }
    }

    class ListViewAdapter extends BaseAdapter {
        int count = 20;


        public int getCount() {
            return count;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View view, ViewGroup parent) {
            TextView mTextView;
            NewsFeed newsFeed = (NewsFeed) newsFeeds.get(position);
            if (view == null) {
                mTextView = new TextView(getActivity());
            } else {
                mTextView = (TextView) view;
            }
            mTextView.setText(newsFeed.getTitle());
            mTextView.setTextSize(10f);
            mTextView.setGravity(Gravity.CENTER);
            mTextView.setHeight(200);
            mTextView.setTextColor(getResources().getColor(R.color.text_gray));
            return mTextView;
        }

    }

}
