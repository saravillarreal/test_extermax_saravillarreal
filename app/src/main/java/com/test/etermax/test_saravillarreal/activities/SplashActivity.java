package com.test.etermax.test_saravillarreal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.test.etermax.test_saravillarreal.R;

/**
 * Created by saravillarreal on 4/13/17.
 */

public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        showDelay();
    }


    private void showDelay() {
        int secondsDelayed = 3;

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();

                }
            }, secondsDelayed * 1000);

    }
}
