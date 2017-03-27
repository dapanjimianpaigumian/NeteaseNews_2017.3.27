package com.yulu.zhaoxinpeng.neteasenews.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.yulu.zhaoxinpeng.neteasenews.listener.BitmapListener;
import com.yulu.zhaoxinpeng.neteasenews.util.CacheManager;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/3/18.
 */

/**
 * 开启异步任务，从网络获取图片数据
 */
public class BitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {
    //1.写一个接口变量
    private BitmapListener bitmapListener;

    //2.使接口与传值方关联
    public BitmapAsyncTask(NewsAdapter newsAdapter) {
        this.bitmapListener = newsAdapter;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);

            //把图片放入缓存中
            CacheManager.getInstance().getCacheHelper().put(strings[0], bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        bitmapListener.getBitmap(bitmap);
    }
}
