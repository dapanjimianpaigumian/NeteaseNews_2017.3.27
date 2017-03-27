package com.yulu.zhaoxinpeng.neteasenews.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yulu.zhaoxinpeng.neteasenews.R;
import com.yulu.zhaoxinpeng.neteasenews.adapter.NewsAdapter;
import com.yulu.zhaoxinpeng.neteasenews.bean.NewsInfor;
import com.yulu.zhaoxinpeng.neteasenews.db.DBManager;
import com.yulu.zhaoxinpeng.neteasenews.util.JsonUtils;

import java.util.LinkedList;

/**
 * 利用HttpClient获取数据
 */

public class NewsFragment extends Fragment {
    View view;
    PullToRefreshListView listView;
    LinkedList<NewsInfor> list = new LinkedList<NewsInfor>();
    private NewsAdapter newsAdapter;
    String url = "http://118.244.212.82:9092/newsClient/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                list = (LinkedList<NewsInfor>) msg.obj;
                newsAdapter.setDataList(list);
                newsAdapter.notifyDataSetChanged();
            } else if (msg.what == 2) {
                Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 3) {

                LinkedList<NewsInfor> handlerlist = (LinkedList<NewsInfor>) msg.obj;
                Log.e("handler,obj-down-size", handlerlist.size() + "");

                list.addAll(0,handlerlist);
                Log.e("all------size---", list.size() + "");

                newsAdapter.setDataList(list);
                newsAdapter.notifyDataSetChanged();

                jsonDatalist.clear();
                //加载成功，下拉刷新的头部隐藏
                listView.onRefreshComplete();
            }else if (msg.what == 4) {

                LinkedList<NewsInfor> handlerlist = (LinkedList<NewsInfor>) msg.obj;
                Log.e("handler,obj-up-size--", handlerlist.size() + "");



                list.addAll(handlerlist);
                Log.e("all------size---", list.size() + "");

                newsAdapter.setDataList(list);
                newsAdapter.notifyDataSetChanged();

                jsonDatalist.clear();

            }
            //加载成功，下拉刷新的头部隐藏
            listView.onRefreshComplete();
        }
    };
    private LinkedList<NewsInfor> jsonDatalist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, null);

        initfragment();

        setadapter();
        //加载数据
        initdata();

        setPullToRefresh();

        return view;
    }

    private void setPullToRefresh() {
        listView.setMode(PullToRefreshListView.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String s = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_ABBREV_ALL);

                listView.getLoadingLayoutProxy().setLastUpdatedLabel(s);

                Log.e("news.db.size---------",DBManager.getInstance(getActivity()).query().size()+"");

                jsonDatalist = JsonUtils.getInstance().getJsonData(url);
                Log.e("down++++size++++++", jsonDatalist.size() + "");
                Message msg = Message.obtain();
                msg.what = 3;
                msg.obj = jsonDatalist;

                handler.sendMessage(msg);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String s = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_ABBREV_ALL);
                listView.getLoadingLayoutProxy().setLastUpdatedLabel(s);

                jsonDatalist = JsonUtils.getInstance().getJsonData(url);
                Log.e("up+++size++++", jsonDatalist.size() + "");
                Message msg = Message.obtain();
                msg.what = 4;
                msg.obj = jsonDatalist;

                handler.sendMessage(msg);
            }
        });
    }


    private void initdata() {
        if (DBManager.getInstance(getActivity()).getCount() > 0) {
            //当数据库中有数据时，加载数据库数据
            list = DBManager.getInstance(getActivity()).query();
            newsAdapter.setDataList(list);
            newsAdapter.notifyDataSetChanged();
        } else {
            //当数据库中没有数据时，从网络获取数据
            setdata();
        }
    }

    private void setadapter() {
        newsAdapter = new NewsAdapter(getActivity());
        listView.setAdapter(newsAdapter);

    }

    //解析网络数据
    private void setdata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LinkedList<NewsInfor> list_json = JsonUtils.getInstance().getJsonData(url);

                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = list_json;
                    handler.sendMessage(msg);
                    Log.e("msg-----------msg---msg", msg + "");

                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(2);
                }
            }
        }).start();
    }


    private void initfragment() {
        listView = (PullToRefreshListView) view.findViewById(R.id.news_fragment_lv);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
