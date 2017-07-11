package com.funs.appreciate.art.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.base.BaseActivity;
import com.funs.appreciate.art.model.entitys.DetailEntity;
import com.funs.appreciate.art.model.entitys.LayoutModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by yc on 2017/6/26.
 * 详情
 */

public class DetailActivity extends BaseActivity{
    String content  , type;
    ImageView browse_iv;
    ImageButton  img_left,img_right;
    String urls[];//图片数组
    static int picIndex;// 显示图片index
    TextView detail_title_tv,detail_content_tv;
    int special;// 专题tag
    List<LayoutModel.LayoutBean.ContentBean> cbs;// 专题数据
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_browse);

        browse_iv = (ImageView) findViewById(R.id.browse_iv);
        img_left = (ImageButton) findViewById(R.id.img_left);
        img_right = (ImageButton) findViewById(R.id.img_right);
        detail_title_tv = (TextView) findViewById(R.id.detail_title_tv);
        detail_content_tv = (TextView) findViewById(R.id.detail_content_tv);

        loadContent();

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            int keyCode = event.getKeyCode();
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    if(img_left.isShown()) {
                        int leftIndex = getLeftShow();
                        if(leftIndex != picIndex) {
                            picIndex = leftIndex;
                            setViewVisibility();
                            if(special == -1) {
                                glideImg();
                            } else {
                                glideImg(cbs);
                            }
                        }
                    }
                    return true;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    if(img_right.isShown()) {
                        int rightIndex = getRightShow();
                        if(rightIndex != picIndex) {
                            picIndex = rightIndex;
                            setViewVisibility();
                            if(special == -1) {
                                glideImg();
                            } else {
                                glideImg(cbs);
                            }
                        }
                    }
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public void loadContent() {
        content = this.getIntent().getStringExtra("content");
        type = this.getIntent().getStringExtra("type");
        picIndex = this.getIntent().getIntExtra("picIndex",0);
        special = this.getIntent().getIntExtra("special",-1);

        if(content != null) {
            if(special == -1) {
                DetailEntity de = new Gson().fromJson(content, DetailEntity.class);
                DetailEntity.DataBean cb = de.getData();
                try {
                    String picUrl = cb.getDatajson();
                    detail_title_tv.setText(cb.getName());
                    detail_content_tv.setText(cb.getRemark());
                    if ("0".equals(type)) {
                        if (picUrl.contains(";")) {
                            urls = picUrl.split(";");
                            setViewVisibility();
                            glideImg();
                        } else {
                            Glide.with(this).load(picUrl).error(R.drawable.bg_err).into(browse_iv);
                        }
                    } else if ("1".equals(type)) {
                        closeScreenService();
                        Intent intent = new Intent(this, VideoActivity.class);
                        intent.putExtra("videoUrl", picUrl);
                        startActivity(intent);
                        finish();
                    } else if ("3".equals(type)) {
                        Intent intent = new Intent(this, WebActivity.class);
                        intent.putExtra("art_url", picUrl);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else { // 专题数据展示
                Type type = new TypeToken<List<LayoutModel.LayoutBean.ContentBean>>(){}.getType();
                cbs = new Gson().fromJson(content, type);
                urls = getImages(cbs);
                setViewVisibility();
                glideImg(cbs);
            }
        }
    }

    private void setViewVisibility() {
//        if(urls.length > 1) {
//            if (picIndex >= 0 && picIndex < urls.length - 1) {
//                img_right.setVisibility(View.VISIBLE);
//            } else {
//                img_right.setVisibility(View.GONE);
//            }
//
//            if (picIndex >= 1 && picIndex < urls.length) {
//                img_left.setVisibility(View.VISIBLE);
//            } else {
//                img_left.setVisibility(View.GONE);
//            }
//        } else {
//            img_right.setVisibility(View.GONE);
//            img_left.setVisibility(View.GONE);
//        }
        if(urls.length > 1) {
            img_right.setVisibility(View.VISIBLE);
            img_left.setVisibility(View.VISIBLE);
        } else {
            img_right.setVisibility(View.GONE);
            img_left.setVisibility(View.GONE);
        }
    }

    private void glideImg() {
        if(urls != null && urls.length >= 1)
            Glide.with(this).load(urls[picIndex]).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.bg_err).into(browse_iv);
    }

    private void glideImg(List<LayoutModel.LayoutBean.ContentBean> cbs) {
        glideImg();

        if(cbs != null) {
            LayoutModel.LayoutBean.ContentBean cb = cbs.get(picIndex);
            detail_title_tv.setText(cb.getName());
            detail_content_tv.setText(cb.getRemark());
        }
    }

    public String[] getImages(List<LayoutModel.LayoutBean.ContentBean> list){
        String arr[] = null;
        if(list != null) {
            arr = new String[list.size()];
            for(int i = 0 ; i < list.size() ; i++){
                arr[i] = list.get(i).getSurfaceimage();
            }
        }
        return arr;
    }
    private int getRightShow(){
        int temp = picIndex;
        temp = ++ temp;
        if(temp >= urls.length ){
            temp = -- temp;
        } else {
            temp = 0;
        }
        return temp;
    }

    private int getLeftShow(){
        int temp = picIndex;
        temp = -- temp;
        if(temp >= 0 && temp < urls.length){
            temp = urls.length -1;
        } else{
            temp = ++ temp;
        }
        return temp;
    }
}
