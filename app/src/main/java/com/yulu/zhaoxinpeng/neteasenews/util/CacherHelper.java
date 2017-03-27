package com.yulu.zhaoxinpeng.neteasenews.util;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/18.
 */

/**
 * 设置图片缓存，（强引用，软引用）
 * 缓存图片
 */
public class CacherHelper extends LruCache <String,Bitmap>{
    HashMap<String,SoftReference<Bitmap>> map=new HashMap<String,SoftReference<Bitmap>>();

    public CacherHelper(int maxSize,HashMap<String,SoftReference<Bitmap>> map) {
        super(maxSize);
        this.map=map;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return super.sizeOf(key, value);
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        if (evicted) {
            //创建图片软引用
            SoftReference<Bitmap> SoftReference = new SoftReference<>(oldValue);
            map.put(key,SoftReference);
        }
    }
}
