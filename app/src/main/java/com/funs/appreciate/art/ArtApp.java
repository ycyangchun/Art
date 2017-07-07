package com.funs.appreciate.art;

import android.app.Application;
import android.content.Context;

import com.funs.appreciate.art.di.components.DaggerNetComponent;
import com.funs.appreciate.art.di.components.NetComponent;
import com.funs.appreciate.art.di.modules.NetModule;
import com.funs.appreciate.art.utils.PathUtils;
import com.funs.appreciate.art.utils.SharedPreferencesUtils;
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
        // App ID: 在TalkingData创建应用后，进入数据报表页中，在“系统设置”-“编辑应用”页面里查看App ID。
        // 渠道 ID: 是渠道标识符，可通过不同渠道单独追踪数据。
//        TCAgent.init(this, "您的 App ID", "渠道 ID");
        // 如果已经在AndroidManifest.xml配置了App ID和渠道ID，调用TCAgent.init(this)即可；或与AndroidManifest.xml中的对应参数保持一致。
        TCAgent.init(this);
        TCAgent.setReportUncaughtExceptions(true);
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
