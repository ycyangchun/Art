package com.funs.appreciate.art.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.funs.appreciate.art.utils.UIHelper;

/**
 * Created by yc on 2017/6/14.
 * Base Activity
 */

public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        UIHelper.initialize(this, false);
        super.onCreate(savedInstanceState);
    }
}
