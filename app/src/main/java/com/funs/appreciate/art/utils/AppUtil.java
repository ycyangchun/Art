package com.funs.appreciate.art.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.SparseArray;

import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;
import java.io.FileInputStream;

/**
 *
 */
public class AppUtil {
    /**
     * 获取app版本名
     */
    public static String getAppVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取app版本号
     */
    public static int getAppVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 设备SDK版本
     * Android API等级（22、23 ...）
     */
    public static int getBuildLevel() {
        return android.os.Build.VERSION.SDK_INT;
    }
    /**
     * 设备系统版本
     * Android 版本（4.4、5.0、5.1 ...）
     */
    public static String getBuildVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
    /**
     * deviceId
     * 获取设备序列号
     */
    public static  String deviceId = "";
    public static String getDeviceId(Context context) {
        if(!TextUtils.isEmpty(deviceId)) return  deviceId;
//        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        deviceId = tm.getDeviceId();
//        deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        deviceId =  android.os.Build.SERIAL;
        return deviceId;
    }


    /**
     * wifi网络mac
     */

    public static String wifiMac = null;
    public static String getMacByWifi(Context context) {
        if (!TextUtils.isEmpty(wifiMac)) return wifiMac;
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            wifiMac = info.getMacAddress();
            return wifiMac;
        } catch (Exception e) {
            e.printStackTrace();
            return wifiMac;
        }
    }
    public static String getMacByWifi(){
        if(!TextUtils.isEmpty(wifiMac)) return  wifiMac;
        //在不开起WiFi的情况下获取mac地址
        try {
                String path = "sys/class/net/eth0/address";
                FileInputStream fis_name = new FileInputStream(path);
                byte[] buffer_name = new byte[8192];
                int byteCount_name = fis_name.read(buffer_name);
                if (byteCount_name > 0) {
                    wifiMac = new String(buffer_name, 0, byteCount_name, "utf-8");
                }
                if (wifiMac == null) {
                    fis_name.close();
                    return wifiMac;
                }
                fis_name.close();
            } catch (Exception io) {
                String path = "sys/class/net/wlan0/address";
                FileInputStream fis_name;
                try {
                    fis_name = new FileInputStream(path);
                    byte[] buffer_name = new byte[8192];
                    int byteCount_name = fis_name.read(buffer_name);
                    if (byteCount_name > 0) {
                        wifiMac = new String(buffer_name, 0, byteCount_name, "utf-8");
                    }
                    fis_name.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


            if (wifiMac == null) {
                return wifiMac;
            } else {
                wifiMac = wifiMac.trim();
                if(wifiMac.contains("\n")){
                    wifiMac = wifiMac.substring(0,wifiMac.indexOf("\\"));
                }
                if(wifiMac.contains(":")){
                    wifiMac = wifiMac.replace(":","");
                }
                return wifiMac;
            }

    }


    /**
     * 屏幕尺寸
     */
    public static String getDisplay(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return width + "X" + height;
    }
    /**
     * 设备型号
     * Build.PRODUCT//手机制造商
     */
    public static String getPhoneProduct() {
        return android.os.Build.PRODUCT;
    }
    /**
     * 设备厂商
     * Build.BRAND//系统定制商
     */
    public static String getPhoneBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 设备名称
     * Build.MODEL//手机型号(MI XXX)
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }


    /////////////////////////////////////////////////////////
    private static Activity mainActivity;

    private static boolean lowMemory = false;
    private static boolean initialized = false;
    private static String rootDirPath;
    private static String cacheDirPath;
    private static String dataDirPath;
    private static String webpicDirPath;
    public static boolean running = true;
    public static synchronized void initialize(Activity activity) {

        mainActivity = activity;

        running = true;

        if (!initialized) {
            setDirPath();
            initImageLoader(activity);
            initialized = true;

            long memSize = getTotalMemory(activity);
            if (memSize > 0 && memSize < (1024 << 20))
                lowMemory = true;
        }

        // 删除上次的缓存数据

    }
    public static long getTotalMemory(Activity activity) {
        return 0;
    }

    /**
     * 配置各种文件夹路径
     */
    private static void setDirPath() {
        File rootDir = mainActivity.getDir("art", Context.MODE_PRIVATE);
        rootDirPath = rootDir.getPath();

        // 存储缓存的目录
        cacheDirPath = rootDirPath + "/cache";
        File cacheDir = new File(rootDirPath, "cache");
        if (!cacheDir.exists())
            cacheDir.mkdir();

        // 存储缓存的目录
        dataDirPath = rootDirPath + "/data";
        File dataDir = new File(rootDirPath, "data");
        if (!dataDir.exists())
            dataDir.mkdir();


        // 存储缓存的目录
        webpicDirPath = rootDirPath + "/webpic";
        File webpicDir = new File(rootDirPath, "webpic");
        if (!webpicDir.exists())
            webpicDir.mkdir();
    }
    public static final int MEMORY_CACHE_SIZE = 4 << 20; // 内存缓存大小
    public static final long DISK_CACHE_SIZE = 50 << 20; // 磁盘缓存大小
    private static DisplayImageOptions imgLoaderOptions;
    private static DisplayImageOptions rawImageOptions;
    private static SparseArray<DisplayImageOptions> roundImageOptions;
    private static SparseArray<DisplayImageOptions> imageLoaderOptions;
    /**
     * 初始化ImageLoader的配置
     */
    private static void initImageLoader(Context context) {

        final File diskCacheFile = new File(context.getCacheDir().getAbsolutePath()
                + File.separator
                + "imageLoader"
                + File.separator + "art_cache");

		/*
		FIFOLimitedMemoryCache：当内存不足时，会根据先进先出的原则进行清理
        LargestLimitedMemoryCache：当内存不足时，会清理sizes最大的值。
        UsingFreqLimitedMemoryCache：当内存不足时，会清掉使用频率最低的缓存。
        LRULimitedMemoryCache：当内存不足时，将清掉使用时间最老的缓存。
        LruMemoryCache：当某一个bitmap被访问时，它被移到队首，当内存不够时，从队尾清理。
		LimitedAgeMemoryCache：当内存不足时，会清理掉超过最大时间的缓存。
        FuzzyKeyMemoryCache：当添加一个元素时，会先把缓存中，key相同的元素删掉
		 * */

        imgLoaderOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)     //设置图片的解码类型
                .build();

        rawImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.ARGB_8888)     //设置图片的解码类型
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(3)// 加载图片的线程数
                .threadPriority(Thread.NORM_PRIORITY - 1) // 最高线程优先级
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .denyCacheImageMultipleSizesInMemory() // 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .memoryCache(new LruMemoryCache(MEMORY_CACHE_SIZE))
                .memoryCacheSize(MEMORY_CACHE_SIZE)
                .diskCache(
                        new LimitedAgeDiskCache(diskCacheFile, new File(
                                diskCacheFile, "reserve"),
                                new Md5FileNameGenerator(), 24 * 60 * 60))
                .defaultDisplayImageOptions(imgLoaderOptions)
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();
        //关闭 imageloader 日志
        com.nostra13.universalimageloader.utils.L.disableLogging();

        roundImageOptions = new SparseArray<DisplayImageOptions>();
        imageLoaderOptions = new SparseArray<DisplayImageOptions>();

    }

    /**
     * @return 针对ImageLoaded的图片选项
     */
    public static DisplayImageOptions getImgOptions() {
        return imgLoaderOptions;
    }

    public static DisplayImageOptions getRawImageOptions() {
        return rawImageOptions;
    }

    public static synchronized DisplayImageOptions getRoundImageOptions(int round) {
        DisplayImageOptions value = roundImageOptions.get(round, null);
        if (value == null) {

            value = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)     //设置图片的解码类型
                    .displayer(new RoundedBitmapDisplayer(round))
                    .build();

            roundImageOptions.put(round, value);
        }

        return value;
    }

    public static synchronized DisplayImageOptions getImageLoaderOptionsWithInSample(int inSampleSize) {

        DisplayImageOptions value = imageLoaderOptions.get(inSampleSize, null);
        if (value == null) {

            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            opts.inPurgeable = true;
            opts.inInputShareable = true;
            opts.inSampleSize = inSampleSize;

            value = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .decodingOptions(opts)
                    .build();

            imageLoaderOptions.put(inSampleSize, value);
        }

        return value;
    }
}
