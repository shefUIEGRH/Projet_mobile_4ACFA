package com.example.projetmobile_4acfa.splash;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.projetmobile_4acfa.R;
import com.example.projetmobile_4acfa.views.MainActivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.splash_activity);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);

        progressBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(230, 35, 100)));

        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                startApp();
                finish();
            }
        }).start();
    }

    private void doWork() {
        for(int progress = 0; progress < 100; progress+=10){
            try {
                Thread.sleep(1000);
                progressBar.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void startApp(){


        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);

    }
}

