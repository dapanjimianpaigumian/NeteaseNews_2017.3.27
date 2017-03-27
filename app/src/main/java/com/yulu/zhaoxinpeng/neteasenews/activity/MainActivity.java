package com.yulu.zhaoxinpeng.neteasenews.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yulu.zhaoxinpeng.neteasenews.R;
import com.yulu.zhaoxinpeng.neteasenews.adapter.MFragmentPagerAdapter;
import com.yulu.zhaoxinpeng.neteasenews.fragment.EntertainmentFragment;
import com.yulu.zhaoxinpeng.neteasenews.fragment.LifeFragment;
import com.yulu.zhaoxinpeng.neteasenews.fragment.MillitaryFragment;
import com.yulu.zhaoxinpeng.neteasenews.fragment.NewsFragment;
import com.yulu.zhaoxinpeng.neteasenews.fragment.ScienceFragment;
import com.yulu.zhaoxinpeng.neteasenews.fragment.SocietyFragment;

import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.login.demo.login.LoginApi;
import cn.sharesdk.login.demo.login.OnLoginListener;
import cn.sharesdk.login.demo.login.UserInfo;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 主界面显示，包括图片、电影和音乐三个选项
 *
 * @author weizhi
 * @version 1.0
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    RadioButton news_radiobutton;
    RadioButton millit_radiobutton;
    RadioButton society_radiobutton;
    RadioButton science_radiobutton;
    RadioButton entertainment_radiobutton;
    RadioButton life_radiobutton;

    ImageView homopage_title_home_iv;
    ImageView homopage_title_share_iv;

    //实现Tab滑动效果
    private ViewPager mViewPager;

    //动画图片
    private ImageView cursor;

    //动画图片偏移量
    private int offset = 0;
    private int position_one;
    private int position_two;
    private int position_three;
    private int position_four;
    private int position_five;

    //动画图片宽度
    private int bmpW;

    //当前页卡编号
    private int currIndex = 0;

    //存放Fragment
    private ArrayList<Fragment> fragmentArrayList;

    //管理Fragment
    private FragmentManager fragmentManager;

    public Context context;

    public static final String TAG = "MainActivity";
    private SlidingMenu slidingMenu;
    private int screenW;
    private TextView slidinglayout_night_mode;
    private TextView slidinglayout_setting;
    private TextView slidinglayout_exit;
    private TextView slidinglayout_collection;
    private TextView slidinglayout_offline;
    private TextView slidinglayout_follow;
    private RelativeLayout slidinglayout_middle_offline;
    RelativeLayout slidinglayout_middle_collection;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private DrawerLayout drawerLayout;
    private ImageView back_iv;
    private LinearLayout drawer_linerlayout;
    private com.yulu.zhaoxinpeng.neteasenews.view.RoundImageView roundImageView;
    private TextView qq_login;
    private TextView wechat_login;
    private TextView sina_login;
    private ImageView share_iv;

    private RelativeLayout parent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context = this;

        initactivity();
        //初始化 侧滑
        ininSlidingMenu();
        //初始化抽屉
        initdrawerlayout();
        //初始化 按钮
        InitRadionButton();
        //初始化ImageView
        InitImageView();
        //初始化Fragment
        InitFragment();
        //初始化ViewPager
        InitViewPager();
        //绑定监听
        setOnClick();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    //绑定监听
    private void setOnClick() {
        homopage_title_share_iv.setOnClickListener(MainActivity.this);
        back_iv.setOnClickListener(MainActivity.this);
        qq_login.setOnClickListener(this);
        sina_login.setOnClickListener(this);
        wechat_login.setOnClickListener(this);

    }

    //初始化抽屉
    private void initdrawerlayout() {

        ShareSDK.initSDK(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        drawer_linerlayout = (LinearLayout) findViewById(R.id.drawer_linerlayout);

        back_iv = (ImageView) findViewById(R.id.back_iv);

        share_iv = (ImageView) findViewById(R.id.share_iv);

        ViewGroup.LayoutParams layoutParams = drawer_linerlayout.getLayoutParams();
        //设置抽屉的宽度
        layoutParams.width = getResources().getDisplayMetrics().widthPixels * 1;

        drawer_linerlayout.setLayoutParams(layoutParams);


        qq_login = (TextView) findViewById(R.id.qq_login);
        Drawable drawable_qq_login = getResources().getDrawable(R.drawable.biz_news_share_qq);
        drawable_qq_login.setBounds(0, 0, 160, 160);
        qq_login.setCompoundDrawables(null, drawable_qq_login, null, null);

        wechat_login = (TextView) findViewById(R.id.wechat_login);
        Drawable drawable_wechat_login = getResources().getDrawable(R.drawable.biz_news_share_wx);
        drawable_wechat_login.setBounds(0, 0, 160, 160);
        wechat_login.setCompoundDrawables(null, drawable_wechat_login, null, null);

        sina_login = (TextView) findViewById(R.id.sina_login);
        Drawable drawable_sina_login = getResources().getDrawable(R.drawable.biz_news_share_sina);
        drawable_sina_login.setBounds(0, 0, 160, 160);
        sina_login.setCompoundDrawables(null, drawable_sina_login, null, null);


    }

    //设置侧滑菜单
    private void ininSlidingMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setMenu(R.layout.slidingmenu_left);
        slidingMenu.setFadeDegree(0.35f);//SlidingMenu滑动时的渐变程度
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setTouchModeBehind(SlidingMenu.LEFT);
        slidingMenu
                .setBehindWidth(getResources().getDisplayMetrics().widthPixels / 6 * 5);
        /**
         * 注意：找到slidingMenu侧滑菜单中的控件
         */
        slidinglayout_middle_collection = (RelativeLayout) slidingMenu.findViewById(R.id.slidinglayout_middle_collection);
        slidinglayout_middle_collection.setOnClickListener(this);

        roundImageView = (com.yulu.zhaoxinpeng.neteasenews.view.RoundImageView) slidingMenu.findViewById(R.id.roundimageview);
        roundImageView.setOnClickListener(this);

        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        homopage_title_home_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        //控制夜间模式图标大小
        slidinglayout_night_mode = (TextView) findViewById(R.id.slidinglayout_night_mode);
        Drawable drawable_mode = getResources().getDrawable(R.drawable.slidinglayout_night_mode);
        drawable_mode.setBounds(0, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        slidinglayout_night_mode.setCompoundDrawables(drawable_mode, null, null, null);//只放左边

        //控制设置图标大小
        slidinglayout_setting = (TextView) findViewById(R.id.slidinglayout_setting);
        Drawable drawable_setting = getResources().getDrawable(R.drawable.slidinglayout_setting);
        drawable_setting.setBounds(0, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        slidinglayout_setting.setCompoundDrawables(drawable_setting, null, null, null);//只放左边

        //控制退出图标大小
        slidinglayout_exit = (TextView) findViewById(R.id.slidinglayout_exit);
        Drawable drawable_exit = getResources().getDrawable(R.drawable.slidinglayout_exit);
        drawable_exit.setBounds(0, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        slidinglayout_exit.setCompoundDrawables(drawable_exit, null, null, null);//只放左边

        //控制收藏图标大小
        slidinglayout_collection = (TextView) findViewById(R.id.slidinglayout_collection);
        Drawable drawable_collection = getResources().getDrawable(R.drawable.slidinglayout_collection_red);
        drawable_collection.setBounds(0, 0, 76, 76);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        slidinglayout_collection.setCompoundDrawables(drawable_collection, null, null, null);//只放左边

        // 控制离线浏览图标大小
        slidinglayout_offline = (TextView) findViewById(R.id.slidinglayout_offline);
        Drawable drawable_offline = getResources().getDrawable(R.drawable.slidinglayout_offline_red);
        drawable_offline.setBounds(0, 0, 76, 76);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        slidinglayout_offline.setCompoundDrawables(drawable_offline, null, null, null);//只放左边
        // 控制跟帖浏览图标大小
        slidinglayout_follow = (TextView) findViewById(R.id.slidinglayout_follow);
        Drawable drawable_follow = getResources().getDrawable(R.drawable.slidinglayout_follow_red);
        drawable_follow.setBounds(0, 0, 76, 76);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        slidinglayout_follow.setCompoundDrawables(drawable_follow, null, null, null);//只放左边


    }

    /**
     * 初始化分享、主界面按钮
     */
    private void initactivity() {
        homopage_title_home_iv = (ImageView) findViewById(R.id.homopage_title_home_iv);
        homopage_title_share_iv = (ImageView) findViewById(R.id.homopage_title_share_iv);
        parent = (RelativeLayout) findViewById(R.id.third_party_login);

    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onResume() {
        /**
         * 设置为竖屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        super.onResume();
    }

    /**
     * 初始化头标
     */
    private void InitRadionButton() {

        //新闻头标
        news_radiobutton = (RadioButton) findViewById(R.id.news_radiobutton);
        //军事头标
        millit_radiobutton = (RadioButton) findViewById(R.id.millit_radiobutton);
        //社会头标
        society_radiobutton = (RadioButton) findViewById(R.id.society_radiobutton);
        //科技头标
        science_radiobutton = (RadioButton) findViewById(R.id.science_radiobutton);
        //娱乐头标
        entertainment_radiobutton = (RadioButton) findViewById(R.id.entertainment_radiobutton);
        //生活头标
        life_radiobutton = (RadioButton) findViewById(R.id.life_radiobutton);


        //添加点击事件
        news_radiobutton.setOnClickListener(new MyOnClickListener(0));
        millit_radiobutton.setOnClickListener(new MyOnClickListener(1));
        society_radiobutton.setOnClickListener(new MyOnClickListener(2));
        life_radiobutton.setOnClickListener(new MyOnClickListener(3));
        entertainment_radiobutton.setOnClickListener(new MyOnClickListener(4));
        science_radiobutton.setOnClickListener(new MyOnClickListener(5));
    }

    /**
     * 初始化页卡内容区
     */
    private void InitViewPager() {

        mViewPager = (ViewPager) findViewById(R.id.vPager);
        mViewPager.setAdapter(new MFragmentPagerAdapter(fragmentManager, fragmentArrayList));

        //让ViewPager缓存2个页面
        mViewPager.setOffscreenPageLimit(6);

        //设置默认打开第一页
        mViewPager.setCurrentItem(0);

        //将顶部文字恢复默认值
        resetTextViewTextColor();
        news_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));

        //设置viewpager页面滑动监听事件
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 初始化动画
     */

    private void InitImageView() {

        cursor = (ImageView) findViewById(R.id.cursor);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // 获取分辨率宽度
        screenW = dm.widthPixels;

        bmpW = (screenW / 6);

        //设置动画图片宽度
        setBmpW(cursor, bmpW);
        offset = 0;

        //动画图片偏移量赋值
        position_one = (int) (screenW / 6.0);
        position_two = position_one * 2;
        position_three = position_one * 3;
        position_four = position_one * 4;
        position_five = position_one * 5;

    }

    /**
     * 初始化Fragment，并添加到ArrayList中
     */
    private void InitFragment() {
        fragmentArrayList = new ArrayList<Fragment>();

        fragmentArrayList.add(new NewsFragment());
        fragmentArrayList.add(new MillitaryFragment());
        fragmentArrayList.add(new SocietyFragment());
        fragmentArrayList.add(new LifeFragment());
        fragmentArrayList.add(new EntertainmentFragment());
        fragmentArrayList.add(new ScienceFragment());


        fragmentManager = getSupportFragmentManager();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.slidinglayout_middle_collection:
                Intent intent = new Intent(MainActivity.this, SlidingCollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.homopage_title_share_iv:
                drawerLayout.openDrawer(drawer_linerlayout);
                break;
            case R.id.back_iv:
                drawerLayout.closeDrawer(drawer_linerlayout);
                break;
            case R.id.roundimageview:
                drawerLayout.openDrawer(drawer_linerlayout);
                break;
            case R.id.qq_login:
                login(QQ.NAME);
                break;
            case R.id.wechat_login:
                login(Wechat.NAME);
                break;
            case R.id.sina_login:
                login(SinaWeibo.NAME);
                break;
            default:
                break;
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    /**
     * 头标点击监听
     *
     * @author weizhi
     * @version 1.0
     */
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(index);
        }
    }

    /**
     * 页卡切换监听
     *
     * @author weizhi
     * @version 1.0
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            Animation animation = null;
            //position为跳转目标页面，
            switch (position) {

                //跳至目标页卡1的过程中要执行的逻辑
                case 0:
                    //从页卡2跳转转到页卡1
                    if (currIndex == 1) {
                        /**
                         * 参数一：跳转目标页卡
                         * 参数二：当前页卡
                         */
                        animation = new TranslateAnimation(position_one, 0, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 0", "跳到2");
                        news_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 2) {//从页卡3跳转转到页卡1
                        animation = new TranslateAnimation(position_two, 0, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 0", "跳到3");
                        news_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(position_three, 0, 0, 0);
                        resetTextViewTextColor();
                        news_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 4) {
                        animation = new TranslateAnimation(position_four, 0, 0, 0);
                        resetTextViewTextColor();
                        news_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 5) {
                        animation = new TranslateAnimation(position_five, 0, 0, 0);
                        resetTextViewTextColor();
                        news_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    }
                    break;

                //跳至目标页卡2的过程中要执行的逻辑
                case 1:
                    //从页卡1跳转转到页卡2
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_one, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 1", "跳到1");
                        millit_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 2) { //从页卡3跳转转到页卡2
                        animation = new TranslateAnimation(position_two, position_one, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 1", "跳到3");
                        millit_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 3) { //从页卡4跳转转到页卡2
                        animation = new TranslateAnimation(position_three, position_one, 0, 0);
                        resetTextViewTextColor();
                        millit_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 4) {//从页卡5跳转转到页卡1
                        animation = new TranslateAnimation(position_four, position_one, 0, 0);
                        resetTextViewTextColor();
                        millit_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 5) {//从页卡6跳转转到页卡2
                        animation = new TranslateAnimation(position_five, position_one, 0, 0);
                        resetTextViewTextColor();
                        millit_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    }
                    break;

                //跳至目标页卡3的过程中要执行的逻辑
                case 2:
                    //从页卡1跳转转到页卡3
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_two, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 2", "跳到1");
                        society_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 1) {//从页卡2跳转转到页卡3
                        animation = new TranslateAnimation(position_one, position_two, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 2", "跳到2");
                        society_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 3) {//从页卡4跳转转到页卡3
                        animation = new TranslateAnimation(position_three, position_two, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 2", "跳到2");
                        society_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 4) {//从页卡5跳转转到页卡3
                        animation = new TranslateAnimation(position_four, position_two, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 2", "跳到2");
                        society_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 5) {//从页卡6跳转转到页卡3
                        animation = new TranslateAnimation(position_five, position_two, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 2", "跳到2");
                        society_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    }
                    break;
                //跳至目标页卡4的过程中要执行的逻辑
                case 3:
                    if (currIndex == 0) {//从页卡1跳转转到页卡4
                        animation = new TranslateAnimation(offset, position_three, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 3", "1跳到4");
                        life_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 1) {//从页卡2跳转转到页卡4
                        animation = new TranslateAnimation(position_one, position_three, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 3", "2跳到4");
                        life_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 2) {//从页卡3跳转转到页卡4
                        animation = new TranslateAnimation(position_two, position_three, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 3", "3跳到4");
                        life_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 4) {//从页卡5跳转转到页卡4
                        animation = new TranslateAnimation(position_four, position_three, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 3", "5跳到4");
                        life_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 5) {//从页卡6跳转转到页卡4
                        animation = new TranslateAnimation(position_five, position_three, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 3", "6跳到4");
                        life_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    }
                    break;
                //跳至目标页卡5的过程中要执行的逻辑
                case 4:
                    if (currIndex == 0) {//从页卡1跳转转到页卡5
                        animation = new TranslateAnimation(offset, position_four, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 4", "1跳到5");
                        entertainment_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 1) {//从页卡2跳转转到页卡5
                        animation = new TranslateAnimation(position_one, position_four, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 4", "2跳到5");
                        entertainment_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 2) {//从页卡3跳转转到页卡5
                        animation = new TranslateAnimation(position_two, position_four, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 4", "3跳到5");
                        entertainment_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 3) {//从页卡4跳转转到页卡5
                        animation = new TranslateAnimation(position_three, position_four, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 4", "4跳到5");
                        entertainment_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 5) {//从页卡6跳转转到页卡5
                        animation = new TranslateAnimation(position_five, position_four, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 4", "6跳到5");
                        entertainment_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    }
                    break;
                //当前为页卡6
                case 5:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_five, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 5", "1跳到6");
                        science_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, position_five, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 5", "2跳到6");
                        science_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(position_two, position_five, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 5", "3跳到6");
                        science_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(position_three, position_five, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 5", "4跳到6");
                        science_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 4) {
                        animation = new TranslateAnimation(position_four, position_five, 0, 0);
                        resetTextViewTextColor();
                        Log.e("case 5", "5跳到6");
                        science_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    }
                    break;

            }
            currIndex = position;

            animation.setFillAfter(true);// true:图片停在动画结束位置
            animation.setDuration(300);
            cursor.startAnimation(animation);

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    /**
     * 设置动画图片宽度
     *
     * @param mWidth
     */
    private void setBmpW(ImageView imageView, int mWidth) {
        ViewGroup.LayoutParams para;
        para = imageView.getLayoutParams();
        para.width = mWidth;
        imageView.setLayoutParams(para);
    }

    /**
     * 将顶部文字恢复默认值
     */
    private void resetTextViewTextColor() {

        news_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color));
        millit_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color));
        society_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color));
        entertainment_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color));
        science_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color));
        life_radiobutton.setTextColor(getResources().getColor(R.color.main_top_tab_color));
    }

    //对侧滑菜单 监听返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (slidingMenu.isMenuShowing()) {

                slidingMenu.toggle();
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return false;
    }

    /*
     * 演示执行第三方登录/注册的方法
	 * <p>
	 * 这不是一个完整的示例代码，需要根据您项目的业务需求，改写登录/注册回调函数
	 *
	 * @param platformName 执行登录/注册的平台名称，如：SinaWeibo.NAME
	 */
    private void login(String platformName) {
        LoginApi api = new LoginApi();
        //设置登陆的平台后执行登陆的方法
        api.setPlatform(platformName);
        api.setOnLoginListener(new OnLoginListener() {
            public boolean onLogin(String platform, HashMap<String, Object> res) {
                // 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
                // 此处全部给回需要注册
                return true;
            }

            public boolean onRegister(UserInfo info) {
                // 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
                return true;
            }
        });
        api.login(this);
    }
}