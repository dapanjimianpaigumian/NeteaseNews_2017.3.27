package com.yulu.zhaoxinpeng.neteasenews.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.yulu.zhaoxinpeng.neteasenews.R;
/**
 * 通过 ImageLoader 加载图片
 * 利用ImageLoader为ImageView加载网络图片，需要自定义图片内存缓存
 */
public class LifeFragment extends Fragment {


    private View view;
    private ImageView life_fragment_iv;
    private String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_life, container, false);
        life_fragment_iv = (ImageView) view.findViewById(R.id.life_fragment_iv);
        url= "http://t1.soutaotu.com/uploads/allimg/150818/1551006151-14.jpg";

        setImage();


        return view;
    }


    private void setImage() {
        // 创建队列
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        // 创建ImageLoader  , 参数一 是volley队列  ，参数 二 ，ImagCache缓存
        ImageLoader imageLoader = new ImageLoader(requestQueue, MyCache.getInstance());
        /**
         *  ImageLoader监听
         *  参数一 imageView控件  ，
         *  参数二 、 加载时 网络太慢，有一个用户等待时加载的图片，
         *  参数三 加载失败 显示的图片
         */
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(life_fragment_iv, R.mipmap.ic_launcher, R.drawable.homepage_title_home_default);
        // 用imagerLoader加载url
        imageLoader.get(url,imageListener,945,1420);

    }

    static class MyCache implements ImageLoader.ImageCache{

        private static MyCache myCache;
        private final LruCache<String, Bitmap> lruCache;

        private MyCache(){
            int size = (int) (Runtime.getRuntime().maxMemory() / 8);
            lruCache = new LruCache<String, Bitmap>(size) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount();
                }

                @Override
                protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                    super.entryRemoved(evicted, key, oldValue, newValue);
                }
            };
        }
        public static MyCache getInstance (){
            if (myCache==null) {
                 myCache =  new MyCache();
            }
            return myCache;
        }


        @Override
        public Bitmap getBitmap(String url) {
            return lruCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            lruCache.put(url,bitmap);
        }
    }

}
