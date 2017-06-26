package com.funs.appreciate.art.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.base.BaseActivity;

/**
 * Created by yc on 2017/6/26.
 *  浏览
 */

public class BrowseActivity extends BaseActivity {

    ImageView browse_iv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_browse);
        browse_iv = (ImageView) findViewById(R.id.browse_iv);
        Glide.with(this).load(R.drawable.left_iv).into(browse_iv);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN) {
            int keyCode = event.getKeyCode();
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    Glide.with(this).load(R.drawable.left_iv).into(browse_iv);
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    Glide.with(this).load(R.drawable.right_iv).into(browse_iv);
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
