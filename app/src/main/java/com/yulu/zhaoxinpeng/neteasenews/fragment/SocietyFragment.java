package com.yulu.zhaoxinpeng.neteasenews.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.yulu.zhaoxinpeng.neteasenews.R;
/**
 * 用Volley网络通信框架。
 * 通常 适合数据量不大，但通信频繁的操作。
 * 本类是 利用Volley加载 图片接口
 */
public class SocietyFragment extends Fragment {


    private View view;
    private String s;
    private ImageView society_volley_iv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_society, container, false);
        society_volley_iv = (ImageView) view.findViewById(R.id.society_volley_iv);

        setImageData();


        return view;
    }

    private void setImageData() {
        s = "http://t1.soutaotu.com/uploads/allimg/150818/1551112Z0-15.jpg";

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        /**
         * 参数一：url
         * 参数二：加载图片成功的监听
         * 参数三：采样后图片的宽
         * 参数四：采样后图片的高
         * 参数五：Config
         * 参数六：加载图片失败的监听
         */
        ImageRequest imageRequest = new ImageRequest(s, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                society_volley_iv.setImageBitmap(response);
            }
        }, 945, 1420, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "当前网络状况不佳", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(imageRequest);
    }

}
