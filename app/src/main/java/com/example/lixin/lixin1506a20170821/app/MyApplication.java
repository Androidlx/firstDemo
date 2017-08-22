package com.example.lixin.lixin1506a20170821.app;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

/**
 * Created by hua on 2017/8/21.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(800,800)
                .memoryCacheSize(2*1024*1024)
                .threadPoolSize(30)
                .threadPriority(1000)
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
