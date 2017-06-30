package com.funs.appreciate.art.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.base.BaseActivity;
import com.funs.appreciate.art.model.entitys.DetailEntity;
import com.google.gson.Gson;

/**
 * Created by yc on 2017/6/26.
 * 详情
 */

public class DetailActivity extends BaseActivity{
    String content  , type;
    ImageView browse_iv;
    ImageButton  img_left,img_right;
    String urls[];
    static int picIndex;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_browse);

        browse_iv = (ImageView) findViewById(R.id.browse_iv);
        img_left = (ImageButton) findViewById(R.id.img_left);
        img_right = (ImageButton) findViewById(R.id.img_right);
        loadContent();

        browse_iv.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            if(img_left.isShown()) {
                                picIndex = getLeftShow();
                                System.out.println("======= left ========>" + picIndex);
                            }
                            return true;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            if(img_right.isShown()) {
                                picIndex = getRightShow();
                                System.out.println("======= right ========>" + picIndex);
                            }
                            return true;
                    }
                }
                return false;
            }
        });
    }

    public void loadContent() {
        content = this.getIntent().getStringExtra("content");
        type = this.getIntent().getStringExtra("type");
        if(content != null) {
            DetailEntity de = new Gson().fromJson(content, DetailEntity.class);
            DetailEntity.DataBean cb = de.getData();
            String picUrl = cb.getDatajson();
            if("0".equals(type)) {
                if (picUrl.contains(";")) {
                    urls = picUrl.split(";");
                    if (urls.length > 1) {
                        img_left.setVisibility(View.VISIBLE);
                        img_right.setVisibility(View.VISIBLE);
                    }
                    picIndex = 0;
                    Glide.with(this).load(urls[picIndex]).error(R.drawable.bg_splash).into(browse_iv);
                } else {
                    Glide.with(this).load(picUrl).error(R.drawable.bg_splash).into(browse_iv);
                }
            }else if("1".equals(type)){
                closeScreenService();
                Intent intent = new Intent(this, VideoActivity.class);
                intent.putExtra("videoUrl",picUrl);
                startActivity(intent);
                finish();
            }else if("3".equals(type)){
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("art_url",picUrl);
                startActivity(intent);
                finish();
            }
        }
    }

    private int getRightShow(){
        int temp = ++picIndex;
        if(temp > urls.length ){
            picIndex = 0;
        } else{
            picIndex = temp;
        }
        return picIndex;
    }

    private int getLeftShow(){
        int temp = --picIndex;
        if(temp >= 0 && temp < urls.length){
            picIndex = temp;
        } else{
            picIndex = urls.length -1;
        }
        return picIndex;
    }
}
