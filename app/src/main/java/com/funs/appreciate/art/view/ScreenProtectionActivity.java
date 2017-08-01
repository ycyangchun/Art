package com.funs.appreciate.art.view;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.funs.appreciate.art.ArtApp;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.di.components.DaggerScreenProtectionComponent;
import com.funs.appreciate.art.di.modules.SplashModule;
import com.funs.appreciate.art.model.entitys.SplashPictureEntity;
import com.funs.appreciate.art.model.util.NoNetworkException;
import com.funs.appreciate.art.presenter.SplashContract;
import com.funs.appreciate.art.presenter.SplashPresenter;
import com.funs.appreciate.art.utils.ArtResourceUtils;
import com.funs.appreciate.art.utils.PathUtils;
import com.funs.appreciate.art.utils.UIHelper;
import com.funs.appreciate.art.utils.ZipUtils;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by yc on 2017/6/23.
 * 屏保
 */

public class ScreenProtectionActivity extends FragmentActivity implements SplashContract.View {

    private PowerManager.WakeLock wakeLock;
    private ViewPager viewPager;
    private Context instance;
    private static int picIndex;
    private int duration;
    private final static int webPic = 0;
    private MyPagerAdapter myPagerAdapter;
    @Inject
    SplashPresenter presenter;
    List<SplashPictureEntity.ConfigBean.DataJsonBean> dataJsonBeen ,dataJsonBeenLocal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIHelper.initialize(this, false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        // 屏蔽系统的屏保
        KeyguardManager manager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = manager
                .newKeyguardLock("KeyguardLock");
        lock.disableKeyguard();
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "SimpleTimer");


        setContentView(R.layout.activity_screen);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        findViewById(R.id.count_down_tv).setVisibility(View.GONE);
        instance = this.getApplicationContext();
        System.out.println("=============== 屏保 ===============>" +  ArtResourceUtils.getScreenSaverTime(10));

        DaggerScreenProtectionComponent.builder()
                .netComponent(ArtApp.get(this).getNetComponent())
                .splashModule(new SplashModule(this))
                .build().inject(this);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handler.removeMessages(webPic);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((wakeLock != null) && (wakeLock.isHeld() == false)) {
            wakeLock.acquire();
        }
        MobclickAgent.onResume(this);
        // 直接跳转到屏保
        String from = this.getIntent().getStringExtra("from");
        if(!TextUtils.isEmpty(from)){
            System.out.println("=============from=================>"+from);
        }
        loadLocalData();
        String res = ArtResourceUtils.getScreenSaverRes();
        if(TextUtils.isEmpty(res)) {
            if(presenter != null) {
                presenter.loadSplash("1",true);
            }
        } else {
            loadData(res);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if ((wakeLock != null) && (wakeLock.isHeld() == false)) {
            wakeLock.acquire();
        }
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wakeLock != null) {
            wakeLock.release();
            wakeLock = null;
        }
        handler.removeMessages(webPic);
    }

    ///////////////////////////////////////////////////////
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void loadSplashSuccess(String splash) {
        loadData(splash);
    }

    @Override
    public void loadSplashFailed(Throwable throwable) {
        if (throwable instanceof NoNetworkException) {
            String splash = ArtResourceUtils.getScreenSaverRes();
            if (splash != null)
                loadData(splash);
        }
    }

    private void loadLocalData(){
        dataJsonBeenLocal = new ArrayList<>();
        // 本地图片
        String res = PathUtils.resourcePath;
        final String scrPath = res + File.separator + "screen";
        File scrFile = new File(scrPath);
        // 本地图片 添加到list
        File [] files = scrFile.listFiles();
        if(files != null) {
            if (files.length == 15) {//已经解压过
                for( int i = 0; i < files.length ;i++){
                    SplashPictureEntity.ConfigBean.DataJsonBean djb = new SplashPictureEntity.ConfigBean.DataJsonBean();
                    djb.setImgUrl(scrPath + File.separator + i+".jpg");
                    dataJsonBeenLocal.add(djb);
                }
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final long t = System.currentTimeMillis();
                            ZipUtils.UnAssZipFolder(ScreenProtectionActivity.this, "screenRes.zip", scrPath, new ZipUtils.ZipListener() {
                                @Override
                                public void zipComplete(String name) {
//                                    System.out.println("============== zipComplete =========="+ name);
                                }

                                @Override
                                public void zipAllComplete() {
                                    long t2= System.currentTimeMillis();
                                    loadLocalData();
                                    dataJsonBeen.addAll(dataJsonBeenLocal);
                                    myPagerAdapter.updateView(dataJsonBeen);
                                    myPagerAdapter.notifyDataSetChanged();
//                                    System.out.println("============== zipComplete ==========" + ( t2 - t)/ 1000);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                    }
                    }
                }).start();
            }
        }
    }

    private void loadData(String splash) {
        SplashPictureEntity se = new Gson().fromJson(splash, SplashPictureEntity.class);
        SplashPictureEntity.ConfigBean cb = se.getConfig();
        dataJsonBeen = cb.getImageArray();
        myPagerAdapter = new MyPagerAdapter();
        myPagerAdapter.setView(dataJsonBeen);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        duration = 5;//默认
        try {
            duration = Integer.parseInt(cb.getDuration());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            if(dataJsonBeenLocal != null && dataJsonBeenLocal.size() >0) {
                dataJsonBeen.addAll(dataJsonBeenLocal);
                myPagerAdapter.updateView(dataJsonBeen);
                myPagerAdapter.notifyDataSetChanged();
            }

            if (dataJsonBeen != null) {
                picIndex = 0;
                if (dataJsonBeen.size() > 0) {
                    handler.sendEmptyMessage(webPic);
                }
            }
        }
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case webPic:
                    if (dataJsonBeen != null) {
//                        System.out.println("=====picIndex =====> picIndex  " + picIndex);
                        viewPager.setCurrentItem(picIndex);
                        handler.sendEmptyMessageDelayed(webPic, duration * 1000);
                        picIndex = getCurrentShow();
                    }
                    break;
            }

        }
    };

    private int getCurrentShow() {
        int temp = ++picIndex;
        if (temp > dataJsonBeen.size() - 1) {
            picIndex = 0;
        } else {
            picIndex = temp;
        }
        return picIndex;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN) {
            int keyCode = event.getKeyCode();
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_CENTER:
                case KeyEvent.KEYCODE_ENTER:
                case KeyEvent.KEYCODE_BACK:
                    finish();
                    handler.removeMessages(webPic);
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }


    private List<ImageView> views;
    class MyPagerAdapter extends PagerAdapter {
        public MyPagerAdapter() {
            views = new ArrayList<>();
        }

        void setView(List<SplashPictureEntity.ConfigBean.DataJsonBean> dataJsonBeen){
            if(dataJsonBeen != null && dataJsonBeen.size() > 0) {
                for (int i = 0; i < dataJsonBeen.size(); i++) {
                    views.add(new ImageView(instance));
                }
            }
        }

        void updateView(List<SplashPictureEntity.ConfigBean.DataJsonBean> dataJsonBeen){
            views.clear();
            if(dataJsonBeen != null && dataJsonBeen.size() > 0) {
                for (int i = 0; i < dataJsonBeen.size(); i++) {
                    views.add(new ImageView(instance));
                }
            }
        }

        @Override
        public int getCount() {
            return views == null ? 0 : views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = views.get(position);
            String url = dataJsonBeen.get(position).getImgUrl();
//            System.out.println("=====url =====>" + url + " position  " + position);
            Glide.with(ScreenProtectionActivity.this)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            System.out.println(model);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .error(R.drawable.bg_welcome)
                    .crossFade()
                    .into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //释放资源
//            recycleView(position);
            ImageView imageView = views.get(position);
            Glide.clear(imageView);
            container.removeView(imageView);
        }
    }

    //
    void recycleView(int position){
        ImageView imageView = views.get(position);
        Drawable drawable = imageView.getDrawable();
        if(drawable != null ) {
            if (drawable instanceof GlideDrawable) {
                GlideBitmapDrawable bitmapDrawable = (GlideBitmapDrawable) drawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (bitmap != null && !bitmap.isRecycled()) {
                    try {
                        bitmap.recycle();
                        bitmap = null;
//                        System.gc();
                        System.out.println("===== recycle =====> position  " + position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
