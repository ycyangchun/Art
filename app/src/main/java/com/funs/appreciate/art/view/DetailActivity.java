package com.funs.appreciate.art.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.funs.appreciate.art.ArtApp;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.base.BaseActivity;
import com.funs.appreciate.art.di.components.DaggerDetailComponent;
import com.funs.appreciate.art.di.modules.ContentModule;
import com.funs.appreciate.art.model.entitys.DetailEntity;
import com.funs.appreciate.art.presenter.ContentContract;
import com.funs.appreciate.art.presenter.ContentPresenter;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * Created by yc on 2017/6/26.
 * 详情
 */

public class DetailActivity extends BaseActivity implements ContentContract.View{

    @Inject
    ContentPresenter contentPresenter;

    String contentId  , type;
    ImageView browse_iv;
    ImageButton  img_left,img_right;
    String urls[];
    static int picIndex;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_browse);
        DaggerDetailComponent.builder()
                .netComponent(ArtApp.get(this).getNetComponent())
                .contentModule(new ContentModule(this))
                .build().inject(this);
        contentId = this.getIntent().getStringExtra("contentId");
        type = this.getIntent().getStringExtra("type");
        browse_iv = (ImageView) findViewById(R.id.browse_iv);
        img_left = (ImageButton) findViewById(R.id.img_left);
        img_right = (ImageButton) findViewById(R.id.img_right);
        if(contentId != null) {
            contentPresenter.loadLayout("getContentDetail", contentId);
        }
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


    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void loadLayoutSuccess(String lay) {
        if(lay != null) {
            DetailEntity de = new Gson().fromJson(lay, DetailEntity.class);
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
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("art_url",picUrl);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void loadLayoutFailed(Throwable throwable) {

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
