package com.funs.appreciate.art.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funs.appreciate.art.ArtApp;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.base.ArtConstants;
import com.funs.appreciate.art.base.BaseActivity;
import com.funs.appreciate.art.base.BaseFragment;
import com.funs.appreciate.art.di.components.DaggerMainComponent;
import com.funs.appreciate.art.di.modules.MainModule;
import com.funs.appreciate.art.model.entitys.PictureModel;
import com.funs.appreciate.art.presenter.MainContract;
import com.funs.appreciate.art.presenter.MainPresenter;
import com.funs.appreciate.art.utils.AnimFocusTabManager;
import com.funs.appreciate.art.utils.MsgHelper;
import com.funs.appreciate.art.view.widget.TabFocusRelative;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by yc on 2017/6/15.
 * art main
 */

public class MainActivity extends BaseActivity  implements MainContract.View , AnimFocusTabManager.TabSelect{

    @Inject
    MainPresenter mainPresenter;

    private TabFocusRelative tabFocusRelative;// tab 布局
    private List<PictureModel> lpms = new ArrayList<>();// tab model数据
    private int currentTabContainId  = -1 ;//当前选择tab ContainId
    private int lastTabContainId = -1;//上次选择tab ContainId
    private int tabIndex ; //默认 tab index
    List<String> listMainTab;// tab 数据
    private FragmentManager fm;//
    private FragmentTransaction ft;//
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        MsgHelper.setMainHandler(handler);

        tabFocusRelative = (TabFocusRelative) findViewById(R.id.focus_linear);

        DaggerMainComponent.builder()
             .netComponent(ArtApp.get(this).getNetComponent())
             .mainModule(new MainModule(this))
             .build().inject(this);

        mainPresenter.loadLayout();

        tabIndex = 0;//默认 0
    }

    // dispatchKeyEvent ↓↓↓↓↓↓↓
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN) {
            int keyCode = event.getKeyCode();
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                case KeyEvent.KEYCODE_DPAD_UP:
                case KeyEvent.KEYCODE_DPAD_DOWN:
                case KeyEvent.KEYCODE_DPAD_CENTER:
                case KeyEvent.KEYCODE_ENTER:
                case KeyEvent.KEYCODE_MENU:
                case KeyEvent.KEYCODE_BACK:
                    // 针对焦点在item上传递
                    View focus = getCurrentFocus();
                    if (focus != null ){
                        if(!tabFocusRelative.isChileView(focus)) {// 导航失去焦点
//                            System.out.println("======= 焦点在item =======>");
                            if (keyCode == KeyEvent.KEYCODE_BACK ) {// item按 (返回键) 导航获取焦点
                                lastFocusStatus();
                                return true;
                            }
                        }
                    }

                    // 退出应用
                    if(keyCode == KeyEvent.KEYCODE_BACK){

                    }

                    break;
            }
        }
        return super.dispatchKeyEvent(event);


    }

    // tab 最后焦点状态
    private void lastFocusStatus() {
        tabFocusRelative.getLastFocusChangeView().requestFocus();
    }


    //////////// MainContract.View ↓↓↓↓↓↓↓
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void loadLayoutSuccess(String lay) {
        String mainTab[] = new  String[]{ "今日推荐" , "油画" ,"山水画" ,"候鸟摄影" ,"精品商城"};
        listMainTab = Arrays.asList(mainTab);
        int section = 80;
        for(int i = 0; i<  listMainTab.size() ;i++){
            int len = listMainTab.get(i).length();
            PictureModel pm = new PictureModel( i+1 , len * section , 100 , mainTab[i], i  ,this);
            lpms.add(pm);
        }
        
        tabFocusRelative.addViews(lpms);
        tabFocusRelative.setAnimation(R.anim.scale_small, R.anim.scale_big);
        tabFocusRelative.setTabSelect(this);
        tabFocusRelative.setIndexFocus(tabIndex);

        // 切换界面
        switchPage(listMainTab.get(tabIndex));
    }

    // 切换界面
    private void switchPage(String tab) {
        BaseFragment fragment;
        fm = this.getSupportFragmentManager();

        // 隐藏上一个
        if(lastTabContainId != -1){
            FrameLayout lastFrame = (FrameLayout) findViewById(lastTabContainId);
            bubbleFocusEvent(lastFrame,false);
            lastFrame.setVisibility(View.GONE);
            lastFrame.clearFocus();

            ft = fm.beginTransaction();
            final BaseFragment lastFragment = (BaseFragment) fm.findFragmentByTag(lastTabContainId+"_fgm");
            ft.hide(lastFragment);
            ft.commit();
        }

        // 显示当前的
        currentTabContainId = getContainId(tab);
        fragment = (BaseFragment) fm.findFragmentByTag(currentTabContainId+"_fgm");
        if(fragment == null) {
            switch (tab) {
                case ArtConstants.recommends:
                    fragment = new RecommendFragment();
                    break;
                case ArtConstants.oil:
                    fragment = new OilFragment();
                    break;
                case ArtConstants.landscape:
                    fragment = new RecommendFragment();
                    break;
                case ArtConstants.migratory:
                    fragment = new OilFragment();
                    break;
                case ArtConstants.mall:
                    fragment = new RecommendFragment();
                    break;
            }

            // 第一次添加
            ft = fm.beginTransaction();
            ft.add(currentTabContainId, fragment, currentTabContainId+"_fgm");
            ft.commit();
        } else {
            ft = fm.beginTransaction();
            ft.show(fragment);
            ft.commit();
            fragment.setScroller();
        }
        FrameLayout currentFrame = (FrameLayout) findViewById(currentTabContainId);

        currentFrame.setVisibility(View.VISIBLE);
        currentFrame.requestFocus();
        bubbleFocusEvent(currentFrame,true);
//        fragment.setIndexFocus();
        lastTabContainId = currentTabContainId;
    }

    // replace
    private int getContainId(String  tab ){
        int container_id = 0;
        switch(tab){
            case ArtConstants.recommends: container_id = R.id.frameLayout1; break;
            case ArtConstants.oil:        container_id = R.id.frameLayout2; break;
            case ArtConstants.landscape:  container_id = R.id.frameLayout3; break;
            case ArtConstants.migratory:  container_id = R.id.frameLayout4; break;
            case ArtConstants.mall:       container_id = R.id.frameLayout5; break;
        }

        return container_id;
    }

    // frameLayout 处理焦点
    // FOCUS_BLOCK_DESCENDANTS  本身进行处理，不管是否处理成功，都不会分发给ChildView进行处理
    // FOCUS_BEFORE_DESCENDANTS 本身先对焦点进行处理，如果没有处理则分发给child View进行处理
    // FOCUS_AFTER_DESCENDANTS  先分发给Child View进行处理，如果所有的Child View都没有处理，则自己再处理
    public void bubbleFocusEvent(FrameLayout v , boolean enable){
        if (enable){
            if (v != null)
                v.setDescendantFocusability(FrameLayout.FOCUS_BEFORE_DESCENDANTS);
        }
        else{
            if (v != null)
                v.setDescendantFocusability(FrameLayout.FOCUS_BLOCK_DESCENDANTS);
        }
    }
    @Override
    public void loadLayoutFailed(Throwable throwable) {

    }
    //////////// MainContract.View ↑↑↑↑↑↑

    //////////// AnimFocusTabManager.TabSelect ↓↓↓↓↓↓↓
    @Override
    public void itemListener(boolean hasFocus ,RelativeLayout rl ,TextView tv) {
//        System.out.println("== tab Listener ==> "+ hasFocus+"  " +tv.getId()+"  "+tv.getText());
        tabFocusRelative.recordFocus(hasFocus ,rl ,tv);
        if(hasFocus) {// 获取焦点时才切换
            String title = tv.getText().toString();
            switchPage(title);
            tabIndex = listMainTab.indexOf(title);
            tabFocusRelative.setLastFocusChangeIndex(tabIndex);
        }

    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ArtConstants.KEYTOP:
                    lastFocusStatus();
                    break;
                case ArtConstants.KEYLEFT:
                    tabFocusRelative.switchPage(ArtConstants.KEYLEFT).requestFocus();
                    break;
                case ArtConstants.KEYRIGHT:
                    tabFocusRelative.switchPage(ArtConstants.KEYRIGHT).requestFocus();
                    break;
                case 3:
                    break;
            }

        }
    };
}
