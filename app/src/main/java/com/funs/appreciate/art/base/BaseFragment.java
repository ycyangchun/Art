package com.funs.appreciate.art.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.funs.appreciate.art.R;
import com.funs.appreciate.art.view.widget.PictureFocusRelative;

/**
 * Created by yc on 2017/6/14.
 * Base Fragment
 */

public class BaseFragment extends Fragment {

    public  PictureFocusRelative fr;// content view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    // 获取焦点view
    public void setFocus(){
        if(fr != null ){
            fr.setFocusView();
        }
    }

}
