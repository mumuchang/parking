package com.parking.main;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import cn.bmob.v3.Bmob;

import com.parking.R;
import com.parking.fragments.SettingFragment;
import com.parking.fragments.ShouyeFragment;
import com.parking.fragments.ZhouweiFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.parking.news.ParseXML;
import com.parking.news.tengxun;
import org.jdom2.Document;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Coder-pig on 2015/8/28 0028.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{

    //UI Object

    private TextView txt_channel;
    private TextView txt_message;
    private TextView txt_better;
    private FrameLayout ly_content;
    private List newsFeeds;
    private List newsFeeds2;
    //Fragment Object
    private Fragment fg1,fg2,fg3;
    
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Bmob.initialize(MainActivity.this, "19872817e4d9a4070addbbb47c9564f5"); 
        fManager = getSupportFragmentManager();
        bindViews();
        txt_channel.performClick();   //ģ��һ�ε�����Ƚ�ȥ��ѡ���һ��

        new Thread(runnable).start();
//        new Thread(runnable2).start();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            HttpURLConnection connection = null;
            InputStream in = null;

            HttpURLConnection connection2 = null;
            InputStream in2 = null;
            try {
                Log.e("HELLO","HELLOOOOOOOOOOOOO");
                URL url = new URL("http://www.xinhuanet.com/auto/news_auto.xml");
                URL url2 = new URL("http://auto.qq.com/gouche/hangqing09/rss.xml");
                connection = (HttpURLConnection) url.openConnection();
                connection2 = (HttpURLConnection) url2.openConnection();

                connection.setRequestMethod("GET");
                connection.setReadTimeout(5000);
                connection.setConnectTimeout(100000);

                connection2.setRequestMethod("GET");
                connection2.setReadTimeout(5000);
                connection2.setConnectTimeout(100000);

                int responseCode = connection.getResponseCode();
                int responseCode2 = connection.getResponseCode();

                if (responseCode2 == 200){
                    in2 = connection2.getInputStream();

                    BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2 , "GB2312"));
                    StringBuilder res2 = new StringBuilder();
                    String line2;
                    while ((line2 = reader2.readLine()) != null) {
                        Log.e("AAAAAAAA", line2);
                        res2.append(line2);
                    }

                    InputStream stream2 = new ByteArrayInputStream(res2.toString().getBytes());
//                    InputStream stream = new ByteArrayInputStream(reader)
                    Document doc2 = ParseXML.readXMLFile(stream2);
                    if (doc2 == null){
                        Log.e("ERRORRRRRRRRRRRRRR", "IN IS NULL");
                    }
                    newsFeeds2 = ParseXML.parse(doc2);
//                    Log.e("content", "Conent"+ newsFeeds.get(1));

//                    mListViewAdapter = new tengxun.ListViewAdapter();
                }else {
                    Log.e("CONNECT ERROR", "error:"+responseCode);
                }

                if (responseCode == 200){
                    in = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in , "UTF-8"));
                    StringBuilder res = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Log.e("AAAAAAAA", line);
                        res.append(line);
                    }

                    InputStream stream = new ByteArrayInputStream(res.toString().getBytes());
//                    InputStream stream = new ByteArrayInputStream(reader)
                    Document doc = ParseXML.readXMLFile(stream);
                    if (doc == null){
                        Log.e("ERRORRRRRRRRRRRRRR", "IN IS NULL");
                    }
                    newsFeeds = ParseXML.parse(doc);
//                    Log.e("content", "Conent"+ newsFeeds.get(1));

//                    mListViewAdapter = new tengxun.ListViewAdapter();
                }else {
                    Log.e("CONNECT ERROR", "error:"+responseCode);
                }

            }catch (Exception e){
                Log.e("ERROR", e.getMessage());
                e.printStackTrace();
            }finally {
                try {
                    in.close();
                    connection.disconnect();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };



    //UI�����ʼ�����¼���
    private void bindViews() {
   
        txt_channel = (TextView) findViewById(R.id.txt_channel);
        txt_message = (TextView) findViewById(R.id.txt_message);
        txt_better = (TextView) findViewById(R.id.txt_better);
        ly_content = (FrameLayout) findViewById(R.id.ly_content);
        txt_channel.setOnClickListener(this);
        txt_message.setOnClickListener(this);
        txt_better.setOnClickListener(this);
     
    }

    //���������ı���ѡ��״̬
    private void setSelected(){
        txt_channel.setSelected(false);
        txt_message.setSelected(false);
        txt_better.setSelected(false);
     
    }

    //��������Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()){
            case R.id.txt_channel:
                setSelected();
                txt_channel.setSelected(true);
                if(fg1 == null){
                    fg1 = new ShouyeFragment();
                    fTransaction.add(R.id.ly_content,fg1);
                }else{
                    fTransaction.show(fg1);
                }
                break;
            case R.id.txt_message:
                setSelected();
                txt_message.setSelected(true);
                if(fg2 == null){
                    fg2 = new ZhouweiFragment();
                    Bundle data = new Bundle();
                    data.putStringArrayList("list", (ArrayList)newsFeeds);
                    data.putStringArrayList("list2", (ArrayList)newsFeeds2);
                    fg2.setArguments(data);//通过Bundle向Activity中传递值
                    fTransaction.add(R.id.ly_content,fg2);
                }else{
                    fTransaction.show(fg2);
                }
                break;
            case R.id.txt_better:
                setSelected();
                txt_better.setSelected(true);
                if(fg3 == null){
                    fg3 = new SettingFragment();
                    fTransaction.add(R.id.ly_content,fg3);
                }else{
                    fTransaction.show(fg3);
                }
                break;
        }
        fTransaction.commit();
    }
}
