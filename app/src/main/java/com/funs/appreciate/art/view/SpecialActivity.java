package com.funs.appreciate.art.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.funs.appreciate.art.ArtApp;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.base.BaseActivity;
import com.funs.appreciate.art.di.components.DaggerSpecialComponent;
import com.funs.appreciate.art.di.modules.MainModule;
import com.funs.appreciate.art.model.entitys.ContentEntity;
import com.funs.appreciate.art.model.entitys.LayoutModel;
import com.funs.appreciate.art.model.entitys.PictureModel;
import com.funs.appreciate.art.presenter.MainContract;
import com.funs.appreciate.art.presenter.MainPresenter;
import com.funs.appreciate.art.view.widget.PictureFocusRelative;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by yc on 2017/6/26.
 * 专题
 */

public class SpecialActivity extends BaseActivity implements  PictureFocusRelative.PictureFocusKeyEvent ,MainContract.View{
    List<PictureModel> lms = new ArrayList<>();
    public  PictureFocusRelative fr;// content view

    @Inject
    MainPresenter mainPresenter;

    String content;
    ImageView iv_special;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);
        loadContent();

        DaggerSpecialComponent.builder()
                .mainModule(new MainModule(this))
                .netComponent(ArtApp.get(this).getNetComponent())
                .build().inject(this);
    }

    /////////// PictureFocusKeyEvent ↓↓↓↓↓↓

    @Override
    public void pictureListener(String keyType, PictureModel lm , int index) {

        if("top".equals(keyType)) {

        } else if("left".equals(keyType)) {
            System.out.println("========left==========>");
        } else if("right".equals(keyType)) {
            System.out.println("========right==========>");
        } else if("center".equals(keyType)) {
            List<LayoutModel.LayoutBean.ContentBean> contents = lm.getContentBean();
            if(contents != null) {
                LayoutModel.LayoutBean.ContentBean contentBean = contents.get(index);
                String type = contentBean.getType();
                String contentId = contentBean.getId();
                if("0".equals(type)){//图片
                    String json = new Gson().toJson(contents);
                    Intent intent = new Intent(this, DetailActivity.class);
                    intent.putExtra("content", json);
                    intent.putExtra("type", type);
                    intent.putExtra("picIndex", index);
                    intent.putExtra("special", 100);
                    startActivity(intent);
                } else if("1".equals(type)){//视频
                    mainPresenter.loadContent("getContentDetail", contentId, type);
                } else if("2".equals(type)){//专题
                    mainPresenter.loadContent("getSubject", contentId,type);
                }
            }
        }
    }

    /////////// PictureFocusKeyEvent ↑↑↑↑↑↑

    public void loadContent() {
        content = this.getIntent().getStringExtra("content");
        iv_special = (ImageView) findViewById(R.id.iv_special);
        if(content != null) {
            ContentEntity lm = new Gson().fromJson(content, ContentEntity.class);
            List<LayoutModel.LayoutBean.ContentBean> ls = lm.getData().getContent();
            String bgImg = lm.getData().getBgimage();
            if(bgImg != null){
                Glide.with(this).load(bgImg)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .thumbnail(0.2f)
                        .error(R.drawable.bg_err).into(iv_special);
            }
            if( ls != null) {
               for(int i = 0 ; i < ls.size() ; i++){
                   PictureModel pm  = new PictureModel( i+1 ,330 , 600, "tag_content", i, ls ,this);
                   lms.add(pm);
               }
                fr = (PictureFocusRelative) findViewById(R.id.focus_relative);
                fr.addViews(lms);
                fr.setAnimation(R.anim.scale_small, R.anim.scale_big);
                fr.setmPictureFocusKeyEvent(this);
            }
        }
    }

    ////////////////////////////////////////////////////////
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void loadLayoutSuccess(String lay) {
    }

    @Override
    public void loadContentSuccess(String content, String type) {
        if("0".equals(type) || "1".equals(type)) {//图片 or 视频
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("content", content);
            intent.putExtra("type", type);
            startActivity(intent);
        } else if("2".equals(type)){//专题
            Intent intent = new Intent(this, SpecialActivity.class);
            intent.putExtra("content",content);
            startActivity(intent);
        }
    }

    @Override
    public void loadLayoutFailed(Throwable throwable , int type) {

    }


    @Override
    public void viewDestroy() {
        mainPresenter.unSubscribed();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewDestroy();
    }
}
