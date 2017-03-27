package com.yulu.zhaoxinpeng.neteasenews.db;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/17.
 */

/**
 * 利用HttpClient获取网络数据，返回String类型数据
 */
public class HttpUtils {
    private static HttpUtils httpUtils;
    private String s=null;

    private HttpUtils() {
    }

    public static HttpUtils getInstance() {
        if (httpUtils == null) {
            httpUtils = new HttpUtils();
        }
        return httpUtils;
    }

    //Get 方式获取网络数据
    public String httpGet(String url) {
        try {
            //1.添加HttpClient
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            //2.设置请求方式
            HttpGet httpGet = new HttpGet(url);
            //3.通过 Httpclient发送 请求方式
            HttpResponse execute = defaultHttpClient.execute(httpGet);
            //4.获取服务器返回值
            HttpEntity entity = execute.getEntity();
            //5.把httpentity转化为 String类型
            s = EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

}
