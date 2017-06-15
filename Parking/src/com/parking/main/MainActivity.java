package com.parking.main;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;

import cn.bmob.v3.Bmob;

import com.parking.R;
import com.parking.fragments.SettingFragment;
import com.parking.fragments.ShouyeFragment;
import com.parking.fragments.ZhouweiFragment;
import com.parking.news.ParseXML;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Coder-pig on 2015/8/28 0028.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{

    //UI Object

    private TextView txt_channel;
    private TextView txt_message;
    private TextView txt_better;
    private FrameLayout ly_content;

    //Fragment Object
    private Fragment fg1,fg2,fg3;
    
    private FragmentManager fManager;
    
    public List newsFeeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Bmob.initialize(MainActivity.this, "19872817e4d9a4070addbbb47c9564f5"); 
        fManager = getSupportFragmentManager();
        bindViews();
        txt_channel.performClick();   //模拟一次点击，既进去后选择第一项
        
        new Thread(runnable).start();
    }
    
    Runnable runnable = new Runnable(){
        @Override
        public void run() {

            HttpURLConnection connection = null;
            InputStream in = null;

            HttpURLConnection connection2 = null;
            InputStream in2 = null;
            try {
                Log.e("HELLO","HELLOOOOOOOOOOOOO");
                URL url = new URL("http://www.xinhuanet.com/auto/news_auto.xml");
                connection = (HttpURLConnection) url.openConnection();
    
                connection.setRequestMethod("GET");
                connection.setReadTimeout(5000);
                connection.setConnectTimeout(100000);

                int responseCode = connection.getResponseCode();

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

    //UI组件初始化与事件绑定
    private void bindViews() {
   
        txt_channel = (TextView) findViewById(R.id.txt_channel);
        txt_message = (TextView) findViewById(R.id.txt_message);
        txt_better = (TextView) findViewById(R.id.txt_better);
        ly_content = (FrameLayout) findViewById(R.id.ly_content);
        txt_channel.setOnClickListener(this);
        txt_message.setOnClickListener(this);
        txt_better.setOnClickListener(this);
     
    }

    //重置所有文本的选中状态
    private void setSelected(){
        txt_channel.setSelected(false);
        txt_message.setSelected(false);
        txt_better.setSelected(false);
     
    }

    //隐藏所有Fragment
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
                    fg2.setArguments(data);
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
