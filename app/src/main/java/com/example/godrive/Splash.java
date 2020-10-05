package com.example.godrive;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    private ImageView imageView;
    private ImageView imageViewcar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = (ImageView) findViewById(R.id.logosplash);
        imageViewcar = (ImageView) findViewById(R.id.carsplash);

        ObjectAnimator animation = ObjectAnimator.ofFloat(imageView, "translationY", -80f);
        animation.setDuration(700);


        ObjectAnimator animation2 = ObjectAnimator.ofFloat(imageViewcar, "translationX", 1400f);
        animation2.setDuration(1800);
        animation2.start();
        animation.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i=new Intent(Splash.this,main.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}