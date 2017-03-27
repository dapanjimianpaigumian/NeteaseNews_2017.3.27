package com.yulu.zhaoxinpeng.neteasenews.listener;

import com.yulu.zhaoxinpeng.neteasenews.bean.NewsInfor;

import java.util.List;

import static android.R.id.list;

/**
 * Created by Administrator on 2017/3/20.
 */

public interface ListCallBack {
    void getList(List<NewsInfor> list);
}
