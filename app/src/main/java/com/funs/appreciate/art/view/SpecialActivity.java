package com.funs.appreciate.art.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.funs.appreciate.art.ArtApp;
import com.funs.appreciate.art.ArtConfig;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.base.BaseActivity;
import com.funs.appreciate.art.di.components.DaggerRecommendFragmentComponent;
import com.funs.appreciate.art.di.components.DaggerSpecialComponent;
import com.funs.appreciate.art.di.modules.ContentModule;
import com.funs.appreciate.art.di.modules.MainModule;
import com.funs.appreciate.art.model.entitys.ContentEntity;
import com.funs.appreciate.art.model.entitys.LayoutModel;
import com.funs.appreciate.art.model.entitys.PictureModel;
import com.funs.appreciate.art.presenter.ContentContract;
import com.funs.appreciate.art.presenter.ContentPresenter;
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

public class SpecialActivity extends BaseActivity implements  PictureFocusRelative.PictureFocusKeyEvent ,ContentContract.View{
    List<PictureModel> lms = new ArrayList<>();
    public  PictureFocusRelative fr;// content view
    @Inject
    ContentPresenter contentPresenter;

    String contentId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);
        DaggerSpecialComponent.builder()
                .netComponent(ArtApp.get(this).getNetComponent())
                .contentModule(new ContentModule(this))
                .build().inject(this);
        contentId = this.getIntent().getStringExtra("contentId");
        if(contentId != null) {
            contentPresenter.loadLayout("getSubject", contentId);
        }
    }

    /////////// PictureFocusKeyEvent ↓↓↓↓↓↓

    @Override
    public void pictureListener(String keyType, PictureModel lm , RelativeLayout rl) {

        if("top".equals(keyType)) {

        } else if("left".equals(keyType)) {

        } else if("right".equals(keyType)) {

        } else if("center".equals(keyType)) {

        }
    }

    /////////// PictureFocusKeyEvent ↑↑↑↑↑↑

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void loadLayoutSuccess(String lay) {
        if(lay != null) {
            ContentEntity lm = new Gson().fromJson(lay, ContentEntity.class);
            List<LayoutModel.LayoutBean.ContentBean> ls = lm.getData().getContent();
            if( ls != null) {
               for(int i = 0 ; i < ls.size() ; i++){
                   PictureModel pm  = new PictureModel( i+1 ,315 , 560, "tag_content", i, ls ,this);
                   lms.add(pm);
               }
                fr = (PictureFocusRelative) findViewById(R.id.focus_relative);
                fr.addViews(lms);
                fr.setAnimation(R.anim.scale_small, R.anim.scale_big);
                fr.setmPictureFocusKeyEvent(this);
            }
        }
    }

    @Override
    public void loadLayoutFailed(Throwable throwable) {

    }
}
