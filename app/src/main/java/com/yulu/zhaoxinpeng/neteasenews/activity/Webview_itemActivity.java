package com.yulu.zhaoxinpeng.neteasenews.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yulu.zhaoxinpeng.neteasenews.R;
import com.yulu.zhaoxinpeng.neteasenews.adapter.NewsAdapter;
import com.yulu.zhaoxinpeng.neteasenews.bean.NewsInfor;
import com.yulu.zhaoxinpeng.neteasenews.db.DBManager;

import java.lang.reflect.Field;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

import static android.R.id.list;
import static com.yulu.zhaoxinpeng.neteasenews.R.id.qq_login;
import static com.yulu.zhaoxinpeng.neteasenews.R.id.share_wechat_tv;

public class Webview_itemActivity extends AppCompatActivity {
    private ProgressBar webview_item_pro;
    private WebView webview_item_wv;
    private TextView webview_item_collect;
    private ImageView webview_item_back;
    private TextView webview_item_title;
    private String url1;
    private String[] strings;
    private int[] ints;
    private String summary;
    private String icon;
    private String stamp;
    private String title;
    private int nid;
    private String link;
    private int type;
    private NewsInfor newsInfor;
    private PopupWindow popupWindow;
    private ImageView share_iv;
    private RelativeLayout activity_webview_item;
    private TextView share_wechat_tv;
    private TextView share_wechat;
    private TextView share_qq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_item);

        //设置菜单溢出按钮
        getOverflowMenu();

        ShareSDK.initSDK(this);

        //初始化布局
        initactivity();
        webview_item_wv.loadUrl(link);
        //加载链接
        loadingLink();
        //设置进度条
        loadingprogressbar();
        //加入收藏
        setCollection();

    }

    //设置optionmenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webview_menu, menu);

        return true;
    }

    //菜单的点击监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menuItem1:
                Toast.makeText(Webview_itemActivity.this, "这是第一个菜单。", Toast.LENGTH_LONG)
                        .show();
                return true;
            case R.id.menuItem2:
                Toast.makeText(Webview_itemActivity.this, "这是第二个菜单。", Toast.LENGTH_LONG)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }

    //菜单溢出按钮
    private void getOverflowMenu() {
        ViewConfiguration viewConfig = ViewConfiguration.get(this);

        try {
            Field overflowMenuField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (null != overflowMenuField) {
                overflowMenuField.setAccessible(true);
                overflowMenuField.setBoolean(viewConfig, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View inflate;

    private void initPopupWindow() {
        if (popupWindow == null) {

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            inflate = layoutInflater.inflate(R.layout.popup_window_layout, null);
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels * 1 / 5;
            popupWindow = new PopupWindow(inflate, width, height);
            //设置 popupwindow 获取焦点
            popupWindow.setFocusable(true);


            popupWindow.setOutsideTouchable(true);
            popupWindow.setAnimationStyle(R.style.Popupwindow_Theme);

            popupWindow.setBackgroundDrawable(new BitmapDrawable());

            popupWindow.setTouchInterceptor(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        popupWindow.dismiss();
                        return true;
                    }
                    return false;
                }
            });
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                public void onDismiss() {
                    setBackgroundAlpha(1.0f);
                }
            });
        }
        popupWindow.showAtLocation(activity_webview_item, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        share_wechat_tv = (TextView) inflate.findViewById(R.id.share_wechat_tv);
        Drawable drawable_wechat_tv = getResources().getDrawable(R.drawable.share_wechatcircle);
        drawable_wechat_tv.setBounds(0, 0, 140, 140);
        share_wechat_tv.setCompoundDrawables(null, drawable_wechat_tv, null, null);

        share_wechat = (TextView) inflate.findViewById(R.id.share_wechat);
        Drawable drawable_wechat = getResources().getDrawable(R.drawable.share_wechat);
        drawable_wechat.setBounds(0, 0, 140, 140);
        share_wechat.setCompoundDrawables(null, drawable_wechat, null, null);

        share_qq = (TextView) inflate.findViewById(R.id.share_qq);
        Drawable drawable_qq = getResources().getDrawable(R.drawable.share_qq);
        drawable_qq.setBounds(0, 0, 140, 140);
        share_qq.setCompoundDrawables(null, drawable_qq, null, null);

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        this.getWindow().setAttributes(lp);
    }

    //加入收藏
    private void setCollection() {
        webview_item_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将数据添加到collections表中
                if (DBManager.getInstance(getApplication()).find(newsInfor.getTitle())) {
                    Toast.makeText(getApplication(), "本条数据在我的收藏中已经存在", Toast.LENGTH_SHORT).show();
                } else {
                    DBManager.getInstance(getApplication()).insertone(newsInfor);
                    Toast.makeText(getApplication(), "已收藏", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //设置进度条
    private void loadingprogressbar() {
        webview_item_wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                webview_item_pro.setProgress(newProgress);
                if (newProgress == 100) {
                    webview_item_pro.setVisibility(View.GONE);
                }
            }
        });
    }

    //加载链接
    private void loadingLink() {
        webview_item_wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    private void initactivity() {
        webview_item_pro = (ProgressBar) findViewById(R.id.webview_item_pro);
        webview_item_wv = (WebView) findViewById(R.id.webview_item_wv);
        webview_item_collect = (TextView) findViewById(R.id.webview_item_collect);
        webview_item_back = (ImageView) findViewById(R.id.webview_item_back);
        webview_item_title = (TextView) findViewById(R.id.webview_item_title);

        activity_webview_item = (RelativeLayout) findViewById(R.id.activity_webview_item);

        share_iv = (ImageView) findViewById(R.id.share_iv);

        webview_item_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        share_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* //初始化popupwindow
                initPopupWindow();
                setBackgroundAlpha(0.7f);//设置屏幕透明度*/
                showShare();

            }
        });

        strings = getIntent().getStringArrayExtra("string");
        summary = strings[0];
        icon = strings[1];
        stamp = strings[2];
        title = strings[3];
        link = strings[4];

        ints = getIntent().getIntArrayExtra("int");
        nid = ints[0];
        type = ints[1];

        newsInfor = new NewsInfor(summary, icon, stamp, title, nid, link, type);

        webview_item_title.setText(title);


    }

    //shareSDK 分享
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, cn.sharesdk.framework.Platform.ShareParams paramsToShare) {
                if ("QZone".equals(platform.getName())) {
                    paramsToShare.setTitle(null);
                    paramsToShare.setTitleUrl(null);
                }
                if ("SinaWeibo".equals(platform.getName())) {
                    paramsToShare.setUrl(null);
                    paramsToShare.setText("分享文本 http://www.baidu.com");
                }
                if ("Wechat".equals(platform.getName())) {
                    Bitmap imageData = BitmapFactory.decodeResource(getResources(), R.drawable.ssdk_logo);
                    paramsToShare.setImageData(imageData);
                }
                if ("WechatMoments".equals(platform.getName())) {
                    Bitmap imageData = BitmapFactory.decodeResource(getResources(), R.drawable.ssdk_logo);
                    paramsToShare.setImageData(imageData);
                }

            }
        });

// 启动分享GUI
        oks.show(this);
    }

}
