package com.funs.appreciate.art.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.funs.appreciate.art.ArtApp;
import com.funs.appreciate.art.ArtConfig;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.base.ArtConstants;
import com.funs.appreciate.art.base.BaseActivity;
import com.funs.appreciate.art.di.components.DaggerMainComponent;
import com.funs.appreciate.art.di.modules.MainModule;
import com.funs.appreciate.art.model.entitys.LayoutModel;
import com.funs.appreciate.art.model.entitys.PictureModel;
import com.funs.appreciate.art.model.util.NoNetworkException;
import com.funs.appreciate.art.presenter.MainContract;
import com.funs.appreciate.art.presenter.MainPresenter;
import com.funs.appreciate.art.utils.ArtResourceUtils;
import com.funs.appreciate.art.utils.MsgHelper;
import com.funs.appreciate.art.view.widget.TabFocusRelative;
import com.google.gson.Gson;

import java.util.ArrayList;
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
    private int tabIndex ; //默认 tab index
    private String lastTab;//上次显示 tab
    List<String> listMainTab , listMainIds;// tab 数据

    ViewPager contentVp;
    MyPagerAdapter myPagerAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        MsgHelper.setMainHandler(handler);
        ArtConfig.setMainActivity(this);

        tabFocusRelative = (TabFocusRelative) findViewById(R.id.focus_linear);
        contentVp = (ViewPager) findViewById(R.id.main_fragment);
        initData();

        DaggerMainComponent.builder()
             .netComponent(ArtApp.get(this).getNetComponent())
             .mainModule(new MainModule(this))
             .build().inject(this);

        mainPresenter.loadLayout("getColumnList","");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initData() {
        tabIndex = 0;//默认 0
        lastTab = "";
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
                                return lastFocusStatus();
//                                return true;
                            }
                        } else {

                        }
                    }

                    // 退出应用
                    if(keyCode == KeyEvent.KEYCODE_BACK){
                        sps_intent.putExtra("screen_status", "remove");
                        startService(sps_intent);
                    }

                    break;
            }
        }
        return super.dispatchKeyEvent(event);


    }

    // tab 最后焦点状态
    private boolean lastFocusStatus() {
        if(tabFocusRelative != null) {
            bubbleFocusEvent(tabFocusRelative, true);
            try {
                RelativeLayout rl = tabFocusRelative.getLastFocusChangeView();
                if(rl != null){
                    rl.requestFocus();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
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
        ArtResourceUtils.setLayoutRes(lay, "main");
        loadData(lay);
    }



    @Override
    public void loadLayoutFailed(Throwable throwable ,int type) {
        if(throwable instanceof NoNetworkException){
            String lay = ArtResourceUtils.getLayoutRes("main");
            if(lay != null)
                loadData(lay);
        }
    }

    @Override
    public void loadContentSuccess(String content , String type) {

    }

    private void loadData(String lay) {
        if(lay != null) {
            LayoutModel lm = new Gson().fromJson(lay, LayoutModel.class);
            listMainTab = lm.getColumnNames();
            listMainIds = lm.getColumnIds();
            int section = 96;
            for (int i = 0; i < listMainTab.size(); i++) {
                int len = listMainTab.get(i).length();
                PictureModel pm = new PictureModel(i + 1, len * section, 96, listMainTab.get(i), i, this);
                lpms.add(pm);
            }

            //tab
            tabFocusRelative.addViews(lpms);
            tabFocusRelative.setAnimation(R.anim.scale_small, R.anim.scale_big);
            tabFocusRelative.setTabSelect(this);
            tabFocusRelative.setIndexFocus(tabIndex);

            //content
            myPagerAdapter = new MyPagerAdapter(this.getSupportFragmentManager());
            contentVp.setAdapter(myPagerAdapter);
            contentVp.setOffscreenPageLimit(listMainTab.size());
        }
    }
    //////////// MainContract.View ↑↑↑↑↑↑
    //////////// TabFocusRelative.TabSelect ↓↓↓↓↓↓↓
    //tab 切换
    @Override
    public void tabChangeListener(String tab) {
        switchPage(tab , -1);
    }

    // 切换界面fragment
    private void switchPage(String tab , int type) {
        tabIndex = listMainTab.indexOf(tab);
        System.out.println("======= tab ======> "+tab+"  =  "+ tabIndex +" = "+lastTab);
        if(tab.equals(lastTab)) return;
        lastTab = tab;
        contentVp.setCurrentItem(tabIndex);

    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return listMainTab.size();
        }

        @Override
        public Fragment getItem(int position) {//只会在新建 Fragment 时执行一次
            Fragment f = null;
            String tab = listMainTab.get(position);
            switch(tab){
            case ArtConstants.recommends:
                f = new RecommendFragment();
                break;
            case ArtConstants.oil:
                f = new RecommendFragment();
                break;
            case ArtConstants.landscape:
                f = new RecommendFragment();
                break;
            case ArtConstants.migratory:
                f = new RecommendFragment();
                break;
            case ArtConstants.mall:
                f = new RecommendFragment();
                break;
        }
            Bundle bundle = new Bundle();
            bundle.putString("columnId",listMainIds.get(position));
            f.setArguments(bundle);
            return f;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
            //FragmentStatePagerAdapter 在会在因 POSITION_NONE
            // 触发调用的 destroyItem() 中
            // 真正的释放资源，重新建立一个新的 Fragment
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return super.isViewFromObject(view, object);
        }


    }

    // frameLayout 处理焦点
    // FOCUS_BLOCK_DESCENDANTS  本身进行处理，不管是否处理成功，都不会分发给ChildView进行处理
    // FOCUS_BEFORE_DESCENDANTS 本身先对焦点进行处理，如果没有处理则分发给child View进行处理
    // FOCUS_AFTER_DESCENDANTS  先分发给Child View进行处理，如果所有的Child View都没有处理，则自己再处理
    public void bubbleFocusEvent(ViewGroup v , boolean enable){
        if (enable){
            if (v != null)
                v.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);//本身先对焦点进行处理
        }
        else{
            if (v != null)
                v.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);//本身进行处理
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
                    leftOrRight(ArtConstants.KEYLEFT);
                    break;
                case ArtConstants.KEYRIGHT:
                    leftOrRight(ArtConstants.KEYRIGHT);
                    break;
            }

        }
        // 左右切换
        private void leftOrRight(int keyCode) {
            int key = tabFocusRelative.switchPageNext(keyCode, tabIndex);
            switchPage(listMainTab.get(key) ,keyCode);
            tabFocusRelative.setTextColorByPageChange(key);
        }
    };

}
