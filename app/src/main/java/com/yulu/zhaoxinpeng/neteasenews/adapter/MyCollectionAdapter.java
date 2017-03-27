package com.yulu.zhaoxinpeng.neteasenews.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.yulu.zhaoxinpeng.neteasenews.activity.SlidingCollectionActivity;
import com.yulu.zhaoxinpeng.neteasenews.activity.Webview_itemActivity;
import com.yulu.zhaoxinpeng.neteasenews.bean.NewsInfor;
import com.yulu.zhaoxinpeng.neteasenews.db.DBManager;
import com.yulu.zhaoxinpeng.neteasenews.listener.BitmapCollectionCallback;
import com.yulu.zhaoxinpeng.neteasenews.listener.ListCallBack;
import com.yulu.zhaoxinpeng.neteasenews.util.CacheManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
public class MyCollectionAdapter extends BaseAdapter implements BitmapCollectionCallback {
    List<NewsInfor> list = new ArrayList<NewsInfor>();
    private ViewHolder holder;
    Context context;
    private View view;
    private ListCallBack listCallBack;

    public MyCollectionAdapter(SlidingCollectionActivity slidingCollectionActivity) {
        this.context = slidingCollectionActivity;
        this.listCallBack=slidingCollectionActivity;
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
            view = LayoutInflater.from(context).inflate(R.layout.sliding_collection_item, null);
            holder.icon = (ImageView) view.findViewById(R.id.collection_icon);
            holder.stamp = (TextView) view.findViewById(R.id.collection_stamp);
            holder.summary = (TextView) view.findViewById(R.id.collection_summary);
            holder.title = (TextView) view.findViewById(R.id.collection_title);
            holder.collection_item_layout = (RelativeLayout) view.findViewById(R.id.collection_item_layout);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (list.size() > 0) {
            holder.title.setText(list.get(position).getTitle());
            holder.summary.setText(list.get(position).getSummary());
            holder.stamp.setText(list.get(position).getStamp());
            Bitmap bitmap = CacheManager.getInstance().getBitmap(list.get(position).getIcon());
            if (bitmap == null) {
                new BitmapCollectionAsyncTask(this).execute(list.get(position).getIcon());
            } else {
                holder.icon.setImageBitmap(bitmap);
            }
        }
        //长按item 呼出提示框
        holder.collection_item_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                 Toast.makeText(context, "这是长按", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                builder.setMessage("是否删除此条收藏？");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //将数据从 collections表中删除
                        Toast.makeText(context,"已删除此条收藏",Toast.LENGTH_SHORT).show();
                        //String summary, String icon, String stamp, String title, int nid, String link, int type
//                        NewsInfor newsInfor = new NewsInfor("summary", "icon", "stamp", "title", list.get(position).getNid(), "link", 1);
                        DBManager.getInstance(context).delete(list.get(position));
                        list=DBManager.getInstance(context).queryCollection();
                        listCallBack.getList(list);
                    }
                });
                builder.show();
                return false;
            }
        });
        //短按进入WebView
        holder.collection_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Webview_itemActivity.class);
                intent.putExtra("string", new String[]{list.get(position).getSummary(), list.get(position).getIcon(), list.get(position).getStamp(), list.get(position).getTitle().toString(), list.get(position).getLink().toString()});
                intent.putExtra("int", new int[]{list.get(position).getNid(), list.get(position).getType()});
                context.startActivity(intent);
            }
        });


        return view;
    }

    public void setDataList(List<NewsInfor> list) {
        this.list = list;
    }

    @Override
    public void getBitmap(Bitmap bitmap) {
        holder.icon.setImageBitmap(bitmap);
    }

    class ViewHolder {
        ImageView icon;
        TextView title, summary, stamp;
        RelativeLayout collection_item_layout;
    }
}
