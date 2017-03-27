package com.yulu.zhaoxinpeng.neteasenews.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.yulu.zhaoxinpeng.neteasenews.R;

public class AlphaActivity extends AppCompatActivity {

    private ImageView netease_first_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha);

        initActivity();
        //设置动画
        setAnimation();
    }
    //设置动画
    private void setAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getApplication(), R.anim.netease_first_iv_alpha);
        netease_first_iv.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            //设置动画结束后，要执行的操作
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(AlphaActivity.this, ViewPagerActivity.class);
                startActivity(intent);
                Log.e("intent++++++++++",intent+"");
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initActivity() {
        netease_first_iv = (ImageView) findViewById(R.id.netease_first_iv);
    }
}
