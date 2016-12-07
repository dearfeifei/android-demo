package forever.foreverandroiddemo.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;


public class MyApplication extends Application {

    private static Context mContext;
    private static Handler mHandler;
    private static MyApplication instance;
    private Activity mCurrentActivity = null;

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }




    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mHandler = new Handler();

    }


    public static Context getContext() {
        return mContext;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }



}