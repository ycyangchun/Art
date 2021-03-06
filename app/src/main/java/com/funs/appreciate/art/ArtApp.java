package com.funs.appreciate.art;

import android.app.Application;
import android.content.Context;

import com.funs.appreciate.art.di.components.DaggerNetComponent;
import com.funs.appreciate.art.di.components.NetComponent;
import com.funs.appreciate.art.di.modules.NetModule;
import com.funs.appreciate.art.utils.PathUtils;
import com.funs.appreciate.art.utils.SharedPreferencesUtils;
import com.squareup.leakcanary.LeakCanary;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by yc on 2017/6/14.
 * application
 */

public class ArtApp  extends Application{
    public static ArtApp instance;
    private NetComponent netComponent;
    public static  ArtApp getInstance(){
        return instance;
    }
    public static ArtApp get(Context context) {
        return (ArtApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initNet();
        initPath();
        initDadaBase();

        // talkingData
        TCAgent.LOG_ON = ArtConfig.IS_DEBUG;
        TCAgent.init(this);
        TCAgent.setReportUncaughtExceptions(true);
        //umeng
        MobclickAgent.setDebugMode(ArtConfig.IS_DEBUG);

        //LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }


    //path
    private void initPath() {
        SharedPreferencesUtils.init(this);
        PathUtils.getInstance(this, "art");
    }

    //net
    private void initNet() {
        netComponent = DaggerNetComponent.builder()
                .netModule(new NetModule(this))
                .build();
    }

    //db
    private void initDadaBase() {

    }

    public NetComponent getNetComponent() {
        return netComponent;
    }
}
