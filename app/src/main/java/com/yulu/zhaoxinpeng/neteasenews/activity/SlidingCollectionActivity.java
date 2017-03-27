package com.yulu.zhaoxinpeng.neteasenews.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yulu.zhaoxinpeng.neteasenews.R;
import com.yulu.zhaoxinpeng.neteasenews.adapter.MyCollectionAdapter;
import com.yulu.zhaoxinpeng.neteasenews.bean.NewsInfor;
import com.yulu.zhaoxinpeng.neteasenews.db.DBManager;
import com.yulu.zhaoxinpeng.neteasenews.listener.ListCallBack;

import java.util.List;

public class SlidingCollectionActivity extends AppCompatActivity implements ListCallBack{

    private ImageView sliding_collection_back;
    private TextView sliding_collection_title;
    private ListView sliding_collection_lv;
    private List<NewsInfor> list;
    private MyCollectionAdapter myCollectionAdapter;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1) {
             list= (List<NewsInfor>) msg.obj;
                myCollectionAdapter.setDataList(list);
                myCollectionAdapter.notifyDataSetChanged();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_collection);

        initactivity();

        setData();

        setAdapter();

        setOnclick();
    }

    private void setAdapter() {
        myCollectionAdapter = new MyCollectionAdapter(SlidingCollectionActivity.this);
        myCollectionAdapter.setDataList(list);
        sliding_collection_lv.setAdapter(myCollectionAdapter);

    }

    private void setData() {

        list = DBManager.getInstance(getApplication()).queryCollection();
        Log.e("list+++++++++++", list + "");

        Message msg = new Message();
        msg.what=1;
        msg.obj=list;
        handler.sendMessage(msg);
    }

    private void setOnclick() {
        sliding_collection_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initactivity() {
        sliding_collection_back = (ImageView) findViewById(R.id.sliding_collection_back);
        sliding_collection_title = (TextView) findViewById(R.id.sliding_collection_title);
        sliding_collection_lv = (ListView) findViewById(R.id.sliding_collection_lv);
    }

    @Override
    public void getList(List<NewsInfor> list) {
        this.list=list;
        myCollectionAdapter.setDataList(list);
        myCollectionAdapter.notifyDataSetChanged();
    }
}
