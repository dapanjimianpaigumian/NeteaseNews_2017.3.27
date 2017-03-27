package com.yulu.zhaoxinpeng.neteasenews.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.yulu.zhaoxinpeng.neteasenews.listener.BitmapCollectionCallback;
import com.yulu.zhaoxinpeng.neteasenews.util.CacheManager;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/3/20.
 */

public class BitmapCollectionAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private Bitmap bitmap;
    private BitmapCollectionCallback bitmapCollectionCallback;

    public BitmapCollectionAsyncTask(MyCollectionAdapter myCollectionAdapter) {
        this.bitmapCollectionCallback = (BitmapCollectionCallback) myCollectionAdapter;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            //将从网络获取的图片放入缓存
            CacheManager.getInstance().getCacheHelper().put(params[0], bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        bitmapCollectionCallback.getBitmap(bitmap);
    }
}
