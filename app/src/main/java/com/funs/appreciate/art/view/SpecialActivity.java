package com.funs.appreciate.art.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.funs.appreciate.art.R;
import com.funs.appreciate.art.base.BaseActivity;
import com.funs.appreciate.art.model.entitys.PictureModel;
import com.funs.appreciate.art.view.widget.PictureFocusRelative;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 2017/6/26.
 * 专题
 */

public class SpecialActivity extends BaseActivity implements  PictureFocusRelative.PictureFocusKeyEvent{
    List<PictureModel> lms = new ArrayList<>();
    public  PictureFocusRelative fr;// content view
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);
        intViewData();
        fr = (PictureFocusRelative) findViewById(R.id.focus_relative);
        fr.addViews(lms);
        fr.setAnimation(R.anim.scale_small, R.anim.scale_big);
        fr.setmPictureFocusKeyEvent(this);
    }

    private void intViewData() {
        for(int i = 0; i < 8; i++){
            PictureModel lm  = new PictureModel( i+1 ,315 , 560, 0, i,this);
            lms.add(lm);
        }
    }


    /////////// PictureFocusKeyEvent ↓↓↓↓↓↓

    @Override
    public void pictureListener(String keyType, PictureModel lm , RelativeLayout rl) {

        if("top".equals(keyType)) {

        } else if("left".equals(keyType)) {

        } else if("right".equals(keyType)) {

        } else if("center".equals(keyType)) {
            startActivity(new Intent(this , BrowseActivity.class));
        }
    }

    /////////// PictureFocusKeyEvent ↑↑↑↑↑↑
}
