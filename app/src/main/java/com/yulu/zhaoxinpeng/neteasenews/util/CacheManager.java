package com.yulu.zhaoxinpeng.neteasenews.util;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/18.
 */

public class CacheManager {
    private static CacheManager cacheManager;
    HashMap<String, SoftReference<Bitmap>> map = new HashMap<String, SoftReference<Bitmap>>();
    private CacherHelper cacherHelper;

    public CacheManager() {
    }
    public static CacheManager getInstance(){
        if (cacheManager==null) {
            cacheManager =  new CacheManager();
        }
        return cacheManager;
    }

    public CacherHelper getCacheHelper(){
        int size = (int) Runtime.getRuntime().maxMemory();
        if (cacherHelper==null) {
            cacherHelper = new CacherHelper(size / 8, map);
        }
        return cacherHelper;
    }

    public Bitmap getBitmap(String url){
        Bitmap bitmap = getCacheHelper().get(url);
        if (bitmap!=null) {
            return bitmap;
        }else {
            if (map.get(url)==null) {
                map.remove(url);
                return null;
            }else {
                getCacheHelper().put(url,map.get(url).get());

                return map.get(url).get();
            }
        }
    }
}
