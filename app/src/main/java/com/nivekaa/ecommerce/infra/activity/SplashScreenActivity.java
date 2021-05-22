package com.nivekaa.ecommerce.infra.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nivekaa.ecommerce.application.AbstractAppActivity;
import com.nivekaa.ecommerce.R;
import com.nivekaa.ecommerce.domain.spi.SessionStoragePort;
import com.nivekaa.ecommerce.infra.storage.SessionStorage;

import butterknife.BindView;

public class SplashScreenActivity extends AbstractAppActivity {
    private Animation animation;
    @BindView(R.id.logo_img) ImageView logo;
    @BindView(R.id.track_txt) TextView appTitle;
    @BindView(R.id.pro_txt) TextView appSlogan;

    public final static String FIRST_TIME = "is_my_first_time_ID";
    private SessionStoragePort sessionStoragePort;

    @Override
    protected boolean hasErrorView() {
        return false;
    }

    @Override
    protected boolean hasToolBar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sessionStoragePort = SessionStorage.getInstance(this);

        String fontPath = "font/CircleD_Font_by_CrazyForMusic.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        appTitle.setTypeface(tf);
        appSlogan.setTypeface(tf);
        boolean isFirstTime = sessionStoragePort.retrieveDataBoolean(FIRST_TIME);
        if (isFirstTime) {
            if (savedInstanceState == null) {
                flyIn();
            }
            sessionStoragePort.saveDataBoolean(FIRST_TIME, true);
            new Handler().postDelayed(this::endSplash, 3500);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void flyIn() {
        animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        logo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_animation);
        appTitle.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.pro_animation);
        appSlogan.startAnimation(animation);
    }

    private void endSplash() {
        animation = AnimationUtils.loadAnimation(this,
                R.anim.logo_animation_back);
        logo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_animation_back);
        appTitle.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.pro_animation_back);
        appSlogan.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {

                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }
}