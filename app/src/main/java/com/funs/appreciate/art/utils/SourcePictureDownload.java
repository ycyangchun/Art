package com.funs.appreciate.art.utils;

import android.app.Application;
import android.content.Context;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class SourcePictureDownload {

    private Context mContext;
    private DownloadListener sListener;
    public String localPath;
    private final static String TAG = SourcePictureDownload.class.getName();

    public interface DownloadListener {
        void downSuccess(String url,String path, int position);

        void downFailed(Throwable t, String url);
    }

    public SourcePictureDownload(Context context, DownloadListener listener) {
        this.mContext = context;
        this.sListener = listener;
        localPath = PathUtils.resourcePath;
        x.Ext.init((Application) context.getApplicationContext());// Xutils初始化
    }

    public void downloadX(String sRes, final int position) {
        final String url = sRes;
        String name = sRes.substring(sRes.lastIndexOf("/") + 1, sRes.length());
        final String path = localPath + File.separator + name;
        RequestParams params = new RequestParams(url);
        params.setAutoRename(true);// 断点下载
        params.setSaveFilePath(path);
        x.http().get(params, new Callback.CommonCallback<File>() {

            @Override
            public void onCancelled(CancelledException arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                if(sListener != null)
                    sListener.downFailed(arg0, url);

            }

            @Override
            public void onFinished() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onSuccess(File arg0) {
                if(sListener != null) {
                    sListener.downSuccess(url, path, position);
                }

            }
        });
    }

}
