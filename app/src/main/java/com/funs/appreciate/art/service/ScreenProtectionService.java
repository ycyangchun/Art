package com.funs.appreciate.art.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.funs.appreciate.art.utils.UtilTimer;
import com.funs.appreciate.art.view.ScreenProtectionActivity;

import static com.funs.appreciate.art.base.ArtConstants.START_STATUS;

/**
 * Created by yc on 2017/6/28.
 * 屏保 service
 */

public class ScreenProtectionService extends Service {

    private static Handler mHandler;
    UtilTimer utilTimer;

    public ScreenProtectionService() {
        super();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println(" === > onCreate ");
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case START_STATUS:
                        System.out.println(" Handler === > START_STATUS ");
                        Intent intent  = new Intent(ScreenProtectionService.this , ScreenProtectionActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        UtilTimer.timerRemove();
                        stopSelf();
                        break;
                }

            }
        };
        utilTimer = UtilTimer.getUtilTimer(this , mHandler);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        System.out.println(" === > onStartCommand " + flags + " " + startId +" "+intent.getStringExtra("screen_status"));
        String status = intent.getStringExtra("screen_status");
        if( status != null ){
            if("start".equals(status)){
                UtilTimer.timerStart();
            } else if("remove".equals(status)){
                UtilTimer.timerRemove();
            }
        }
        return super.onStartCommand(intent, flags, startId);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println(" === > onDestroy ");
    }
}
