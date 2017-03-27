package com.yulu.zhaoxinpeng.neteasenews.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yulu.zhaoxinpeng.neteasenews.R;

/**
 * 用Volley网络通信框架。
 * 通常 适合数据量不大，但通信频繁的操作。
 * 本类是 利用Volley加载 数据接口
 */

public class MillitaryFragment extends Fragment {

    String Url = "http://118.244.212.82:9092/newsClient/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";

    private View view;
    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_millitary, null);

        initfragment();

        setVolleyData();
        return view;
    }

    //StringRequest
    private void setVolleyData() {
        if (requestQueue==null) {
            requestQueue = Volley.newRequestQueue(getActivity());
        }
        //2.实例化请求参数。参数一：Url;参数二：请求成功的处理；参数三：请求失败的处理
        StringRequest stringRequest=new StringRequest(Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response",response);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"没有网络，请打开网络！",Toast.LENGTH_SHORT).show();
            }
        });
        //3.把对象添加到请求队列中
        requestQueue.add(stringRequest);
    }

    private void initfragment() {

    }


}
