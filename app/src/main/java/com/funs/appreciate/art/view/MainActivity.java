package com.funs.appreciate.art.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.funs.appreciate.art.ArtApp;
import com.funs.appreciate.art.ArtConfig;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.base.ArtConstants;
import com.funs.appreciate.art.base.BaseActivity;
import com.funs.appreciate.art.base.BaseFragment;
import com.funs.appreciate.art.di.components.DaggerMainComponent;
import com.funs.appreciate.art.di.modules.MainModule;
import com.funs.appreciate.art.model.entitys.LayoutModel;
import com.funs.appreciate.art.model.entitys.PictureModel;
import com.funs.appreciate.art.presenter.MainContract;
import com.funs.appreciate.art.presenter.MainPresenter;
import com.funs.appreciate.art.utils.MsgHelper;
import com.funs.appreciate.art.view.widget.TabFocusRelative;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by yc on 2017/6/15.
 * art main
 */

public class MainActivity extends BaseActivity  implements MainContract.View ,TabFocusRelative.TabSelect{

    @Inject
    MainPresenter mainPresenter;

    private TabFocusRelative tabFocusRelative;// tab 布局
    private List<PictureModel> lpms = new ArrayList<>();// tab model数据
    private int currentTabContainId  = -1 ;//当前选择tab ContainId
    private int lastTabContainId = -1;//上次选择tab ContainId
    private int tabIndex ; //默认 tab index
    private String lastTab;//上次显示 tab
    List<String> listMainTab;// tab 数据
    private FragmentManager fm;//
    private FragmentTransaction ft;//
    private BaseFragment currentFragment;// 当前 fragment
    private String layoutString;// 布局数据
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        MsgHelper.setMainHandler(handler);
        ArtConfig.setMainActivity(this);

        tabFocusRelative = (TabFocusRelative) findViewById(R.id.focus_linear);
        tabIndex = 0;//默认 0
        lastTab = "";

        DaggerMainComponent.builder()
             .netComponent(ArtApp.get(this).getNetComponent())
             .mainModule(new MainModule(this))
             .build().inject(this);

        mainPresenter.loadLayout();


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
                            bubbleFocusEvent(tabFocusRelative,false);
                            if (keyCode == KeyEvent.KEYCODE_BACK ) {// item按 (返回键) 导航获取焦点
                                lastFocusStatus();
                                return true;
                            }
                        } else {

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
        bubbleFocusEvent(tabFocusRelative,true);
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
        //////////////////////////////////
        if(lay != null) {
            layoutString = lay;
            LayoutModel lm = new Gson().fromJson(lay, LayoutModel.class);
        }
        //////////////////////////////////
        String mainTab[] = new  String[]{ "精品推荐" , "油画" };//,"山水画" ,"候鸟摄影" ,"精品商城"};
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

    }

    //////////// TabFocusRelative.TabSelect ↓↓↓↓↓↓↓
    //tab 切换
    @Override
    public void tabChangeListener(String tab) {
        switchPage(tab , -1);
    }

    // 切换界面fragment
    private void switchPage(String tab , int type) {
        if(tab.equals(lastTab)) return;
        lastTab = tab;

        tabIndex = listMainTab.indexOf(tab);
        System.out.println("=======tab======>"+tab+" tabIndex "+tabIndex +" lastTab "+lastTab);
        fm = this.getSupportFragmentManager();
        // 隐藏上一个
        if(lastTabContainId != -1){
            FrameLayout lastFrame = (FrameLayout) findViewById(lastTabContainId);
            lastFrame.setVisibility(View.GONE);
            ft = fm.beginTransaction();
            final BaseFragment lastFragment = (BaseFragment) fm.findFragmentByTag(lastTabContainId+"_fgm");
            ft.hide(lastFragment);
            ft.commit();
        }

        // 显示当前的
        currentTabContainId = getContainId(tab);
        currentFragment = (BaseFragment) fm.findFragmentByTag(currentTabContainId+"_fgm");
        if(currentFragment == null) {
            switch (tab) {
                case ArtConstants.recommends:
                    currentFragment = new RecommendFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("layout",layoutString);
                    currentFragment.setArguments(bundle);
                    break;
                case ArtConstants.oil:
                    currentFragment = new OilFragment();
                    break;
                case ArtConstants.landscape:
                    break;
                case ArtConstants.migratory:
                    break;
                case ArtConstants.mall:
                    break;
            }

            // 第一次添加
            ft = fm.beginTransaction();
            ft.add(currentTabContainId, currentFragment, currentTabContainId+"_fgm");
            ft.commit();
            if(type == ArtConstants.KEYRIGHT) { // 右 滑动创建fragment时获取第一个焦点
                MsgHelper.sendMessageDelayed(handler, ArtConstants.RIGHTSCROLLCREATE, 100);
            }
        } else {
            ft = fm.beginTransaction();
            ft.show(currentFragment);
            ft.commit();
            if(type != -1) { // 左右切换 ，回复上次焦点
                MsgHelper.sendMessage(handler, ArtConstants.RIGHTSCROLLCREATE);
            }
        }
        FrameLayout currentFrame = (FrameLayout) findViewById(currentTabContainId);
        currentFrame.setVisibility(View.VISIBLE);
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
    public void bubbleFocusEvent(ViewGroup v , boolean enable){
        if (enable){
            if (v != null)
                v.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        }
        else{
            if (v != null)
                v.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        }
    }
    @Override
    public void loadLayoutFailed(Throwable throwable) {

    }
    //////////// MainContract.View ↑↑↑↑↑↑

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ArtConstants.KEYTOP:
                    lastFocusStatus();
                    break;
                case ArtConstants.KEYLEFT:
                    leftOrRight(ArtConstants.KEYLEFT);
                    break;
                case ArtConstants.KEYRIGHT:
                    leftOrRight(ArtConstants.KEYRIGHT);
                    break;
                case ArtConstants.RIGHTSCROLLCREATE: //
                    currentFragment.setFocus();
                    break;
            }

        }
        // 左右切换
        private void leftOrRight(int keyCode) {
            if( ArtConstants.KEYLEFT == keyCode ){
                currentFragment.setScroller();
            }
            int key = tabFocusRelative.switchPageNext(keyCode, tabIndex);
            switchPage(listMainTab.get(key) ,keyCode);
            tabFocusRelative.setTextColorByPageChange(key);
        }
    };

}
