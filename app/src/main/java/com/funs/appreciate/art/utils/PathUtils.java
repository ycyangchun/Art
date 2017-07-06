package com.funs.appreciate.art.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;


import java.io.File;
import java.io.Serializable;

/**
 *  path
 */
public class PathUtils implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7373248994698828806L;

    private static Context ctx;
    /**
     * application version code
     */
    public static String APP_VERSION;
    /**
     * application package name
     */
    public static String APP_PACKAGE;
    /**
     * application card storage
     */
    public static File EXT_STORAGE;

    /**
     * 应用根目录 xess
     */
    public static String ROOT_NAME;


    public static PathUtils getInstance(Context context, String name) {
        PathUtils.ctx = context;
        PathUtils.ROOT_NAME = name;
        return PathHolder.instance;
    }

    private static class PathHolder {
        public final static PathUtils instance = new PathUtils();
    }

    private PathUtils() {
        initSelf();
    }

    /**
     * application root path,when base package application initialization method
     * call this library application create the folder in the card and create
     * other folder in root folder to use another communications.
     */
    public static String rootPath;


    /**
     * when application in test model log will not output if application
     * exception the reason be write to file and upload to server
     */
    public static String exlogPath;

    /**
     * auto create database to this path or delete database from this path when
     * the application update self or come data error
     */
    public static String databasePath;


    /**
     * put some temporary file to this path
     */
    public static String downloadPath;

    /**
     * resource in this path
     */

    public static String resourcePath;

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        if (!SharedPreferencesUtils.getString("root").equals(rootPath)) {
            if (new File(getRootPath()).renameTo(new File(rootPath)))
                SharedPreferencesUtils.put("root", rootPath);
        }
        PathUtils.rootPath = rootPath;
    }


    public String getExlogPath() {
        return exlogPath;
    }

    public void setExlogPath(String exlogPath) {
        if (!SharedPreferencesUtils.getString("exlog").equals(exlogPath)) {
            if (new File(getExlogPath()).renameTo(new File(exlogPath)))
                SharedPreferencesUtils.put("exlog", exlogPath);
        }
        PathUtils.exlogPath = exlogPath;
    }

    public String getDatabasePath() {
        return databasePath;
    }

    public void setDatabasePath(String databasePath) {
        if (!SharedPreferencesUtils.getString("database").equals(databasePath)) {
            if (new File(getDatabasePath()).renameTo(new File(databasePath)))
                SharedPreferencesUtils.put("database", databasePath);
        }
        PathUtils.databasePath = databasePath;
    }


    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        if (!SharedPreferencesUtils.getString("download").equals(downloadPath)) {
            if (new File(getDownloadPath()).renameTo(new File(downloadPath)))
                SharedPreferencesUtils.put("download", downloadPath);
        }
        PathUtils.downloadPath = downloadPath;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        if (!SharedPreferencesUtils.getString("resource").equals(resourcePath)) {
            if (new File(getResourcePath()).renameTo(new File(resourcePath)))
                SharedPreferencesUtils.put("resource", resourcePath);
        }
        PathUtils.resourcePath = resourcePath;
    }

    /**
     * delete the folder files
     *
     * @return success or failure
     */
    public boolean deleteFile(String path) {
        if (!TextUtils.isEmpty(path)) {
            File f = new File(path);
            for (File file : f.listFiles()) {
                if (file.isFile())
                    file.delete();
            }
            return true;
        } else
            return false;
    }

    /**
     * create the folder in root path
     *
     * @param path
     * @return success or failure
     */
    public boolean createFolder(String path) {
        if (!TextUtils.isEmpty(path)) {
            File f = new File(path);
            if (!f.exists())
                f.mkdir();
            return true;
        } else
            return false;
    }

    /**
     * initialization this folder and initialization path
     */
    private void initSelf() {
//        SharedPreferencesUtils.init(ctx);
        PackageManager pm = ctx.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(ctx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (pi != null) {
            APP_VERSION = pi.versionName;
            APP_PACKAGE = pi.packageName;
        } else
            System.err.println("get application version & package failure!");
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
            EXT_STORAGE = Environment.getExternalStorageDirectory();
        else
            EXT_STORAGE = ctx.getCacheDir();

        //---------begin---添加应用路径-----------
        StringBuffer stringBuffer = new StringBuffer();
        String path = EXT_STORAGE.getAbsolutePath();
        stringBuffer.append(path)
                .append(File.separator)
                .append("Android")
                .append(File.separator)
                .append("data")
                .append(File.separator)
                .append(APP_PACKAGE);
        EXT_STORAGE = new File(stringBuffer.toString());
        if (!EXT_STORAGE.exists()) {
            EXT_STORAGE.mkdirs();
        }

        //---------end--------------
        File base = null;
        if (TextUtils.isEmpty(SharedPreferencesUtils.getString("root"))) {
            base = new File(EXT_STORAGE, ROOT_NAME);
            if (base.isDirectory() || base.mkdir()) {
                SharedPreferencesUtils.put("root", base.getAbsolutePath());
                setRootPath(base.getAbsolutePath());
            }
        } else {
            base = new File(SharedPreferencesUtils.getString("root"));
            setRootPath(SharedPreferencesUtils.getString("root"));
        }

        if (TextUtils.isEmpty(SharedPreferencesUtils.getString("exlog"))) {
            File exlogDir = new File(base, "exlog");
            if (exlogDir.isDirectory() || exlogDir.mkdir()) {
                SharedPreferencesUtils.put("exlog", exlogDir.getAbsolutePath());
                setExlogPath(exlogDir.getAbsolutePath());
            }
        } else
            setExlogPath(SharedPreferencesUtils.getString("exlog"));
        if (TextUtils.isEmpty(SharedPreferencesUtils.getString("database"))) {
            File databaseDir = new File(base, "database");
            if (databaseDir.isDirectory() || databaseDir.mkdir()) {
                SharedPreferencesUtils.put("database", databaseDir.getAbsolutePath());
                setDatabasePath(databaseDir.getAbsolutePath());
            }
        } else
            setDatabasePath(SharedPreferencesUtils.getString("database"));

        if (TextUtils.isEmpty(SharedPreferencesUtils.getString("download"))) {
            File downloadDir = new File(base, "download");
            if (downloadDir.isDirectory() || downloadDir.mkdir()) {
                SharedPreferencesUtils.put("download", downloadDir.getAbsolutePath());
                setDownloadPath(downloadDir.getAbsolutePath());
            }
        } else
            setDownloadPath(SharedPreferencesUtils.getString("download"));
        if (TextUtils.isEmpty(SharedPreferencesUtils.getString("resource"))) {
            File resourceDir = new File(base, "resource");
            if (resourceDir.isDirectory() || resourceDir.mkdir()) {
                SharedPreferencesUtils.put("resource", resourceDir.getAbsolutePath());
                setResourcePath(resourceDir.getAbsolutePath());
            }
        } else
            setResourcePath(SharedPreferencesUtils.getString("resource"));
    }
}
