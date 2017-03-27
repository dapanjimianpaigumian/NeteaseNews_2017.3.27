package com.yulu.zhaoxinpeng.neteasenews.util;

import com.yulu.zhaoxinpeng.neteasenews.bean.NewsInfor;
import com.yulu.zhaoxinpeng.neteasenews.db.DBManager;
import com.yulu.zhaoxinpeng.neteasenews.db.HttpUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/3/22.
 */

public class JsonUtils {
    LinkedList<NewsInfor> list = new LinkedList<NewsInfor>();
    private static JsonUtils jsonUtils;

    public static JsonUtils getInstance() {
        if (jsonUtils == null) {
            jsonUtils = new JsonUtils();
        }
        return jsonUtils;
    }

    public LinkedList<NewsInfor> getJsonData(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtils.getInstance().httpGet(url);
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject_son = data.getJSONObject(i);
                        String summary = jsonObject_son.getString("summary");
                        String icon = jsonObject_son.getString("icon");
                        String stamp = setTime(jsonObject_son.getString("stamp"));
                        String title = jsonObject_son.getString("title");
                        int nid = jsonObject_son.getInt("nid");
                        String link = jsonObject_son.getString("link");
                        int type = jsonObject_son.getInt("type");
                        NewsInfor newsInfor = new NewsInfor(summary, icon, stamp, title, nid, link, type);

                        if (!DBManager.findIn(title)) {
                            //把数据写入数据库文件中
                            DBManager.insert(newsInfor);
                        }




                        list.addFirst(newsInfor);


                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();
        return list;
    }
    //转换时间格式
    public String setTime(String time) {

        String format = null;

        try {
            //先转换成 毫秒值
            Calendar instance = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            instance.setTime(simpleDateFormat.parse(time));
            long haom = instance.getTimeInMillis();

            //然后再转换为时间格式
            Date date = new Date(haom);
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date);
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM/dd HH:mm");
            format = simpleDateFormat1.format(gregorianCalendar.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format;
    }
}
