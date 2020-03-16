package com.lxkj.jieju.Activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jieju.App;
import com.lxkj.jieju.MainActivity;
import com.lxkj.jieju.R;

public class AdvertisingActivity extends AppCompatActivity {
    private ImageView im_guangao;
    private String url;
    private TextView tv_skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertising);
        im_guangao = findViewById(R.id.im_guangao);
        tv_skip = findViewById(R.id.tv_skip);
        url = getIntent().getStringExtra("url");



        CountDownTimer timer = new CountDownTimer(3 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_skip.setText("跳过"+millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                tv_skip.setText("跳过");
                Intent intent = new Intent(AdvertisingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };

        timer.start();

        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.logo)
                .placeholder(R.mipmap.logo))
                .load(url)
                .into(im_guangao);

        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdvertisingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
