package com.funs.appreciate.art.base;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.funs.appreciate.art.R;
import com.funs.appreciate.art.view.widget.PictureFocusRelative;

import java.util.List;

/**
 * Created by yc on 2017/6/14.
 * Base Fragment
 */

public class BaseFragment extends Fragment {

    AnimationEndListeners animationEndListeners;
    public  PictureFocusRelative fr;// content view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    // 再次显示时，位置不在0,0
    public void setScroller(){
        if( getView() != null) {
            HorizontalScrollView horizontalScrollView = (HorizontalScrollView) getView().findViewById(R.id.scroll_view);
            horizontalScrollView.scrollTo(0, 0);
        }
    }


    // 最后获取焦点view
    public void setLastFocus(){
        if(fr != null ){
            fr.setLastFocus();
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation anim = null;
        if(nextAnim != 0) {
            anim = AnimationUtils.loadAnimation(getActivity(), nextAnim);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    System.out.println("======= onAnimationStart ========>");
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    System.out.println("======= onAnimationEnd ========>");
                    animationEndListeners.onAnimationEnd(animation);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        return anim;
    }

    public interface AnimationEndListeners{
        void onAnimationEnd(Animation animation);
    }

    public void setAnimationEndListeners(AnimationEndListeners animationEndListeners) {
        this.animationEndListeners = animationEndListeners;
    }
}
