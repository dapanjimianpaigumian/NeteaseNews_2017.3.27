package com.yulu.zhaoxinpeng.neteasenews.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.yulu.zhaoxinpeng.neteasenews.R;
import com.yulu.zhaoxinpeng.neteasenews.adapter.GuidanceAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager guidance_viewpager;
    private Button skip_button;
    private List<View> list;
    private GuidanceAdapter guidanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        SharedPreferences sp2 = getSharedPreferences("viewpager",
                Context.MODE_PRIVATE);
        Boolean isFirst = sp2.getBoolean("if_is_first", true);

        if (isFirst) {
            initActivity();

            setAdapter();

            setData();

            setviewpagerListener();
        }else {
            Intent intent = new Intent(ViewPagerActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

    private void setviewpagerListener() {
        guidance_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position > 1) {
                    skip_button.setVisibility(View.VISIBLE);
                } else {
                    skip_button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setData() {
        list = new ArrayList<View>();

        View one = LayoutInflater.from(ViewPagerActivity.this).inflate(R.layout.viewpager_guidance_one, null);
        View two = LayoutInflater.from(ViewPagerActivity.this).inflate(R.layout.viewpager_guidance_two, null);
        View three = LayoutInflater.from(ViewPagerActivity.this).inflate(R.layout.viewpager_guidance_three, null);
        //View four = LayoutInflater.from(ViewPagerActivity.this).inflate(R.layout.viewpager_guidance_four, null);
        //View five = LayoutInflater.from(ViewPagerActivity.this).inflate(R.layout.viewpager_guidance_five, null);

        list.add(one);
        list.add(two);
        list.add(three);
        //list.add(four);
        //list.add(five);

        guidanceAdapter.setDataList(list);
        guidanceAdapter.notifyDataSetChanged();
    }

    private void setAdapter() {
        guidanceAdapter = new GuidanceAdapter();
        guidance_viewpager.setAdapter(guidanceAdapter);
    }

    private void initActivity() {
        guidance_viewpager = (ViewPager) findViewById(R.id.guidance_viewpager);
        skip_button = (Button) findViewById(R.id.skip_button);
    }

    public void onSkipClick(View view){
        Intent intent = new Intent(ViewPagerActivity.this, MainActivity.class);
        startActivity(intent);

        SharedPreferences sp = getSharedPreferences("viewpager",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean("if_is_first", false);
        ed.commit();

        finish();
    }
}
