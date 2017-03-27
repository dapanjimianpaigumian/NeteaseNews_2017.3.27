package com.yulu.zhaoxinpeng.neteasenews.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yulu.zhaoxinpeng.neteasenews.R;
import com.yulu.zhaoxinpeng.neteasenews.activity.Webview_itemActivity;
import com.yulu.zhaoxinpeng.neteasenews.bean.NewsInfor;
import com.yulu.zhaoxinpeng.neteasenews.db.DBManager;
import com.yulu.zhaoxinpeng.neteasenews.listener.BitmapListener;
import com.yulu.zhaoxinpeng.neteasenews.util.CacheManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/16.
 */
public class NewsAdapter extends BaseAdapter implements BitmapListener {
    Context context;
    private ViewHolder holder;
    LinkedList<NewsInfor> list = new LinkedList<NewsInfor>();

    public NewsAdapter(FragmentActivity activity) {
        this.context = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.fragment_news_item, null);
            holder.icon = (ImageView) view.findViewById(R.id.News_icon);
            holder.title = (TextView) view.findViewById(R.id.News_title);
            holder.summary = (TextView) view.findViewById(R.id.News_summary);
            holder.stamp = (TextView) view.findViewById(R.id.News_stamp);
            holder.news_item_layout = (RelativeLayout) view.findViewById(R.id.news_item_layout);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (list.size() > 0) {
            holder.stamp.setText(list.get(position).getStamp());
            holder.summary.setText(list.get(position).getSummary());
            holder.title.setText(list.get(position).getTitle());
            Bitmap bitmap = CacheManager.getInstance().getBitmap(list.get(position).getIcon());
            if (bitmap == null) {

                new BitmapAsyncTask(this).execute(list.get(position).getIcon());
            } else {
                holder.icon.setImageBitmap(bitmap);
            }

        }
        //短按item 开启WebVeiw
        holder.news_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 在适配器中开启Activity的方法
                 */

                Intent intent = new Intent(context, Webview_itemActivity.class);
                intent.putExtra("string", new String[]{list.get(position).getSummary(), list.get(position).getIcon(), list.get(position).getStamp(), list.get(position).getTitle().toString(), list.get(position).getLink().toString()});
                intent.putExtra("int", new int[]{list.get(position).getNid(), list.get(position).getType()});
                context.startActivity(intent);

            }
        });
        //长按item 呼出提示框
        holder.news_item_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                builder.setMessage("加入收藏？\n\n(稍后可在我的收藏中查看)");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //将数据添加到collections表中
                        if (DBManager.getInstance(context).find(list.get(position).getTitle())) {
                            Toast.makeText(context, "本条数据在我的收藏中已经存在", Toast.LENGTH_SHORT).show();
                        }else {
                            DBManager.getInstance(context).insertone(list.get(position));
                            Toast.makeText(context, "已收藏", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
                return false;
            }
        });
        return view;
    }

    public void setDataList(LinkedList<NewsInfor> list) {
        this.list = list;
    }

    //实现接口回调
    @Override
    public void getBitmap(Bitmap bitmap) {
        holder.icon.setImageBitmap(bitmap);
    }

    class ViewHolder {
        ImageView icon;
        TextView title, summary, stamp;
        RelativeLayout news_item_layout;
    }
}
